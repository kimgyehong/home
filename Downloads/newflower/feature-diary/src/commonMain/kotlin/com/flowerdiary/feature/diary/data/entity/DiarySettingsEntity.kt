package com.flowerdiary.feature.diary.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class DiarySettingsEntity(
  val id: String = DEFAULT_ID,
  val autoSaveEnabled: Boolean = true,
  val autoSaveIntervalSeconds: Int = DEFAULT_AUTO_SAVE_INTERVAL,
  val bgmTrack: String = DEFAULT_BGM_TRACK,
  val bgmEnabled: Boolean = true,
  val showBirthFlowerBackground: Boolean = true,
  val fontSize: Float = DEFAULT_FONT_SIZE,
  val lastModified: Long = System.currentTimeMillis(),
  val version: Int = CURRENT_VERSION
) {

  fun isValid(): Boolean {
    return id.isNotBlank() &&
           autoSaveIntervalSeconds > 0 &&
           fontSize > 0 &&
           fontSize <= MAX_FONT_SIZE &&
           lastModified > 0 &&
           version > 0 &&
           isValidBgmTrack()
  }

  fun isDefault(): Boolean {
    return autoSaveEnabled &&
           autoSaveIntervalSeconds == DEFAULT_AUTO_SAVE_INTERVAL &&
           bgmTrack == DEFAULT_BGM_TRACK &&
           bgmEnabled &&
           showBirthFlowerBackground &&
           fontSize == DEFAULT_FONT_SIZE
  }

  fun updateTimestamp(): DiarySettingsEntity {
    return copy(lastModified = System.currentTimeMillis())
  }

  private fun isValidBgmTrack(): Boolean {
    return bgmTrack in VALID_BGM_TRACKS
  }

  companion object {
    private const val DEFAULT_ID = "diary_settings"
    private const val CURRENT_VERSION = 1
    private const val DEFAULT_AUTO_SAVE_INTERVAL = 30
    private const val DEFAULT_BGM_TRACK = "bgm.mp3"
    private const val DEFAULT_FONT_SIZE = 16.0f
    private const val MAX_FONT_SIZE = 32.0f
    
    private val VALID_BGM_TRACKS = setOf(
      "bgm.mp3", "bgm2.mp3", "bgm3.mp3", "bgm4.mp3"
    )
    
    fun default(): DiarySettingsEntity {
      return DiarySettingsEntity()
    }
  }
}