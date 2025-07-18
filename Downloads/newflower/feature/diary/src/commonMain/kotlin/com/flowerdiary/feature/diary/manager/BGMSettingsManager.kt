package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.constants.PreferencesConfig
import com.flowerdiary.common.constants.UIConfig
import com.flowerdiary.common.platform.AudioManager
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.common.utils.ExceptionUtil

/**
 * BGM 설정 관리자
 * SRP: 배경음악 관련 설정만 담당
 */
class BGMSettingsManager(
    private val preferencesStore: PreferencesStore,
    private val audioManager: AudioManager
) {
    
    /**
     * BGM 활성화 상태 토글
     */
    suspend fun toggleBGM(currentEnabled: Boolean): Result<Boolean> {
        return ExceptionUtil.runCatchingSuspend {
            val newState = !currentEnabled
            preferencesStore.putBoolean(PreferencesConfig.PREF_KEY_BGM_ENABLED, newState)
            
            if (newState) {
                val trackIndex = preferencesStore.getInt(PreferencesConfig.PREF_KEY_BGM_TRACK, 0)
                audioManager.playBGM(trackIndex)
            } else {
                audioManager.stopBGM()
            }
            
            newState
        }
    }
    
    /**
     * BGM 볼륨 변경
     */
    suspend fun changeBGMVolume(volume: Float): Result<Unit> {
        return ExceptionUtil.runCatchingSuspend {
            val clampedVolume = volume.coerceIn(0f, 1f)
            preferencesStore.putFloat(PreferencesConfig.PREF_KEY_BGM_VOLUME, clampedVolume)
            audioManager.setBGMVolume(clampedVolume)
        }
    }
    
    /**
     * BGM 트랙 변경
     */
    suspend fun changeBGMTrack(trackIndex: Int): Result<Unit> {
        return ExceptionUtil.runCatchingSuspend {
            val clampedIndex = trackIndex.coerceIn(0, UIConfig.BGM_TRACK_COUNT - 1)
            preferencesStore.putInt(PreferencesConfig.PREF_KEY_BGM_TRACK, clampedIndex)
            
            val isEnabled = preferencesStore.getBoolean(
                PreferencesConfig.PREF_KEY_BGM_ENABLED, 
                true
            )
            if (isEnabled) {
                audioManager.playBGM(clampedIndex)
            }
        }
    }
    
    /**
     * BGM 설정 로드
     */
    suspend fun loadBGMSettings(): BGMSettings {
        return BGMSettings(
            enabled = preferencesStore.getBoolean(PreferencesConfig.PREF_KEY_BGM_ENABLED, true),
            volume = preferencesStore.getFloat(PreferencesConfig.PREF_KEY_BGM_VOLUME, UIConfig.BGM_VOLUME_DEFAULT),
            currentTrack = preferencesStore.getInt(PreferencesConfig.PREF_KEY_BGM_TRACK, 0)
        )
    }
}

/**
 * BGM 설정 데이터
 */
data class BGMSettings(
    val enabled: Boolean,
    val volume: Float,
    val currentTrack: Int
)