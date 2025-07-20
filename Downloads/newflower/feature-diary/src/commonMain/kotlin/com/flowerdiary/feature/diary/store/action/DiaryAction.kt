package com.flowerdiary.feature.diary.store.action

import com.flowerdiary.feature.diary.domain.model.BgmTrack
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiarySettings
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
sealed interface DiaryAction

@Serializable
sealed interface DiaryListAction : DiaryAction {
  
  @Serializable
  data object LoadDiaries : DiaryListAction
  
  @Serializable
  data object RefreshDiaries : DiaryListAction
  
  @Serializable
  data object LoadMoreDiaries : DiaryListAction
  
  @Serializable
  data class SearchDiaries(val query: String) : DiaryListAction
  
  @Serializable
  data class FilterByStatus(val status: String?) : DiaryListAction
  
  @Serializable
  data class SortDiaries(val sortOrder: String) : DiaryListAction
  
  @Serializable
  data class SelectDiary(val diaryId: DiaryId) : DiaryListAction
  
  @Serializable
  data class DeleteDiary(val diaryId: DiaryId) : DiaryListAction
  
  @Serializable
  data object ClearSearch : DiaryListAction
  
  @Serializable
  data object ClearError : DiaryListAction
}

@Serializable
sealed interface DiaryEditorAction : DiaryAction {
  
  @Serializable
  data class LoadDiary(val diaryId: DiaryId) : DiaryEditorAction
  
  @Serializable
  data class CreateNewDiary(val date: LocalDate) : DiaryEditorAction
  
  @Serializable
  data class UpdateTitle(val title: String) : DiaryEditorAction
  
  @Serializable
  data class UpdateContent(val content: String) : DiaryEditorAction
  
  @Serializable
  data object SaveDiary : DiaryEditorAction
  
  @Serializable
  data object AutoSaveDiary : DiaryEditorAction
  
  @Serializable
  data object PublishDiary : DiaryEditorAction
  
  @Serializable
  data object ArchiveDiary : DiaryEditorAction
  
  @Serializable
  data object DeleteDiary : DiaryEditorAction
  
  @Serializable
  data object ClearError : DiaryEditorAction
  
  @Serializable
  data object ResetChanges : DiaryEditorAction
}

@Serializable
sealed interface DiarySettingsAction : DiaryAction {
  
  @Serializable
  data object LoadSettings : DiarySettingsAction
  
  @Serializable
  data class UpdateBgmTrack(val track: BgmTrack) : DiarySettingsAction
  
  @Serializable
  data class UpdateBgmEnabled(val enabled: Boolean) : DiarySettingsAction
  
  @Serializable
  data class UpdateAutoSave(val enabled: Boolean, val interval: Int) : DiarySettingsAction
  
  @Serializable
  data class UpdateFontSize(val fontSize: Float) : DiarySettingsAction
  
  @Serializable
  data class UpdateBirthFlowerBackground(val enabled: Boolean) : DiarySettingsAction
  
  @Serializable
  data object SaveSettings : DiarySettingsAction
  
  @Serializable
  data object ResetToDefaults : DiarySettingsAction
  
  @Serializable
  data object ClearError : DiarySettingsAction
}