package com.flowerdiary.feature.diary.state

/**
 * BGM 설정 상태
 */
data class BGMSettingsState(
    val isLoading: Boolean = false,
    val enabled: Boolean = true,
    val volume: Float = 0.7f,
    val trackIndex: Int = 0,
    val error: String? = null
)

/**
 * BGM 설정 의도
 */
sealed interface BGMSettingsIntent {
    data object LoadSettings : BGMSettingsIntent
    data object ToggleBGM : BGMSettingsIntent
    data class ChangeVolume(val volume: Float) : BGMSettingsIntent
    data class ChangeTrack(val trackIndex: Int) : BGMSettingsIntent
}

/**
 * BGM 설정 효과
 */
sealed interface BGMSettingsEffect {
    data class PlayBGM(val trackIndex: Int) : BGMSettingsEffect
    data object StopBGM : BGMSettingsEffect
    data class SetVolume(val volume: Float) : BGMSettingsEffect
}
