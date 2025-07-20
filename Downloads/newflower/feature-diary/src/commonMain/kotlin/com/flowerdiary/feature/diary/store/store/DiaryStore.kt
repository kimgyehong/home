package com.flowerdiary.feature.diary.store.store

import com.flowerdiary.feature.diary.domain.usecase.CreateDiaryUseCase
import com.flowerdiary.feature.diary.domain.usecase.DeleteDiaryUseCase
import com.flowerdiary.feature.diary.domain.usecase.GetDiariesUseCase
import com.flowerdiary.feature.diary.domain.usecase.GetDiaryByIdUseCase
import com.flowerdiary.feature.diary.domain.usecase.SaveDiaryUseCase
import com.flowerdiary.feature.diary.domain.usecase.UpdateDiaryUseCase
import com.flowerdiary.feature.diary.store.action.DiaryAction
import com.flowerdiary.feature.diary.store.state.DiaryDetailState
import com.flowerdiary.feature.diary.store.state.DiaryEditorState
import com.flowerdiary.feature.diary.store.state.DiaryListState
import com.flowerdiary.feature.diary.store.state.DiarySettingsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiaryStore(
  private val createDiaryUseCase: CreateDiaryUseCase,
  private val updateDiaryUseCase: UpdateDiaryUseCase,
  private val deleteDiaryUseCase: DeleteDiaryUseCase,
  private val getDiariesUseCase: GetDiariesUseCase,
  private val getDiaryByIdUseCase: GetDiaryByIdUseCase,
  private val saveDiaryUseCase: SaveDiaryUseCase,
  private val coroutineScope: CoroutineScope
) {

  private val _listState = MutableStateFlow(DiaryListState())
  val listState: StateFlow<DiaryListState> = _listState.asStateFlow()

  private val _editorState = MutableStateFlow(DiaryEditorState())
  val editorState: StateFlow<DiaryEditorState> = _editorState.asStateFlow()

  private val _detailState = MutableStateFlow(DiaryDetailState())
  val detailState: StateFlow<DiaryDetailState> = _detailState.asStateFlow()

  private val _settingsState = MutableStateFlow(DiarySettingsState())
  val settingsState: StateFlow<DiarySettingsState> = _settingsState.asStateFlow()

  fun dispatch(action: DiaryAction) {
    coroutineScope.launch {
      when (action) {
        is DiaryAction -> handleGlobalAction(action)
      }
    }
  }

  private suspend fun handleGlobalAction(action: DiaryAction) {
    // Global actions that affect multiple states can be handled here
  }

  fun updateListState(state: DiaryListState) {
    _listState.value = state
  }

  fun updateEditorState(state: DiaryEditorState) {
    _editorState.value = state
  }

  fun updateDetailState(state: DiaryDetailState) {
    _detailState.value = state
  }

  fun updateSettingsState(state: DiarySettingsState) {
    _settingsState.value = state
  }

  fun getCurrentListState(): DiaryListState {
    return _listState.value
  }

  fun getCurrentEditorState(): DiaryEditorState {
    return _editorState.value
  }

  fun getCurrentDetailState(): DiaryDetailState {
    return _detailState.value
  }

  fun getCurrentSettingsState(): DiarySettingsState {
    return _settingsState.value
  }

  fun resetToInitialState() {
    _listState.value = DiaryListState()
    _editorState.value = DiaryEditorState()
    _detailState.value = DiaryDetailState()
    _settingsState.value = DiarySettingsState()
  }

  fun isLoading(): Boolean {
    return listState.value.isLoading || 
           editorState.value.isLoading || 
           detailState.value.isLoading || 
           settingsState.value.isLoading
  }

  fun hasErrors(): Boolean {
    return listState.value.hasError() || 
           editorState.value.hasError() || 
           detailState.value.hasError() || 
           settingsState.value.hasError()
  }
}