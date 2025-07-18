package com.flowerdiary.feature.diary.manager

import com.flowerdiary.feature.diary.state.SettingsState
import com.flowerdiary.feature.diary.state.settings.AudioSettingsState
import com.flowerdiary.feature.diary.state.settings.ThemeSettingsState
import com.flowerdiary.feature.diary.state.settings.AppSettingsState
import com.flowerdiary.feature.diary.state.settings.ThemeMode

/**
 * 설정 화면 상태 관리자
 * SRP: 설정 화면의 상태 관리 로직만 담당
 */
class SettingsStateManager {
    
    /**
     * 초기 상태 생성
     */
    fun createInitialState(): SettingsState {
        return SettingsState()
    }
    
    /**
     * 로딩 상태 업데이트
     */
    fun updateLoadingState(
        currentState: SettingsState,
        isLoading: Boolean
    ): SettingsState {
        return currentState.copy(isLoading = isLoading)
    }
    
    /**
     * BGM 설정 업데이트
     */
    fun updateBGMSettings(
        currentState: SettingsState,
        enabled: Boolean,
        volume: Float,
        trackIndex: Int
    ): SettingsState {
        return currentState.copy(
            audio = currentState.audio.copy(
                bgmEnabled = enabled,
                bgmVolume = volume,
                bgmTrackIndex = trackIndex
            )
        )
    }
    
    /**
     * BGM 활성화 상태 업데이트
     */
    fun updateBGMEnabled(
        currentState: SettingsState,
        enabled: Boolean
    ): SettingsState {
        return currentState.copy(
            audio = currentState.audio.copy(bgmEnabled = enabled)
        )
    }
    
    /**
     * BGM 볼륨 업데이트
     */
    fun updateBGMVolume(
        currentState: SettingsState,
        volume: Float
    ): SettingsState {
        return currentState.copy(
            audio = currentState.audio.copy(bgmVolume = volume)
        )
    }
    
    /**
     * BGM 트랙 업데이트
     */
    fun updateBGMTrack(
        currentState: SettingsState,
        trackIndex: Int
    ): SettingsState {
        return currentState.copy(
            audio = currentState.audio.copy(bgmTrackIndex = trackIndex)
        )
    }
    
    /**
     * 디스플레이 설정 업데이트
     */
    fun updateDisplaySettings(
        currentState: SettingsState,
        fontScale: Float,
        themeMode: ThemeMode
    ): SettingsState {
        return currentState.copy(
            theme = currentState.theme.copy(
                fontSizeScale = fontScale,
                themeMode = themeMode
            )
        )
    }
    
    /**
     * 알림 설정 업데이트
     */
    fun updateNotificationSettings(
        currentState: SettingsState,
        enabled: Boolean,
        autoUnlock: Boolean
    ): SettingsState {
        return currentState.copy(
            app = currentState.app.copy(
                notificationsEnabled = enabled,
                autoUnlockEnabled = autoUnlock
            )
        )
    }
    
    /**
     * 에러 상태 업데이트
     */
    fun updateErrorState(
        currentState: SettingsState,
        error: String?
    ): SettingsState {
        return currentState.copy(
            error = error,
            isLoading = false
        )
    }
    
    /**
     * 전체 설정 업데이트
     */
    fun updateAllSettings(
        currentState: SettingsState,
        bgmEnabled: Boolean,
        bgmVolume: Float,
        bgmTrackIndex: Int,
        fontScale: Float,
        notifications: Boolean,
        autoUnlock: Boolean,
        themeMode: ThemeMode
    ): SettingsState {
        return currentState.copy(
            isLoading = false,
            audio = AudioSettingsState(
                bgmEnabled = bgmEnabled,
                bgmVolume = bgmVolume,
                bgmTrackIndex = bgmTrackIndex
            ),
            theme = ThemeSettingsState(
                fontSizeScale = fontScale,
                themeMode = themeMode
            ),
            app = AppSettingsState(
                notificationsEnabled = notifications,
                autoUnlockEnabled = autoUnlock
            ),
            error = null
        )
    }
}