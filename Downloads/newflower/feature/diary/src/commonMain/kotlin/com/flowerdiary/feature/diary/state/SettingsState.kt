package com.flowerdiary.feature.diary.state

import com.flowerdiary.feature.diary.state.settings.AppSettingsState
import com.flowerdiary.feature.diary.state.settings.AudioSettingsState
import com.flowerdiary.feature.diary.state.settings.ThemeSettingsState

/**
 * 설정 화면 전체 상태
 * Composition 패턴으로 관련 설정들을 조합
 * SRP: 각 하위 상태는 독립적인 책임을 가짐
 */
data class SettingsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val audio: AudioSettingsState = AudioSettingsState(),
    val theme: ThemeSettingsState = ThemeSettingsState(),
    val app: AppSettingsState = AppSettingsState()
) {
    /**
     * BGM 활성화 여부 (위임)
     */
    val bgmEnabled: Boolean get() = audio.bgmEnabled
    
    /**
     * BGM 볼륨 퍼센트 (위임)
     */
    val bgmVolumePercent: Int get() = audio.bgmVolumePercent
    
    /**
     * 현재 테마 모드 (위임)
     */
    val themeMode get() = theme.themeMode
    
    /**
     * 폰트 크기 레벨 (위임)
     */
    val fontSizeLevel get() = theme.fontSizeLevel
    
    /**
     * 알림 활성화 여부 (위임)
     */
    val notificationsEnabled: Boolean get() = app.notificationsEnabled
    
    /**
     * 자동 해금 활성화 여부 (위임)
     */
    val autoUnlockEnabled: Boolean get() = app.autoUnlockEnabled
}