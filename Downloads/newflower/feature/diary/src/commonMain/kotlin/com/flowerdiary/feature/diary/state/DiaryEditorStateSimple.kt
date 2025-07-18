package com.flowerdiary.feature.diary.state

import com.flowerdiary.domain.model.*

/**
 * 일기 편집 상태 (단순화됨)
 * 핵심 일기 편집 기능만 포함
 */
data class DiaryEditorStateSimple(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isEditMode: Boolean = false,
    val diaryId: DiaryId? = null,
    val title: String = "",
    val content: String = "",
    val mood: Mood = Mood.PEACEFUL,
    val weather: Weather = Weather.SUNNY,
    val selectedFlowerId: FlowerId? = null,
    val error: String? = null
)

/**
 * 일기 편집 의도 (단순화됨)
 */
sealed interface DiaryEditorIntentSimple {
    data class LoadDiary(val diaryId: DiaryId) : DiaryEditorIntentSimple
    data class UpdateTitle(val title: String) : DiaryEditorIntentSimple
    data class UpdateContent(val content: String) : DiaryEditorIntentSimple
    data class SelectMood(val mood: Mood) : DiaryEditorIntentSimple
    data class SelectWeather(val weather: Weather) : DiaryEditorIntentSimple
    data class SetFlower(val flowerId: FlowerId?) : DiaryEditorIntentSimple
    data object SaveDiary : DiaryEditorIntentSimple
    data object NavigateBack : DiaryEditorIntentSimple
}

/**
 * 일기 편집 효과 (단순화됨)
 */
sealed interface DiaryEditorEffectSimple {
    data class ShowToast(val message: String) : DiaryEditorEffectSimple
    data object NavigateBack : DiaryEditorEffectSimple
}
