package com.flowerdiary.core.model

import com.flowerdiary.core.types.SettingsTimestamp
import kotlinx.serialization.Serializable

/**
 * 앱 설정 도메인 모델
 * 사용자의 앱 사용 설정
 */
@Serializable
data class Settings(
  val bgmTrackIndex: Int = DEFAULT_BGM_INDEX,
  val bgmVolume: Float = DEFAULT_VOLUME,
  val isBgmEnabled: Boolean = true,
  val isLoadingAnimationEnabled: Boolean = true,
  val loadingDuration: Long = DEFAULT_LOADING_DURATION,
  val updatedAt: SettingsTimestamp
) {
  init {
    require(bgmTrackIndex in MIN_BGM_INDEX..MAX_BGM_INDEX) { 
      "BGM track index must be $MIN_BGM_INDEX-$MAX_BGM_INDEX: $bgmTrackIndex" 
    }
    require(bgmVolume in MIN_VOLUME..MAX_VOLUME) { 
      "BGM volume must be $MIN_VOLUME-$MAX_VOLUME: $bgmVolume" 
    }
    require(loadingDuration >= MIN_LOADING_DURATION) { 
      "Loading duration must be at least $MIN_LOADING_DURATION ms: $loadingDuration" 
    }
    require(loadingDuration <= MAX_LOADING_DURATION) { 
      "Loading duration must be at most $MAX_LOADING_DURATION ms: $loadingDuration" 
    }
  }

  /**
   * BGM 설정 변경
   */
  fun updateBgmSettings(
    trackIndex: Int = bgmTrackIndex,
    volume: Float = bgmVolume,
    enabled: Boolean = isBgmEnabled,
    updatedTime: SettingsTimestamp
  ): Settings = copy(
    bgmTrackIndex = trackIndex,
    bgmVolume = volume,
    isBgmEnabled = enabled,
    updatedAt = updatedTime
  )


  /**
   * 로딩 설정 변경
   */
  fun updateLoadingSettings(
    animationEnabled: Boolean = isLoadingAnimationEnabled,
    duration: Long = loadingDuration,
    updatedTime: SettingsTimestamp
  ): Settings = copy(
    isLoadingAnimationEnabled = animationEnabled,
    loadingDuration = duration,
    updatedAt = updatedTime
  )

  companion object {
    const val MIN_BGM_INDEX = 1
    const val MAX_BGM_INDEX = 4
    const val DEFAULT_BGM_INDEX = 1
    const val MIN_VOLUME = 0.0f
    const val MAX_VOLUME = 1.0f
    const val DEFAULT_VOLUME = 0.7f
    const val MIN_LOADING_DURATION = 1000L
    const val MAX_LOADING_DURATION = 5000L
    const val DEFAULT_LOADING_DURATION = 2000L
  }
}