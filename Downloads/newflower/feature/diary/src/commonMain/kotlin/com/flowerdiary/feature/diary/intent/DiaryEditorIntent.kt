package com.flowerdiary.feature.diary.intent

import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.DiaryId
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather

/**
 * 일기 편집 UI 이벤트 - Context7 KMP 극한 압축
 * SRP: 카테고리별로 분리된 사용자 의도 표현
 */
sealed interface DiaryEditorIntent {
    
    sealed interface Content : DiaryEditorIntent {
        data class UpdateTitle(val title: String) : Content
        data class UpdateContent(val content: String) : Content
    }
    
    sealed interface Selection : DiaryEditorIntent {
        data class SelectMood(val mood: Mood) : Selection
        data class SelectWeather(val weather: Weather) : Selection
        data class SelectFlower(val flower: BirthFlower) : Selection
    }
    
    sealed interface Customization : DiaryEditorIntent {
        data class ChangeFontFamily(val fontFamily: String) : Customization
        data class ChangeFontColor(val color: Long) : Customization
        data class ChangeBackgroundTheme(val theme: String) : Customization
    }
    
    sealed interface Action : DiaryEditorIntent {
        data class LoadDiary(val diaryId: DiaryId) : Action
        data object SaveDiary : Action
        data object UseTodayFlower : Action
        data object RequestFlowerRecommendation : Action
        data object ClearError : Action
        data object NavigateBack : Action
    }
}