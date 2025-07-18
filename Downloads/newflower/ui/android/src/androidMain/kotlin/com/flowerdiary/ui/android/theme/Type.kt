package com.flowerdiary.ui.android.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * 꽃 다이어리 타이포그래피
 * SRP: 앱 타이포그래피 정의만 담당
 * 폰트 크기는 Config의 배수로 설정
 */
val Typography = Typography(
    // Display
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (BASE_FONT_SIZE * DISPLAY_LARGE_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * DISPLAY_LARGE_SCALE).sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (BASE_FONT_SIZE * DISPLAY_MEDIUM_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * DISPLAY_MEDIUM_SCALE).sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (BASE_FONT_SIZE * DISPLAY_SMALL_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * DISPLAY_SMALL_SCALE).sp,
        letterSpacing = 0.sp
    ),
    
    // Headline
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (BASE_FONT_SIZE * HEADLINE_LARGE_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * HEADLINE_LARGE_SCALE).sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (BASE_FONT_SIZE * HEADLINE_MEDIUM_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * HEADLINE_MEDIUM_SCALE).sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (BASE_FONT_SIZE * HEADLINE_SMALL_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * HEADLINE_SMALL_SCALE).sp,
        letterSpacing = 0.sp
    ),
    
    // Title
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = (BASE_FONT_SIZE * TITLE_LARGE_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * TITLE_LARGE_SCALE).sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = (BASE_FONT_SIZE * TITLE_MEDIUM_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * TITLE_MEDIUM_SCALE).sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = (BASE_FONT_SIZE * TITLE_SMALL_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * TITLE_SMALL_SCALE).sp,
        letterSpacing = 0.1.sp
    ),
    
    // Body
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (BASE_FONT_SIZE * BODY_LARGE_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * BODY_LARGE_SCALE).sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (BASE_FONT_SIZE * BODY_MEDIUM_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * BODY_MEDIUM_SCALE).sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (BASE_FONT_SIZE * BODY_SMALL_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * BODY_SMALL_SCALE).sp,
        letterSpacing = 0.4.sp
    ),
    
    // Label
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = (BASE_FONT_SIZE * LABEL_LARGE_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * LABEL_LARGE_SCALE).sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = (BASE_FONT_SIZE * LABEL_MEDIUM_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * LABEL_MEDIUM_SCALE).sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = (BASE_FONT_SIZE * LABEL_SMALL_SCALE).sp,
        lineHeight = (BASE_LINE_HEIGHT * LABEL_SMALL_SCALE).sp,
        letterSpacing = 0.5.sp
    )
)

// 기본 폰트 크기와 줄 높이
private const val BASE_FONT_SIZE = 14
private const val BASE_LINE_HEIGHT = 20

// 스케일 팩터들
private const val DISPLAY_LARGE_SCALE = 4.0f    // 57sp
private const val DISPLAY_MEDIUM_SCALE = 3.21f  // 45sp
private const val DISPLAY_SMALL_SCALE = 2.57f   // 36sp

private const val HEADLINE_LARGE_SCALE = 2.29f  // 32sp
private const val HEADLINE_MEDIUM_SCALE = 2.0f  // 28sp
private const val HEADLINE_SMALL_SCALE = 1.71f  // 24sp

private const val TITLE_LARGE_SCALE = 1.57f     // 22sp
private const val TITLE_MEDIUM_SCALE = 1.14f    // 16sp
private const val TITLE_SMALL_SCALE = 1.0f      // 14sp

private const val BODY_LARGE_SCALE = 1.14f      // 16sp
private const val BODY_MEDIUM_SCALE = 1.0f      // 14sp
private const val BODY_SMALL_SCALE = 0.86f      // 12sp

private const val LABEL_LARGE_SCALE = 1.0f      // 14sp
private const val LABEL_MEDIUM_SCALE = 0.86f    // 12sp
private const val LABEL_SMALL_SCALE = 0.79f     // 11sp