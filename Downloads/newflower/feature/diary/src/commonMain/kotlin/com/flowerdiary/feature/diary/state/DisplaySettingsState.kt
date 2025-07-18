package com.flowerdiary.feature.diary.state

/**
 * 화면 설정 상태
 */
data class DisplaySettingsState(
    val isLoading: Boolean = false,
    val fontSizeScale: Float = 1.0f,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val error: String? = null
)

/**
 * 테마 모드
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}

/**
 * 폰트 크기 레벨
 */
enum class FontSizeLevel(val scale: Float) {
    SMALL(0.8f),
    MEDIUM(1.0f),
    LARGE(1.2f),
    EXTRA_LARGE(1.5f)
}

/**
 * 화면 설정 의도
 */
sealed interface DisplaySettingsIntent {
    data object LoadSettings : DisplaySettingsIntent
    data class ChangeFontSize(val level: FontSizeLevel) : DisplaySettingsIntent
    data class ChangeTheme(val mode: ThemeMode) : DisplaySettingsIntent
}

/**
 * 화면 설정 효과
 */
sealed interface DisplaySettingsEffect {
    data class ShowToast(val message: String) : DisplaySettingsEffect
    data class ApplyTheme(val mode: ThemeMode) : DisplaySettingsEffect
}
