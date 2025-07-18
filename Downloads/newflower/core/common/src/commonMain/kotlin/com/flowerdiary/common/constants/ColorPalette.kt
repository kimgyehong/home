package com.flowerdiary.common.constants

/**
 * 앱 전체 색상 팔레트 - 하드코딩된 색상값 제거
 * 플랫폼 중립적 색상 표현 (ARGB 정수값)
 */
object ColorPalette {
    // Primary Colors - 꽃 테마 색상
    object Primary {
        const val Light = 0xFFFFF0F5L  // Lavender Blush
        const val Default = 0xFFFFB6C1L // Light Pink
        const val Dark = 0xFFFF69B4L    // Hot Pink
    }
    
    // Secondary Colors - 잎사귀 테마 색상
    object Secondary {
        const val Light = 0xFFF0FFF0L   // Honeydew
        const val Default = 0xFF90EE90L // Light Green
        const val Dark = 0xFF228B22L    // Forest Green
    }
    
    // Background Colors
    object Background {
        const val Primary = 0xFFFFFFFFL   // Pure White
        const val Secondary = 0xFFFAFAFAL // Off White
        const val Tertiary = 0xFFF5F5F5L  // White Smoke
        const val Card = 0xFFFFFFFFL      // Card Background
        const val Overlay = 0xCC000000L   // Semi-transparent Black
    }
    
    // Text Colors
    object Text {
        const val Primary = 0xFF212121L   // Almost Black
        const val Secondary = 0xFF757575L // Grey
        const val Tertiary = 0xFF9E9E9EL  // Light Grey
        const val OnPrimary = 0xFFFFFFFFL // White on Primary
        const val OnSecondary = 0xFFFFFFFFL // White on Secondary
        const val Disabled = 0x61000000L  // 38% Black
        const val Black = 0xFF000000L     // Pure Black
    }
    
    // Accent Colors - 계절별 색상
    object Seasonal {
        const val Spring = 0xFFFFB6C1L  // Light Pink
        const val Summer = 0xFF87CEEB   // Sky Blue
        const val Autumn = 0xFFFF8C00L  // Dark Orange
        const val Winter = 0xFFB0C4DEL  // Light Steel Blue
    }
    
    // Status Colors
    object Status {
        const val Success = 0xFF4CAF50L  // Green
        const val Warning = 0xFFFFC107L  // Amber
        const val Error = 0xFFF44336L    // Red
        const val Info = 0xFF2196F3L     // Blue
    }
    
    // Birth Flower Background Colors (12개월)
    object FlowerBackground {
        const val January = 0xFFFFE4E1L   // Misty Rose
        const val February = 0xFFF0F8FFL  // Alice Blue
        const val March = 0xFFFFF0F5L     // Lavender Blush
        const val April = 0xFFFFFAF0L     // Floral White
        const val May = 0xFFF0FFF0L       // Honeydew
        const val June = 0xFFFFF5EEL      // Seashell
        const val July = 0xFFF5FFFAL      // Mint Cream
        const val August = 0xFFFFFAFAL    // Snow
        const val September = 0xFFF0E68CL // Khaki
        const val October = 0xFFFFE4B5L   // Moccasin
        const val November = 0xFFFFDAB9L  // Peach Puff
        const val December = 0xFFF0FFFFL  // Azure
    }
    
    // Utility Colors
    object Utility {
        const val Divider = 0x1F000000L  // 12% Black
        const val Shadow = 0x33000000L   // 20% Black
        const val Ripple = 0x1F000000L   // 12% Black
        const val Locked = 0xFFBDBDBDL   // Grey
    }
}
