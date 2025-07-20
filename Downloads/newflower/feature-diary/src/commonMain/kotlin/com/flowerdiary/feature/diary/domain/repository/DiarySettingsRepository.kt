package com.flowerdiary.feature.diary.domain.repository

import com.flowerdiary.feature.diary.domain.model.DiarySettings
import com.flowerdiary.feature.diary.domain.model.BgmTrack

interface DiarySettingsRepository {

  suspend fun getSettings(): DiarySettings

  suspend fun saveSettings(settings: DiarySettings): DiarySettings

  suspend fun updateBgmSettings(track: BgmTrack, enabled: Boolean): DiarySettings

  suspend fun updateAutoSaveSettings(enabled: Boolean, intervalSeconds: Int): DiarySettings

  suspend fun updateFontSize(fontSize: Float): DiarySettings

  suspend fun updateBirthFlowerBackgroundEnabled(enabled: Boolean): DiarySettings

  suspend fun resetToDefaults(): DiarySettings

  suspend fun exportSettings(): String

  suspend fun importSettings(settingsJson: String): DiarySettings

  suspend fun backupSettings(): Boolean

  suspend fun restoreSettings(): DiarySettings?

  suspend fun hasCustomSettings(): Boolean

  suspend fun getLastModified(): Long

  suspend fun isAutoSaveEnabled(): Boolean

  suspend fun getAutoSaveInterval(): Int

  suspend fun getCurrentBgmTrack(): BgmTrack

  suspend fun isBgmEnabled(): Boolean
}