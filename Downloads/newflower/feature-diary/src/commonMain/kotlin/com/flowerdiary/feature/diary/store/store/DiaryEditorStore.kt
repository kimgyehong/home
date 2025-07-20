package com.flowerdiary.feature.diary.store.store

import com.flowerdiary.feature.diary.domain.usecase.CreateDiaryUseCase
import com.flowerdiary.feature.diary.domain.usecase.DeleteDiaryUseCase
import com.flowerdiary.feature.diary.domain.usecase.GetDiaryByIdUseCase
import com.flowerdiary.feature.diary.domain.usecase.SaveDiaryUseCase
import com.flowerdiary.feature.diary.domain.usecase.UpdateDiaryUseCase
import com.flowerdiary.feature.diary.domain.validator.DiaryValidator
import com.flowerdiary.feature.diary.store.action.DiaryEditorAction
import com.flowerdiary.feature.diary.store.state.DiaryEditorState
import com.flowerdiary.feature.diary.store.state.EditorMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiaryEditorStore(
  private val createDiaryUseCase: CreateDiaryUseCase,
  private val updateDiaryUseCase: UpdateDiaryUseCase,
  private val deleteDiaryUseCase: DeleteDiaryUseCase,
  private val getDiaryByIdUseCase: GetDiaryByIdUseCase,
  private val saveDiaryUseCase: SaveDiaryUseCase,
  private val diaryValidator: DiaryValidator,
  private val coroutineScope: CoroutineScope
) {

  private val _state = MutableStateFlow(DiaryEditorState())
  val state: StateFlow<DiaryEditorState> = _state.asStateFlow()

  private var autoSaveJob: Job? = null

  fun dispatch(action: DiaryEditorAction) {
    coroutineScope.launch {
      when (action) {
        is DiaryEditorAction.Initialize -> initialize()
        is DiaryEditorAction.LoadDiary -> loadDiary(action.diaryId)
        is DiaryEditorAction.CreateNewDiary -> createNewDiary(action.date, action.birthFlowerId)
        is DiaryEditorAction.SetMode -> setMode(action.mode)
        is DiaryEditorAction.UpdateTitle -> updateTitle(action.title)
        is DiaryEditorAction.UpdateContent -> updateContent(action.content)
        is DiaryEditorAction.UpdateDate -> updateDate(action.date)
        is DiaryEditorAction.UpdateBirthFlower -> updateBirthFlower(action.flowerId)
        is DiaryEditorAction.SaveDiary -> saveDiary()
        is DiaryEditorAction.AutoSaveDiary -> autoSaveDiary()
        is DiaryEditorAction.PublishDiary -> publishDiary()
        is DiaryEditorAction.ArchiveDiary -> archiveDiary()
        is DiaryEditorAction.DeleteDiary -> deleteDiary()
        is DiaryEditorAction.ValidateContent -> validateContent()
        is DiaryEditorAction.ClearValidationErrors -> clearValidationErrors()
        is DiaryEditorAction.MarkAsChanged -> markAsChanged()
        is DiaryEditorAction.MarkAsSaved -> markAsSaved()
        is DiaryEditorAction.ResetChanges -> resetChanges()
        is DiaryEditorAction.DiscardChanges -> discardChanges()
        is DiaryEditorAction.ClearError -> clearError()
        is DiaryEditorAction.RetryLoad -> retryLoad()
        is DiaryEditorAction.RetryAutoSave -> retryAutoSave()
        is DiaryEditorAction.ShowValidationError -> showValidationError(action.message)
        is DiaryEditorAction.StartAutoSave -> startAutoSave()
        is DiaryEditorAction.StopAutoSave -> stopAutoSave()
        is DiaryEditorAction.EnableEditMode -> enableEditMode()
        is DiaryEditorAction.EnableViewMode -> enableViewMode()
      }
    }
  }

  private fun initialize() {
    _state.value = DiaryEditorState()
  }

  private suspend fun loadDiary(diaryId: com.flowerdiary.feature.diary.domain.model.DiaryId) {
    _state.value = DiaryEditorState.loading()
    
    val result = getDiaryByIdUseCase.execute(diaryId)
    
    result.fold(
      onSuccess = { diary ->
        _state.value = DiaryEditorState.edit(diary)
      },
      onFailure = { error ->
        _state.value = DiaryEditorState.error(error.message ?: "Failed to load diary")
      }
    )
  }

  private suspend fun createNewDiary(
    date: kotlinx.datetime.LocalDate,
    birthFlowerId: com.flowerdiary.core.types.FlowerId?
  ) {
    _state.value = DiaryEditorState.loading()
    
    val result = createDiaryUseCase.execute(date)
    
    result.fold(
      onSuccess = { diary ->
        _state.value = DiaryEditorState.create(date).copy(diary = diary)
      },
      onFailure = { error ->
        _state.value = DiaryEditorState.error(error.message ?: "Failed to create diary")
      }
    )
  }

  private fun setMode(mode: EditorMode) {
    _state.value = _state.value.copy(mode = mode)
  }

  private fun updateTitle(title: String) {
    val currentState = _state.value
    if (currentState.title != title) {
      _state.value = currentState.copy(
        title = title,
        hasUnsavedChanges = true
      )
      scheduleAutoSave()
    }
  }

  private fun updateContent(content: String) {
    val currentState = _state.value
    if (currentState.content != content) {
      _state.value = currentState.copy(
        content = content,
        hasUnsavedChanges = true
      )
      scheduleAutoSave()
    }
  }

  private fun updateDate(date: kotlinx.datetime.LocalDate) {
    _state.value = _state.value.copy(
      date = date,
      hasUnsavedChanges = true
    )
  }

  private suspend fun updateBirthFlower(flowerId: com.flowerdiary.core.types.FlowerId?) {
    // Implementation to update birth flower
    _state.value = _state.value.copy(hasUnsavedChanges = true)
  }

  private suspend fun saveDiary() {
    val currentState = _state.value
    if (!currentState.canSave()) return
    
    _state.value = currentState.copy(isSaving = true, errorMessage = null)
    
    try {
      val diary = currentState.diary?.updateEntry(currentState.title, currentState.content)
        ?: return
      
      val result = saveDiaryUseCase.execute(diary)
      
      result.fold(
        onSuccess = { savedDiary ->
          _state.value = _state.value.copy(
            diary = savedDiary,
            isSaving = false,
            hasUnsavedChanges = false,
            lastSavedAt = System.currentTimeMillis()
          )
        },
        onFailure = { error ->
          _state.value = _state.value.copy(
            isSaving = false,
            errorMessage = error.message
          )
        }
      )
    } catch (exception: Exception) {
      _state.value = _state.value.copy(
        isSaving = false,
        errorMessage = exception.message
      )
    }
  }

  private suspend fun autoSaveDiary() {
    val currentState = _state.value
    if (!currentState.canAutoSave()) return
    
    _state.value = currentState.copy(isAutoSaving = true)
    
    try {
      val diary = currentState.diary?.updateEntry(currentState.title, currentState.content)
        ?: return
      
      val result = saveDiaryUseCase.autoSave(diary)
      
      result.fold(
        onSuccess = { savedDiary ->
          _state.value = _state.value.copy(
            diary = savedDiary,
            isAutoSaving = false,
            hasUnsavedChanges = false,
            lastSavedAt = System.currentTimeMillis()
          )
        },
        onFailure = {
          _state.value = _state.value.copy(isAutoSaving = false)
        }
      )
    } catch (exception: Exception) {
      _state.value = _state.value.copy(isAutoSaving = false)
    }
  }

  private suspend fun publishDiary() {
    val diaryId = _state.value.diary?.id ?: return
    _state.value = _state.value.copy(isLoading = true)
    
    val result = updateDiaryUseCase.publishDiary(diaryId)
    
    result.fold(
      onSuccess = { publishedDiary ->
        _state.value = _state.value.copy(
          diary = publishedDiary,
          isLoading = false
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(
          isLoading = false,
          errorMessage = error.message
        )
      }
    )
  }

  private suspend fun archiveDiary() {
    val diaryId = _state.value.diary?.id ?: return
    _state.value = _state.value.copy(isLoading = true)
    
    val result = updateDiaryUseCase.archiveDiary(diaryId)
    
    result.fold(
      onSuccess = { archivedDiary ->
        _state.value = _state.value.copy(
          diary = archivedDiary,
          isLoading = false
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(
          isLoading = false,
          errorMessage = error.message
        )
      }
    )
  }

  private suspend fun deleteDiary() {
    val diaryId = _state.value.diary?.id ?: return
    
    val result = deleteDiaryUseCase.execute(diaryId)
    
    result.fold(
      onSuccess = {
        _state.value = DiaryEditorState()
      },
      onFailure = { error ->
        _state.value = _state.value.copy(errorMessage = error.message)
      }
    )
  }

  private suspend fun validateContent() {
    val currentState = _state.value
    val diary = currentState.diary ?: return
    
    val validationResult = diaryValidator.validateForSave(diary)
    
    if (validationResult.isValid) {
      _state.value = currentState.copy(validationErrors = emptyList())
    } else {
      _state.value = currentState.copy(
        validationErrors = listOf(validationResult.errorMessage)
      )
    }
  }

  private fun clearValidationErrors() {
    _state.value = _state.value.copy(validationErrors = emptyList())
  }

  private fun markAsChanged() {
    _state.value = _state.value.copy(hasUnsavedChanges = true)
  }

  private fun markAsSaved() {
    _state.value = _state.value.copy(
      hasUnsavedChanges = false,
      lastSavedAt = System.currentTimeMillis()
    )
  }

  private fun resetChanges() {
    val diary = _state.value.diary ?: return
    
    _state.value = _state.value.copy(
      title = diary.entry.title,
      content = diary.entry.content,
      hasUnsavedChanges = false,
      validationErrors = emptyList()
    )
  }

  private fun discardChanges() {
    _state.value = DiaryEditorState()
  }

  private fun clearError() {
    _state.value = _state.value.copy(errorMessage = null)
  }

  private suspend fun retryLoad() {
    val diaryId = _state.value.diary?.id ?: return
    loadDiary(diaryId)
  }

  private suspend fun retryAutoSave() {
    autoSaveDiary()
  }

  private fun showValidationError(message: String) {
    val currentErrors = _state.value.validationErrors.toMutableList()
    currentErrors.add(message)
    _state.value = _state.value.copy(validationErrors = currentErrors)
  }

  private fun startAutoSave() {
    scheduleAutoSave()
  }

  private fun stopAutoSave() {
    autoSaveJob?.cancel()
    autoSaveJob = null
  }

  private fun enableEditMode() {
    _state.value = _state.value.copy(mode = EditorMode.EDIT)
  }

  private fun enableViewMode() {
    _state.value = _state.value.copy(mode = EditorMode.VIEW)
  }

  private fun scheduleAutoSave() {
    autoSaveJob?.cancel()
    autoSaveJob = coroutineScope.launch {
      delay(AUTO_SAVE_DELAY)
      autoSaveDiary()
    }
  }

  companion object {
    private const val AUTO_SAVE_DELAY = 3000L // 3초
  }
}