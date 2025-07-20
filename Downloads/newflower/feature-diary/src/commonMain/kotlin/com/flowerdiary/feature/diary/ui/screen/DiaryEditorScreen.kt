package com.flowerdiary.feature.diary.ui.screen

import com.flowerdiary.core.types.FlowerId
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.store.action.DiaryEditorAction
import com.flowerdiary.feature.diary.store.state.DiaryEditorState
import com.flowerdiary.feature.diary.store.state.EditorMode
import kotlinx.datetime.LocalDate

data class DiaryEditorScreen(
  val state: DiaryEditorState,
  val onAction: (DiaryEditorAction) -> Unit
) {

  fun initialize() {
    onAction(DiaryEditorAction.Initialize)
  }

  fun loadDiary(diaryId: DiaryId) {
    onAction(DiaryEditorAction.LoadDiary(diaryId))
  }

  fun createNewDiary(date: LocalDate, birthFlowerId: FlowerId? = null) {
    onAction(DiaryEditorAction.CreateNewDiary(date, birthFlowerId))
  }

  fun updateTitle(title: String) {
    onAction(DiaryEditorAction.UpdateTitle(title))
  }

  fun updateContent(content: String) {
    onAction(DiaryEditorAction.UpdateContent(content))
  }

  fun updateDate(date: LocalDate) {
    onAction(DiaryEditorAction.UpdateDate(date))
  }

  fun updateBirthFlower(flowerId: FlowerId?) {
    onAction(DiaryEditorAction.UpdateBirthFlower(flowerId))
  }

  fun saveDiary() {
    onAction(DiaryEditorAction.SaveDiary)
  }

  fun publishDiary() {
    onAction(DiaryEditorAction.PublishDiary)
  }

  fun archiveDiary() {
    onAction(DiaryEditorAction.ArchiveDiary)
  }

  fun deleteDiary() {
    onAction(DiaryEditorAction.DeleteDiary)
  }

  fun validateContent() {
    onAction(DiaryEditorAction.ValidateContent)
  }

  fun clearValidationErrors() {
    onAction(DiaryEditorAction.ClearValidationErrors)
  }

  fun resetChanges() {
    onAction(DiaryEditorAction.ResetChanges)
  }

  fun discardChanges() {
    onAction(DiaryEditorAction.DiscardChanges)
  }

  fun enableEditMode() {
    onAction(DiaryEditorAction.EnableEditMode)
  }

  fun enableViewMode() {
    onAction(DiaryEditorAction.EnableViewMode)
  }

  fun startAutoSave() {
    onAction(DiaryEditorAction.StartAutoSave)
  }

  fun stopAutoSave() {
    onAction(DiaryEditorAction.StopAutoSave)
  }

  fun retryLoad() {
    onAction(DiaryEditorAction.RetryLoad)
  }

  fun clearError() {
    onAction(DiaryEditorAction.ClearError)
  }

  val diary: Diary?
    get() = state.diary

  val title: String
    get() = state.title

  val content: String
    get() = state.content

  val date: LocalDate
    get() = state.date

  val mode: EditorMode
    get() = state.mode

  val isLoading: Boolean
    get() = state.isLoading

  val isSaving: Boolean
    get() = state.isSaving

  val isAutoSaving: Boolean
    get() = state.isAutoSaving

  val hasUnsavedChanges: Boolean
    get() = state.hasUnsavedChanges

  val hasValidationErrors: Boolean
    get() = state.validationErrors.isNotEmpty()

  val validationErrors: List<String>
    get() = state.validationErrors

  val hasError: Boolean
    get() = state.hasError()

  val errorMessage: String?
    get() = state.errorMessage

  val canSave: Boolean
    get() = state.canSave()

  val canEdit: Boolean
    get() = mode == EditorMode.EDIT

  val canView: Boolean
    get() = mode == EditorMode.VIEW

  fun getLastSavedTime(): Long? {
    return state.lastSavedAt
  }

  fun getWordCount(): Int {
    return content.split(Regex("\\s+")).filter { it.isNotBlank() }.size
  }

  fun getCharacterCount(): Int {
    return content.length
  }

  fun getCharacterCountWithoutSpaces(): Int {
    return content.replace(Regex("\\s"), "").length
  }
}