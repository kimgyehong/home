package com.flowerdiary.feature.diary.store.state

import com.flowerdiary.feature.diary.domain.model.BgmTrack
import com.flowerdiary.feature.diary.domain.model.DiarySettings
import kotlinx.serialization.Serializable

@Serializable
data class DiarySettingsState(
  val settings: DiarySettings? = null,
  val isLoading: Boolean = false,
  val isSaving: Boolean = false,
  val errorMessage: String? = null,
  val hasUnsavedChanges: Boolean = false,
  val showResetConfirmation: Boolean = false,
  val showBgmOptions: Boolean = false,
  val lastSavedAt: Long? = null
) {

  fun hasSettings(): Boolean {
    return settings != null
  }

  fun canSave(): Boolean {
    return !isSaving && hasUnsavedChanges
  }

  fun canReset(): Boolean {
    return settings != null && !isDefault()
  }

  fun isProcessing(): Boolean {
    return isLoading || isSaving
  }

  fun hasError(): Boolean {
    return errorMessage != null
  }

  fun isDefault(): Boolean {
    return settings?.let { it == DiarySettings.default() } ?: true
  }

  fun isAutoSaveEnabled(): Boolean {
    return settings?.autoSaveEnabled ?: true
  }

  fun getAutoSaveInterval(): Int {
    return settings?.autoSaveIntervalSeconds ?: 30
  }

  fun getCurrentBgmTrack(): BgmTrack {
    return settings?.bgmTrack ?: BgmTrack.getDefault()
  }

  fun isBgmEnabled(): Boolean {
    return settings?.bgmEnabled ?: true
  }

  fun isBirthFlowerBackgroundEnabled(): Boolean {
    return settings?.showBirthFlowerBackground ?: true
  }

  fun getFontSize(): Float {
    return settings?.fontSize ?: 16.0f
  }

  fun isRecentlySaved(): Boolean {
    return lastSavedAt?.let { 
      System.currentTimeMillis() - it < RECENT_SAVE_THRESHOLD 
    } ?: false
  }

  fun updateSettings(newSettings: DiarySettings): DiarySettingsState {
    return copy(
      settings = newSettings,
      hasUnsavedChanges = true
    )
  }

  fun markSaved(): DiarySettingsState {
    return copy(
      hasUnsavedChanges = false,
      lastSavedAt = System.currentTimeMillis(),
      isSaving = false
    )
  }

  companion object {
    private const val RECENT_SAVE_THRESHOLD = 30000 // 30초
    
    fun loading(): DiarySettingsState {
      return DiarySettingsState(isLoading = true)
    }
    
    fun loaded(settings: DiarySettings): DiarySettingsState {
      return DiarySettingsState(settings = settings)
    }
    
    fun error(message: String): DiarySettingsState {
      return DiarySettingsState(errorMessage = message)
    }
    
    fun saving(settings: DiarySettings): DiarySettingsState {
      return DiarySettingsState(
        settings = settings,
        isSaving = true
      )
    }
    
    fun default(): DiarySettingsState {
      return DiarySettingsState(settings = DiarySettings.default())
    }
  }
}