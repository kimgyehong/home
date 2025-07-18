package com.flowerdiary.ui.android.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.flowerdiary.common.constants.ColorPalette

/**
 * 꽃 다이어리 Material3 테마
 * SRP: Material3 테마 제공만 담당
 * ColorPalette 상수를 Material Color로 변환
 */
@Composable
fun FlowerDiaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

/**
 * 라이트 테마 색상
 */
private val lightColorScheme = lightColorScheme(
    primary = Color(ColorPalette.Primary.Default.toInt()),
    onPrimary = Color(ColorPalette.Text.OnPrimary.toInt()),
    primaryContainer = Color(ColorPalette.Primary.Light.toInt()),
    onPrimaryContainer = Color(ColorPalette.Primary.Dark.toInt()),
    
    secondary = Color(ColorPalette.Secondary.Default.toInt()),
    onSecondary = Color(ColorPalette.Text.OnSecondary.toInt()),
    secondaryContainer = Color(ColorPalette.Secondary.Light.toInt()),
    onSecondaryContainer = Color(ColorPalette.Secondary.Dark.toInt()),
    
    tertiary = Color(ColorPalette.Seasonal.Spring.toInt()),
    onTertiary = Color(ColorPalette.Text.OnPrimary.toInt()),
    tertiaryContainer = Color(ColorPalette.Primary.Light.toInt()),
    onTertiaryContainer = Color(ColorPalette.Primary.Dark.toInt()),
    
    error = Color(ColorPalette.Status.Error.toInt()),
    onError = Color(ColorPalette.Text.OnPrimary.toInt()),
    errorContainer = Color(ColorPalette.Status.Error.toInt() and 0x1FFFFFFF),
    onErrorContainer = Color(ColorPalette.Status.Error.toInt()),
    
    background = Color(ColorPalette.Background.Primary.toInt()),
    onBackground = Color(ColorPalette.Text.Primary.toInt()),
    
    surface = Color(ColorPalette.Background.Card.toInt()),
    onSurface = Color(ColorPalette.Text.Primary.toInt()),
    surfaceVariant = Color(ColorPalette.Background.Secondary.toInt()),
    onSurfaceVariant = Color(ColorPalette.Text.Secondary.toInt()),
    
    outline = Color(ColorPalette.Utility.Divider.toInt()),
    outlineVariant = Color(ColorPalette.Utility.Divider.toInt() and 0x0FFFFFFF),
)

/**
 * 다크 테마 색상
 */
private val darkColorScheme = darkColorScheme(
    primary = Color(ColorPalette.Primary.Dark.toInt()),
    onPrimary = Color(ColorPalette.Text.OnPrimary.toInt()),
    primaryContainer = Color(ColorPalette.Primary.Default.toInt()),
    onPrimaryContainer = Color(ColorPalette.Primary.Light.toInt()),
    
    secondary = Color(ColorPalette.Secondary.Dark.toInt()),
    onSecondary = Color(ColorPalette.Text.OnSecondary.toInt()),
    secondaryContainer = Color(ColorPalette.Secondary.Default.toInt()),
    onSecondaryContainer = Color(ColorPalette.Secondary.Light.toInt()),
    
    tertiary = Color(ColorPalette.Seasonal.Winter.toInt()),
    onTertiary = Color(ColorPalette.Text.OnPrimary.toInt()),
    tertiaryContainer = Color(ColorPalette.Seasonal.Spring.toInt()),
    onTertiaryContainer = Color(ColorPalette.Primary.Light.toInt()),
    
    error = Color(ColorPalette.Status.Error.toInt()),
    onError = Color(ColorPalette.Text.OnPrimary.toInt()),
    errorContainer = Color(ColorPalette.Status.Error.toInt() and 0x3FFFFFFF),
    onErrorContainer = Color(ColorPalette.Status.Error.toInt()),
    
    background = Color(0xFF1C1C1C),
    onBackground = Color(0xFFE1E1E1),
    
    surface = Color(0xFF2C2C2C),
    onSurface = Color(0xFFE1E1E1),
    surfaceVariant = Color(0xFF3C3C3C),
    onSurfaceVariant = Color(0xFFC1C1C1),
    
    outline = Color(0xFF5C5C5C),
    outlineVariant = Color(0xFF3C3C3C),
)