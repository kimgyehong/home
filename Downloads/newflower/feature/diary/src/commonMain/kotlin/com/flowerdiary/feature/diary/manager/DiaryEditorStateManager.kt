package com.flowerdiary.feature.diary.manager

import com.flowerdiary.domain.model.*
import com.flowerdiary.feature.diary.state.DiaryEditorState
import com.flowerdiary.feature.diary.state.content.DiaryContentState
import com.flowerdiary.feature.diary.state.content.DiaryCustomizationState
import com.flowerdiary.feature.diary.state.content.DiaryFlowerState

/**
 * 일기 편집기 상태 관리자
 * SRP: 일기 편집기의 상태 관리 로직만 담당
 */
class DiaryEditorStateManager {
    
    /**
     * 초기 상태 생성
     */
    fun createInitialState(): DiaryEditorState {
        return DiaryEditorState()
    }
    
    /**
     * 오늘의 꽃 상태 업데이트
     */
    fun updateTodayFlower(
        currentState: DiaryEditorState,
        flower: BirthFlower?
    ): DiaryEditorState {
        return currentState.copy(
            flower = currentState.flower.copy(todayFlower = flower)
        )
    }
    
    /**
     * 일기 로드 상태 업데이트
     */
    fun updateForDiaryLoad(
        currentState: DiaryEditorState,
        diary: Diary
    ): DiaryEditorState {
        return currentState.copy(
            isLoading = false,
            isEditMode = true,
            diaryId = diary.id,
            content = DiaryContentState(
                title = diary.title,
                content = diary.content,
                selectedMood = diary.mood,
                selectedWeather = diary.weather
            ),
            customization = DiaryCustomizationState(
                diarySettings = DiarySettings(
                    fontFamily = diary.fontFamily,
                    fontColor = diary.fontColor,
                    backgroundTheme = diary.backgroundTheme
                )
            )
        )
    }
    
    /**
     * 선택된 꽃 상태 업데이트
     */
    fun updateSelectedFlower(
        currentState: DiaryEditorState,
        flower: BirthFlower?
    ): DiaryEditorState {
        return currentState.copy(
            flower = currentState.flower.copy(selectedFlower = flower)
        )
    }
    
    /**
     * 로딩 상태 업데이트
     */
    fun updateLoadingState(
        currentState: DiaryEditorState,
        isLoading: Boolean
    ): DiaryEditorState {
        return currentState.copy(isLoading = isLoading)
    }
    
    /**
     * 저장 상태 업데이트
     */
    fun updateSavingState(
        currentState: DiaryEditorState,
        isSaving: Boolean
    ): DiaryEditorState {
        return currentState.copy(isSaving = isSaving)
    }
    
    /**
     * 에러 상태 업데이트
     */
    fun updateErrorState(
        currentState: DiaryEditorState,
        error: String?
    ): DiaryEditorState {
        return currentState.copy(
            error = error,
            isLoading = false,
            isSaving = false
        )
    }
    
    /**
     * 제목 업데이트
     */
    fun updateTitle(
        currentState: DiaryEditorState,
        title: String
    ): DiaryEditorState {
        return currentState.copy(
            content = currentState.content.copy(title = title)
        )
    }
    
    /**
     * 내용 업데이트
     */
    fun updateContent(
        currentState: DiaryEditorState,
        newContent: String
    ): DiaryEditorState {
        return currentState.copy(
            content = currentState.content.copy(content = newContent)
        )
    }
    
    /**
     * 기분 업데이트
     */
    fun updateMood(
        currentState: DiaryEditorState,
        mood: Mood
    ): DiaryEditorState {
        return currentState.copy(
            content = currentState.content.copy(selectedMood = mood)
        )
    }
    
    /**
     * 날씨 업데이트
     */
    fun updateWeather(
        currentState: DiaryEditorState,
        weather: Weather
    ): DiaryEditorState {
        return currentState.copy(
            content = currentState.content.copy(selectedWeather = weather)
        )
    }
}