package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.constants.PreferencesConfig
import com.flowerdiary.common.constants.TextConfig
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.common.utils.ExceptionUtil

/**
 * 디스플레이 설정 관리자
 * SRP: 화면 표시 관련 설정만 담당
 */
class DisplaySettingsManager(
    private val preferencesStore: PreferencesStore
) {
    
    /**
     * 폰트 크기 변경
     */
    suspend fun changeFontSize(level: FontSizeLevel): Result<Unit> {
        return ExceptionUtil.runCatchingSuspend {
            val scale = when (level) {
                FontSizeLevel.SMALL -> 0.85f
                FontSizeLevel.MEDIUM -> 1.0f
                FontSizeLevel.LARGE -> 1.15f
                FontSizeLevel.EXTRA_LARGE -> 1.3f
            }
            preferencesStore.putFloat(PreferencesConfig.PREF_KEY_FONT_SCALE, scale)
        }
    }
    
    /**
     * 테마 변경
     */
    suspend fun changeTheme(mode: ThemeMode): Result<Unit> {
        return ExceptionUtil.runCatchingSuspend {
            preferencesStore.putString(PreferencesConfig.PREF_KEY_THEME, mode.name)
        }
    }
    
    /**
     * 디스플레이 설정 로드
     */
    suspend fun loadDisplaySettings(): DisplaySettings {
        val fontScale = preferencesStore.getFloat(PreferencesConfig.PREF_KEY_FONT_SCALE, 1.0f)
        val themeName = preferencesStore.getString(PreferencesConfig.PREF_KEY_THEME, ThemeMode.SYSTEM.name)
        
        return DisplaySettings(
            fontSizeLevel = getFontSizeLevelFromScale(fontScale),
            themeMode = ThemeMode.valueOf(themeName)
        )
    }
    
    private fun getFontSizeLevelFromScale(scale: Float): FontSizeLevel {
        return when {
            scale <= 0.9f -> FontSizeLevel.SMALL
            scale <= 1.1f -> FontSizeLevel.MEDIUM
            scale <= 1.2f -> FontSizeLevel.LARGE
            else -> FontSizeLevel.EXTRA_LARGE
        }
    }
}

/**
 * 폰트 크기 레벨
 */
enum class FontSizeLevel {
    SMALL,
    MEDIUM,
    LARGE,
    EXTRA_LARGE
}

/**
 * 테마 모드
 */
enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}

/**
 * 디스플레이 설정 데이터
 */
data class DisplaySettings(
    val fontSizeLevel: FontSizeLevel,
    val themeMode: ThemeMode
)