package com.flowerdiary.feature.diary.ui.component

import com.flowerdiary.core.types.FlowerId
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.store.state.EditorMode
import kotlinx.datetime.LocalDate

data class DiaryEditor(
  val diary: Diary?,
  val title: String,
  val content: String,
  val date: LocalDate,
  val mode: EditorMode,
  val hasUnsavedChanges: Boolean,
  val isAutoSaving: Boolean,
  val validationErrors: List<String>,
  val onTitleChange: (String) -> Unit = {},
  val onContentChange: (String) -> Unit = {},
  val onDateChange: (LocalDate) -> Unit = {},
  val onFlowerChange: (FlowerId?) -> Unit = {},
  val onSave: () -> Unit = {},
  val onCancel: () -> Unit = {},
  val onModeChange: (EditorMode) -> Unit = {}
) {

  val isEditing: Boolean
    get() = mode == EditorMode.EDIT

  val isViewing: Boolean
    get() = mode == EditorMode.VIEW

  val canSave: Boolean
    get() = hasUnsavedChanges && title.isNotBlank() && validationErrors.isEmpty()

  val canEdit: Boolean
    get() = diary?.status != com.flowerdiary.feature.diary.domain.model.DiaryStatus.Archived

  val wordCount: Int
    get() = content.split(Regex("\\s+")).filter { it.isNotBlank() }.size

  val characterCount: Int
    get() = content.length

  val lineCount: Int
    get() = content.lines().size

  val hasValidationErrors: Boolean
    get() = validationErrors.isNotEmpty()

  val firstValidationError: String?
    get() = validationErrors.firstOrNull()

  val titlePlaceholder: String
    get() = "Enter diary title..."

  val contentPlaceholder: String
    get() = "Write your thoughts here..."

  val saveButtonText: String
    get() = when {
      isAutoSaving -> "Auto-saving..."
      hasUnsavedChanges -> "Save Changes"
      else -> "Saved"
    }

  val modeButtonText: String
    get() = if (isEditing) "Preview" else "Edit"

  fun handleTitleChange(newTitle: String) {
    if (isEditing && newTitle != title) {
      onTitleChange(newTitle)
    }
  }

  fun handleContentChange(newContent: String) {
    if (isEditing && newContent != content) {
      onContentChange(newContent)
    }
  }

  fun handleDateChange(newDate: LocalDate) {
    if (isEditing && newDate != date) {
      onDateChange(newDate)
    }
  }

  fun handleFlowerChange(flowerId: FlowerId?) {
    if (isEditing) {
      onFlowerChange(flowerId)
    }
  }

  fun handleSave() {
    if (canSave) {
      onSave()
    }
  }

  fun handleCancel() {
    onCancel()
  }

  fun toggleMode() {
    val newMode = if (isEditing) EditorMode.VIEW else EditorMode.EDIT
    onModeChange(newMode)
  }

  fun getFormattedDate(): String {
    return "${date.year}.${date.monthNumber.toString().padStart(2, '0')}.${date.dayOfMonth.toString().padStart(2, '0')}"
  }

  fun getCharacterCountText(): String {
    return "$characterCount characters"
  }

  fun getWordCountText(): String {
    return "$wordCount words"
  }

  fun getLineCountText(): String {
    return "$lineCount lines"
  }

  fun getReadingTime(): String {
    val minutes = (wordCount / WORDS_PER_MINUTE).coerceAtLeast(1)
    return "${minutes}min read"
  }

  fun getValidationSummary(): String {
    return when (validationErrors.size) {
      0 -> "No errors"
      1 -> "1 error"
      else -> "${validationErrors.size} errors"
    }
  }

  fun isContentEmpty(): Boolean {
    return content.isBlank()
  }

  fun isTitleEmpty(): Boolean {
    return title.isBlank()
  }

  fun hasMinimumContent(): Boolean {
    return wordCount >= MIN_WORD_COUNT
  }

  fun isValidForSave(): Boolean {
    return title.isNotBlank() && 
           content.isNotBlank() && 
           validationErrors.isEmpty()
  }

  fun getCursorPosition(text: String, position: Int): Int {
    return position.coerceIn(0, text.length)
  }

  fun insertTextAtCursor(text: String, insertText: String, cursorPosition: Int): Pair<String, Int> {
    val newText = text.substring(0, cursorPosition) + insertText + text.substring(cursorPosition)
    val newCursorPosition = cursorPosition + insertText.length
    return Pair(newText, newCursorPosition)
  }

  companion object {
    private const val WORDS_PER_MINUTE = 200
    private const val MIN_WORD_COUNT = 10
    
    fun empty(): DiaryEditor {
      return DiaryEditor(
        diary = null,
        title = "",
        content = "",
        date = LocalDate(2024, 1, 1),
        mode = EditorMode.EDIT,
        hasUnsavedChanges = false,
        isAutoSaving = false,
        validationErrors = emptyList()
      )
    }
  }
}