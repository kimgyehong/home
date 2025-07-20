package com.flowerdiary.feature.diary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DiarySettings(
  val autoSaveEnabled: Boolean = true,
  val autoSaveIntervalSeconds: Int = DEFAULT_AUTO_SAVE_INTERVAL,
  val bgmTrack: BgmTrack = BgmTrack.BGM1,
  val bgmEnabled: Boolean = true,
  val showBirthFlowerBackground: Boolean = true,
  val fontSize: Float = DEFAULT_FONT_SIZE
) {

  init {
    require(autoSaveIntervalSeconds > 0) {
      "Auto save interval must be positive"
    }
    require(fontSize > 0) {
      "Font size must be positive"
    }
    require(fontSize <= MAX_FONT_SIZE) {
      "Font size cannot exceed $MAX_FONT_SIZE"
    }
  }

  fun updateBgm(track: BgmTrack, enabled: Boolean = bgmEnabled): DiarySettings {
    return copy(
      bgmTrack = track,
      bgmEnabled = enabled
    )
  }

  fun updateAutoSave(enabled: Boolean, intervalSeconds: Int = autoSaveIntervalSeconds): DiarySettings {
    return copy(
      autoSaveEnabled = enabled,
      autoSaveIntervalSeconds = intervalSeconds
    )
  }

  fun updateFontSize(size: Float): DiarySettings {
    require(size > 0 && size <= MAX_FONT_SIZE) {
      "Font size must be between 1 and $MAX_FONT_SIZE"
    }
    return copy(fontSize = size)
  }

  companion object {
    private const val DEFAULT_AUTO_SAVE_INTERVAL = 30
    private const val DEFAULT_FONT_SIZE = 16.0f
    private const val MAX_FONT_SIZE = 32.0f
    
    fun default(): DiarySettings {
      return DiarySettings()
    }
  }
}