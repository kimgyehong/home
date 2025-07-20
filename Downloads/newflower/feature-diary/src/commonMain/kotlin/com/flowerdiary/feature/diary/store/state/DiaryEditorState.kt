package com.flowerdiary.feature.diary.store.state

import com.flowerdiary.core.model.BirthFlower
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DiaryEditorState(
  val diary: Diary? = null,
  val title: String = "",
  val content: String = "",
  val date: LocalDate? = null,
  val birthFlower: BirthFlower? = null,
  val isLoading: Boolean = false,
  val isSaving: Boolean = false,
  val isAutoSaving: Boolean = false,
  val lastSavedAt: Long? = null,
  val hasUnsavedChanges: Boolean = false,
  val errorMessage: String? = null,
  val mode: EditorMode = EditorMode.CREATE,
  val validationErrors: List<String> = emptyList()
) {

  fun isEditMode(): Boolean {
    return mode == EditorMode.EDIT
  }

  fun isCreateMode(): Boolean {
    return mode == EditorMode.CREATE
  }

  fun isViewMode(): Boolean {
    return mode == EditorMode.VIEW
  }

  fun canSave(): Boolean {
    return !isSaving && hasUnsavedChanges && title.isNotBlank()
  }

  fun canAutoSave(): Boolean {
    return !isSaving && !isAutoSaving && hasUnsavedChanges
  }

  fun hasContent(): Boolean {
    return title.isNotBlank() || content.isNotBlank()
  }

  fun isEmpty(): Boolean {
    return title.isBlank() && content.isBlank()
  }

  fun hasValidationErrors(): Boolean {
    return validationErrors.isNotEmpty()
  }

  fun hasError(): Boolean {
    return errorMessage != null
  }

  fun getCharacterCount(): Int {
    return content.length
  }

  fun getWordCount(): Int {
    return if (content.isBlank()) 0 else content.trim().split(Regex("\\s+")).size
  }

  fun isRecentlySaved(): Boolean {
    return lastSavedAt?.let { 
      System.currentTimeMillis() - it < RECENT_SAVE_THRESHOLD 
    } ?: false
  }

  companion object {
    private const val RECENT_SAVE_THRESHOLD = 30000 // 30초
    
    fun create(date: LocalDate, birthFlower: BirthFlower? = null): DiaryEditorState {
      return DiaryEditorState(
        date = date,
        birthFlower = birthFlower,
        mode = EditorMode.CREATE
      )
    }
    
    fun edit(diary: Diary): DiaryEditorState {
      return DiaryEditorState(
        diary = diary,
        title = diary.entry.title,
        content = diary.entry.content,
        date = diary.entry.date,
        mode = EditorMode.EDIT
      )
    }
    
    fun view(diary: Diary): DiaryEditorState {
      return DiaryEditorState(
        diary = diary,
        title = diary.entry.title,
        content = diary.entry.content,
        date = diary.entry.date,
        mode = EditorMode.VIEW
      )
    }
    
    fun loading(): DiaryEditorState {
      return DiaryEditorState(isLoading = true)
    }
    
    fun error(message: String): DiaryEditorState {
      return DiaryEditorState(errorMessage = message)
    }
  }
}

@Serializable
enum class EditorMode {
  CREATE,
  EDIT,
  VIEW
}