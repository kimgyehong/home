package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.constants.Messages
import com.flowerdiary.domain.model.*
import com.flowerdiary.feature.diary.manager.*
import com.flowerdiary.feature.diary.state.*

/** 간소화된 일기 편집 ViewModel - Intent 처리와 매니저 조정만 담당 */
class DiaryEditorViewModelSimplified(
    private val dataManager: DiaryDataManager,
    private val stateManager: DiaryEditorStateManager,
    private val styleManager: DiaryStyleManager,
    private val validationManager: DiaryValidationManager,
    private val selectionManager: FlowerSelectionManager
) : BaseViewModel<DiaryEditorState, DiaryEditorIntent, DiaryEditorEffect>() {
    
    init { launch { dataManager.loadTodayFlower().onSuccess { flower -> updateState { stateManager.updateTodayFlower(it, flower) } } } }
    
    override fun createInitialState() = stateManager.createInitialState()
    
    override fun processIntent(intent: DiaryEditorIntent) = when (intent) {
        is DiaryEditorIntent.LoadDiary -> loadDiary(intent.diaryId)
        is DiaryEditorIntent.UpdateTitle -> handleTitleUpdate(intent.title)
        is DiaryEditorIntent.UpdateContent -> handleContentUpdate(intent.content)
        is DiaryEditorIntent.SelectMood -> updateState { stateManager.updateMood(it, intent.mood) }
        is DiaryEditorIntent.SelectWeather -> updateState { stateManager.updateWeather(it, intent.weather) }
        is DiaryEditorIntent.SelectFlower -> updateState { stateManager.updateSelectedFlower(it, intent.flower) }
        is DiaryEditorIntent.UseTodayFlower -> useTodayFlower()
        is DiaryEditorIntent.RequestFlowerRecommendation -> requestFlowerRecommendation()
        is DiaryEditorIntent.ChangeFontFamily -> updateState { styleManager.changeFontFamily(it, intent.fontFamily) }
        is DiaryEditorIntent.ChangeFontColor -> updateState { styleManager.changeFontColor(it, intent.color) }
        is DiaryEditorIntent.ChangeBackgroundTheme -> updateState { styleManager.changeBackgroundTheme(it, intent.theme) }
        is DiaryEditorIntent.SaveDiary -> saveDiary()
        is DiaryEditorIntent.ClearError -> updateState { stateManager.updateErrorState(it, null) }
        is DiaryEditorIntent.NavigateBack -> handleNavigateBack()
    }
    
    override fun handleError(throwable: Throwable) =
        updateState { stateManager.updateErrorState(it, throwable.message ?: Messages.ERROR_GENERAL) }
    
    private fun loadDiary(diaryId: DiaryId) {
        updateState { stateManager.updateLoadingState(it, true) }
        launch {
            dataManager.loadDiary(diaryId).onSuccess { diary ->
                diary?.let {
                    updateState { stateManager.updateForDiaryLoad(it, diary) }
                    dataManager.loadFlowerForDiary(diary.flowerId)
                        .onSuccess { flower -> updateState { stateManager.updateSelectedFlower(it, flower) } }
                } ?: handleError(NoSuchElementException(Messages.ERROR_DIARY_NOT_FOUND))
            }.onFailure { handleError(it) }
        }
    }
    
    private fun handleTitleUpdate(title: String) = if (validationManager.validateTitle(title))
        updateState { stateManager.updateTitle(it, title) } else sendEffect(DiaryEditorEffect.ShowToast(Messages.validationTitleLength(Config.MAX_TITLE_LENGTH)))
    
    private fun handleContentUpdate(content: String) = if (validationManager.validateContent(content))
        updateState { stateManager.updateContent(it, content) } else sendEffect(DiaryEditorEffect.ShowToast(Messages.validationContentLength(Config.MAX_CONTENT_LENGTH)))
    
    private fun useTodayFlower() = currentState.todayFlower?.let { flower ->
        updateState { stateManager.updateSelectedFlower(it, flower) }
        sendEffect(DiaryEditorEffect.ShowToast(Messages.SUCCESS_FLOWER_SELECTED))
    } ?: sendEffect(DiaryEditorEffect.ShowToast(Messages.ERROR_FLOWER_NOT_AVAILABLE))
    
    private fun requestFlowerRecommendation() = launch {
        selectionManager.recommendFlower(currentState.selectedMood, currentState.selectedWeather).onSuccess { flower ->
            flower?.let {
                updateState { stateManager.updateSelectedFlower(it, flower) }
                sendEffect(DiaryEditorEffect.ShowToast(Messages.recommendationFlower(flower.nameKr)))
            } ?: sendEffect(DiaryEditorEffect.ShowToast(Messages.ERROR_NO_RECOMMENDED_FLOWER))
        }
    }
    
    private fun saveDiary() {
        if (!validationManager.canSave(currentState)) {
            sendEffect(DiaryEditorEffect.ShowToast(validationManager.getValidationMessage(currentState)))
            if (!validationManager.hasFlower(currentState)) sendEffect(DiaryEditorEffect.ShowFlowerPicker)
            return
        }
        updateState { stateManager.updateSavingState(it, true) }
        launch {
            val flower = selectionManager.getSelectedOrTodayFlower(currentState)!!
            dataManager.saveDiary(
                currentState.diaryId, currentState.title, currentState.content,
                currentState.selectedMood, currentState.selectedWeather, flower.id,
                currentState.diarySettings.fontFamily, currentState.diarySettings.fontColor,
                currentState.diarySettings.backgroundTheme, currentState.isEditMode
            ).onSuccess {
                sendEffect(DiaryEditorEffect.ShowToast(if (currentState.isEditMode) Messages.SUCCESS_DIARY_UPDATED else Messages.SUCCESS_DIARY_SAVED))
                sendEffect(DiaryEditorEffect.NavigateBack)
            }.onFailure { handleError(it) }
        }
    }
    
    private fun handleNavigateBack() = if (currentState.canSave && !currentState.isSaving)
        sendEffect(DiaryEditorEffect.ShowUnsavedChangesDialog) else sendEffect(DiaryEditorEffect.NavigateBack)
}