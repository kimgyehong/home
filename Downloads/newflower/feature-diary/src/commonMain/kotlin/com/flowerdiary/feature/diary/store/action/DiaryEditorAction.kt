package com.flowerdiary.feature.diary.store.action

import com.flowerdiary.core.types.FlowerId
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.store.state.EditorMode
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
sealed class DiaryEditorAction {

  @Serializable
  data object Initialize : DiaryEditorAction()

  @Serializable
  data class LoadDiary(val diaryId: DiaryId) : DiaryEditorAction()

  @Serializable
  data class CreateNewDiary(
    val date: LocalDate,
    val birthFlowerId: FlowerId? = null
  ) : DiaryEditorAction()

  @Serializable
  data class SetMode(val mode: EditorMode) : DiaryEditorAction()

  @Serializable
  data class UpdateTitle(val title: String) : DiaryEditorAction()

  @Serializable
  data class UpdateContent(val content: String) : DiaryEditorAction()

  @Serializable
  data class UpdateDate(val date: LocalDate) : DiaryEditorAction()

  @Serializable
  data class UpdateBirthFlower(val flowerId: FlowerId?) : DiaryEditorAction()

  @Serializable
  data object SaveDiary : DiaryEditorAction()

  @Serializable
  data object AutoSaveDiary : DiaryEditorAction()

  @Serializable
  data object PublishDiary : DiaryEditorAction()

  @Serializable
  data object ArchiveDiary : DiaryEditorAction()

  @Serializable
  data object DeleteDiary : DiaryEditorAction()

  @Serializable
  data object ValidateContent : DiaryEditorAction()

  @Serializable
  data object ClearValidationErrors : DiaryEditorAction()

  @Serializable
  data object MarkAsChanged : DiaryEditorAction()

  @Serializable
  data object MarkAsSaved : DiaryEditorAction()

  @Serializable
  data object ResetChanges : DiaryEditorAction()

  @Serializable
  data object DiscardChanges : DiaryEditorAction()

  @Serializable
  data object ClearError : DiaryEditorAction()

  @Serializable
  data object RetryLoad : DiaryEditorAction()

  @Serializable
  data object RetryAutoSave : DiaryEditorAction()

  @Serializable
  data class ShowValidationError(val message: String) : DiaryEditorAction()

  @Serializable
  data object StartAutoSave : DiaryEditorAction()

  @Serializable
  data object StopAutoSave : DiaryEditorAction()

  @Serializable
  data object EnableEditMode : DiaryEditorAction()

  @Serializable
  data object EnableViewMode : DiaryEditorAction()
}