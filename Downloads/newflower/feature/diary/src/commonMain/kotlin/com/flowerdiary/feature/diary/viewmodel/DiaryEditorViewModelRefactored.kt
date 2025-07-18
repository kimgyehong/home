package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.constants.Messages
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.*
import com.flowerdiary.domain.usecase.diary.GetDiaryUseCase
import com.flowerdiary.domain.usecase.diary.SaveDiaryUseCase
import com.flowerdiary.feature.diary.state.*
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

/**
 * 일기 편집 ViewModel (리팩토링됨)
 * SRP: 일기 작성/편집의 핵심 기능만 담당
 * 꽃 선택과 스타일 설정은 별도 ViewModel로 분리
 */
class DiaryEditorViewModelRefactored(
    private val getDiaryUseCase: GetDiaryUseCase,
    private val saveDiaryUseCase: SaveDiaryUseCase
) : BaseViewModel<DiaryEditorStateSimple, DiaryEditorIntentSimple, DiaryEditorEffectSimple>(
    initialState = DiaryEditorStateSimple()
) {
    
    override fun handleIntent(intent: DiaryEditorIntentSimple) {
        when (intent) {
            is DiaryEditorIntentSimple.LoadDiary -> loadDiary(intent.diaryId)
            is DiaryEditorIntentSimple.UpdateTitle -> updateTitle(intent.title)
            is DiaryEditorIntentSimple.UpdateContent -> updateContent(intent.content)
            is DiaryEditorIntentSimple.SelectMood -> selectMood(intent.mood)
            is DiaryEditorIntentSimple.SelectWeather -> selectWeather(intent.weather)
            is DiaryEditorIntentSimple.SetFlower -> setFlower(intent.flowerId)
            is DiaryEditorIntentSimple.SaveDiary -> saveDiary()
            is DiaryEditorIntentSimple.NavigateBack -> navigateBack()
        }
    }
    
    private fun loadDiary(diaryId: DiaryId) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            getDiaryUseCase(diaryId)
                .onSuccess { diary ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            diaryId = diary.id,
                            title = diary.title,
                            content = diary.content,
                            mood = diary.mood,
                            weather = diary.weather,
                            selectedFlowerId = diary.flowerId,
                            isEditMode = true
                        )
                    }
                }
                .onFailure { e ->
                    Logger.error(TAG, "Failed to load diary", e)
                    _state.update { 
                        it.copy(isLoading = false, error = Messages.ERROR_DIARY_NOT_FOUND) 
                    }
                }
        }
    }
    
    private fun updateTitle(title: String) {
        if (title.length <= Config.MAX_TITLE_LENGTH) {
            _state.update { it.copy(title = title, error = null) }
        } else {
            _state.update { 
                it.copy(error = Messages.validationTitleLength(Config.MAX_TITLE_LENGTH)) 
            }
        }
    }
    
    private fun updateContent(content: String) {
        if (content.length <= Config.MAX_CONTENT_LENGTH) {
            _state.update { it.copy(content = content, error = null) }
        } else {
            _state.update { 
                it.copy(error = Messages.validationContentLength(Config.MAX_CONTENT_LENGTH)) 
            }
        }
    }
    
    private fun selectMood(mood: Mood) {
        _state.update { it.copy(mood = mood) }
    }
    
    private fun selectWeather(weather: Weather) {
        _state.update { it.copy(weather = weather) }
    }
    
    private fun setFlower(flowerId: FlowerId?) {
        _state.update { it.copy(selectedFlowerId = flowerId) }
    }
    
    private fun saveDiary() {
        viewModelScope.launch {
            val state = _state.value
            
            // 유효성 검증
            if (state.title.isBlank() && state.content.isBlank()) {
                _state.update { it.copy(error = Messages.VALIDATION_EMPTY_CONTENT) }
                return@launch
            }
            
            if (state.selectedFlowerId == null) {
                _state.update { it.copy(error = Messages.VALIDATION_NO_FLOWER) }
                return@launch
            }
            
            _state.update { it.copy(isSaving = true) }
            
            ExceptionUtil.runCatchingSuspend {
                val diary = Diary(
                    id = state.diaryId ?: DiaryId(Clock.System.now().toEpochMilliseconds().toString()),
                    title = state.title.ifBlank { Config.DEFAULT_TITLE_PLACEHOLDER },
                    content = state.content,
                    mood = state.mood,
                    weather = state.weather,
                    flowerId = state.selectedFlowerId!!,
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                )
                
                saveDiaryUseCase(diary)
                    .onSuccess {
                        _state.update { it.copy(isSaving = false) }
                        val message = if (state.isEditMode) {
                            Messages.SUCCESS_DIARY_UPDATED
                        } else {
                            Messages.SUCCESS_DIARY_SAVED
                        }
                        sendEffect(DiaryEditorEffectSimple.ShowToast(message))
                        sendEffect(DiaryEditorEffectSimple.NavigateBack)
                    }
                    .onFailure { e ->
                        Logger.error(TAG, "Failed to save diary", e)
                        _state.update { 
                            it.copy(isSaving = false, error = Messages.ERROR_GENERAL) 
                        }
                    }
            }
        }
    }
    
    private fun navigateBack() {
        sendEffect(DiaryEditorEffectSimple.NavigateBack)
    }
    
    companion object {
        private const val TAG = "DiaryEditorViewModel"
    }
}
