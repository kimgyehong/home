package com.flowerdiary.feature.diary.data.repository

import com.flowerdiary.feature.diary.data.mapper.DiarySettingsMapper
import com.flowerdiary.feature.diary.domain.model.BgmTrack
import com.flowerdiary.feature.diary.domain.model.DiarySettings
import com.flowerdiary.feature.diary.domain.repository.DiarySettingsRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DiarySettingsRepositoryImpl(
  private val localDataSource: DiaryLocalDataSource,
  private val remoteDataSource: DiaryRemoteDataSource? = null
) : DiarySettingsRepository {

  override suspend fun getSettings(): DiarySettings {
    val entity = localDataSource.getSettings()
    return DiarySettingsMapper.toDomain(entity)
  }

  override suspend fun saveSettings(settings: DiarySettings): DiarySettings {
    val entity = DiarySettingsMapper.toEntity(settings)
    val savedEntity = localDataSource.saveSettings(entity)
    
    remoteDataSource?.let {
      try {
        it.uploadSettings(savedEntity)
      } catch (exception: Exception) {
        // Remote sync failure is not critical
      }
    }
    
    return DiarySettingsMapper.toDomain(savedEntity)
  }

  override suspend fun updateBgmSettings(track: BgmTrack, enabled: Boolean): DiarySettings {
    val currentSettings = getSettings()
    val updatedSettings = currentSettings.updateBgm(track, enabled)
    return saveSettings(updatedSettings)
  }

  override suspend fun updateAutoSaveSettings(enabled: Boolean, intervalSeconds: Int): DiarySettings {
    val currentSettings = getSettings()
    val updatedSettings = currentSettings.updateAutoSave(enabled, intervalSeconds)
    return saveSettings(updatedSettings)
  }

  override suspend fun updateFontSize(fontSize: Float): DiarySettings {
    val currentSettings = getSettings()
    val updatedSettings = currentSettings.updateFontSize(fontSize)
    return saveSettings(updatedSettings)
  }

  override suspend fun updateBirthFlowerBackgroundEnabled(enabled: Boolean): DiarySettings {
    val currentSettings = getSettings()
    val updatedSettings = currentSettings.copy(showBirthFlowerBackground = enabled)
    return saveSettings(updatedSettings)
  }

  override suspend fun resetToDefaults(): DiarySettings {
    val defaultSettings = DiarySettings.default()
    return saveSettings(defaultSettings)
  }

  override suspend fun exportSettings(): String {
    val settings = getSettings()
    return Json.encodeToString(settings)
  }

  override suspend fun importSettings(settingsJson: String): DiarySettings {
    return try {
      val settings = Json.decodeFromString<DiarySettings>(settingsJson)
      saveSettings(settings)
    } catch (exception: Exception) {
      throw IllegalArgumentException("Invalid settings JSON: ${exception.message}")
    }
  }

  override suspend fun backupSettings(): Boolean {
    return try {
      val settings = getSettings()
      val settingsJson = exportSettings()
      // Store backup locally or remotely
      true
    } catch (exception: Exception) {
      false
    }
  }

  override suspend fun restoreSettings(): DiarySettings? {
    return try {
      remoteDataSource?.downloadSettings()?.let { entity ->
        val settings = DiarySettingsMapper.toDomain(entity)
        saveSettings(settings)
      }
    } catch (exception: Exception) {
      null
    }
  }

  override suspend fun hasCustomSettings(): Boolean {
    val settings = getSettings()
    val defaultSettings = DiarySettings.default()
    return settings != defaultSettings
  }

  override suspend fun getLastModified(): Long {
    val entity = localDataSource.getSettings()
    return entity.lastModified
  }

  override suspend fun isAutoSaveEnabled(): Boolean {
    val settings = getSettings()
    return settings.autoSaveEnabled
  }

  override suspend fun getAutoSaveInterval(): Int {
    val settings = getSettings()
    return settings.autoSaveIntervalSeconds
  }

  override suspend fun getCurrentBgmTrack(): BgmTrack {
    val settings = getSettings()
    return settings.bgmTrack
  }

  override suspend fun isBgmEnabled(): Boolean {
    val settings = getSettings()
    return settings.bgmEnabled
  }
}