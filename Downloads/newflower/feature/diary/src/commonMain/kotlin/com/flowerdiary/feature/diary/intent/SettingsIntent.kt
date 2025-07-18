package com.flowerdiary.feature.diary.intent

import com.flowerdiary.feature.diary.state.settings.FontSizeLevel
import com.flowerdiary.feature.diary.state.settings.ThemeMode

/**
 * 설정 UI 이벤트 - Context7 KMP 극한 압축
 * SRP: 설정 카테고리별로 분리된 사용자 의도 표현
 */
sealed interface SettingsIntent {
    
    sealed interface Audio : SettingsIntent {
        data object ToggleBGM : Audio
        data class ChangeBGMVolume(val volume: Float) : Audio
        data class ChangeBGMTrack(val trackIndex: Int) : Audio
    }
    
    sealed interface Display : SettingsIntent {
        data class ChangeFontSize(val level: FontSizeLevel) : Display
        data class ChangeTheme(val mode: ThemeMode) : Display
    }
    
    sealed interface App : SettingsIntent {
        data object ToggleNotifications : App
        data object ToggleAutoUnlock : App
    }
    
    sealed interface System : SettingsIntent {
        data object LoadSettings : System
        data object ClearCache : System
        data object ResetData : System
    }
}