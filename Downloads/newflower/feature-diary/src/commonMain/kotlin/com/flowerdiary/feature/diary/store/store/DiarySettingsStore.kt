package com.flowerdiary.feature.diary.store.store

import com.flowerdiary.feature.diary.store.action.DiaryAction
import com.flowerdiary.feature.diary.store.state.DiarySettingsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiarySettingsStore(
  private val coroutineScope: CoroutineScope
) {

  private val _state = MutableStateFlow(DiarySettingsState())
  val state: StateFlow<DiarySettingsState> = _state.asStateFlow()

  fun dispatch(action: DiaryAction) {
    coroutineScope.launch {
      when (action) {
        is DiaryAction -> handleAction(action)
      }
    }
  }

  private fun handleAction(action: DiaryAction) {
    // Handle diary settings actions
  }

  fun updateAutoSaveEnabled(enabled: Boolean) {
    _state.value = _state.value.copy(isAutoSaveEnabled = enabled)
  }

  fun updateAutoSaveInterval(interval: Long) {
    _state.value = _state.value.copy(autoSaveInterval = interval)
  }

  fun updateDefaultTemplate(template: String) {
    _state.value = _state.value.copy(defaultTemplate = template)
  }

  fun updateFontSize(fontSize: Float) {
    _state.value = _state.value.copy(fontSize = fontSize)
  }

  fun updateLineSpacing(spacing: Float) {
    _state.value = _state.value.copy(lineSpacing = spacing)
  }

  fun updateShowWordCount(show: Boolean) {
    _state.value = _state.value.copy(showWordCount = show)
  }

  fun updateShowCharacterCount(show: Boolean) {
    _state.value = _state.value.copy(showCharacterCount = show)
  }

  fun updateSpellCheckEnabled(enabled: Boolean) {
    _state.value = _state.value.copy(isSpellCheckEnabled = enabled)
  }

  fun updateBackupEnabled(enabled: Boolean) {
    _state.value = _state.value.copy(isBackupEnabled = enabled)
  }

  fun updateBackupInterval(interval: Long) {
    _state.value = _state.value.copy(backupInterval = interval)
  }

  fun updateExportFormat(format: String) {
    _state.value = _state.value.copy(exportFormat = format)
  }

  fun updateMaxBackupFiles(maxFiles: Int) {
    _state.value = _state.value.copy(maxBackupFiles = maxFiles)
  }

  fun resetToDefaults() {
    _state.value = DiarySettingsState()
  }

  fun saveSettings() {
    _state.value = _state.value.copy(isLoading = true)
    
    coroutineScope.launch {
      try {
        // Save settings implementation
        _state.value = _state.value.copy(
          isLoading = false,
          lastSavedAt = System.currentTimeMillis()
        )
      } catch (exception: Exception) {
        _state.value = _state.value.copy(
          isLoading = false,
          errorMessage = exception.message
        )
      }
    }
  }

  fun loadSettings() {
    _state.value = _state.value.copy(isLoading = true)
    
    coroutineScope.launch {
      try {
        // Load settings implementation
        _state.value = _state.value.copy(isLoading = false)
      } catch (exception: Exception) {
        _state.value = _state.value.copy(
          isLoading = false,
          errorMessage = exception.message
        )
      }
    }
  }

  fun clearError() {
    _state.value = _state.value.copy(errorMessage = null)
  }

  fun exportSettings(): Map<String, Any> {
    val currentState = _state.value
    return mapOf(
      "autoSaveEnabled" to currentState.isAutoSaveEnabled,
      "autoSaveInterval" to currentState.autoSaveInterval,
      "defaultTemplate" to currentState.defaultTemplate,
      "fontSize" to currentState.fontSize,
      "lineSpacing" to currentState.lineSpacing,
      "showWordCount" to currentState.showWordCount,
      "showCharacterCount" to currentState.showCharacterCount,
      "spellCheckEnabled" to currentState.isSpellCheckEnabled,
      "backupEnabled" to currentState.isBackupEnabled,
      "backupInterval" to currentState.backupInterval,
      "exportFormat" to currentState.exportFormat,
      "maxBackupFiles" to currentState.maxBackupFiles
    )
  }

  fun importSettings(settings: Map<String, Any>) {
    val currentState = _state.value
    
    _state.value = currentState.copy(
      isAutoSaveEnabled = settings["autoSaveEnabled"] as? Boolean ?: currentState.isAutoSaveEnabled,
      autoSaveInterval = settings["autoSaveInterval"] as? Long ?: currentState.autoSaveInterval,
      defaultTemplate = settings["defaultTemplate"] as? String ?: currentState.defaultTemplate,
      fontSize = settings["fontSize"] as? Float ?: currentState.fontSize,
      lineSpacing = settings["lineSpacing"] as? Float ?: currentState.lineSpacing,
      showWordCount = settings["showWordCount"] as? Boolean ?: currentState.showWordCount,
      showCharacterCount = settings["showCharacterCount"] as? Boolean ?: currentState.showCharacterCount,
      isSpellCheckEnabled = settings["spellCheckEnabled"] as? Boolean ?: currentState.isSpellCheckEnabled,
      isBackupEnabled = settings["backupEnabled"] as? Boolean ?: currentState.isBackupEnabled,
      backupInterval = settings["backupInterval"] as? Long ?: currentState.backupInterval,
      exportFormat = settings["exportFormat"] as? String ?: currentState.exportFormat,
      maxBackupFiles = settings["maxBackupFiles"] as? Int ?: currentState.maxBackupFiles
    )
  }
}