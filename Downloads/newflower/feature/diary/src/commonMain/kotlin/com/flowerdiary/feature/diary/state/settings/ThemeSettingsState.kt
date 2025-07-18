package com.flowerdiary.feature.diary.state.settings

/**
 * 테마 설정 관련 상태
 * SRP: 테마와 표시 관련 설정만 관리
 */
data class ThemeSettingsState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val fontSizeScale: Float = 1.0f
) {
    /**
     * 폰트 크기 레벨
     */
    val fontSizeLevel: FontSizeLevel get() = when {
        fontSizeScale < 0.9f -> FontSizeLevel.SMALL
        fontSizeScale > 1.1f -> FontSizeLevel.LARGE
        else -> FontSizeLevel.MEDIUM
    }
    
    /**
     * 다크 모드 활성화 여부
     */
    val isDarkMode: Boolean get() = themeMode == ThemeMode.DARK
}

/**
 * 테마 모드
 */
enum class ThemeMode(val displayName: String) {
    LIGHT("밝은 테마"),
    DARK("어두운 테마"),
    SYSTEM("시스템 설정")
}

/**
 * 폰트 크기 레벨
 */
enum class FontSizeLevel(val scale: Float, val displayName: String) {
    SMALL(0.85f, "작게"),
    MEDIUM(1.0f, "보통"),
    LARGE(1.15f, "크게")
}