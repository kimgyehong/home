package com.flowerdiary.data.source

import com.flowerdiary.common.constants.UIConfig

/**
 * 탄생화 배경색 생성기
 * SRP: 탄생화 배경색 생성 로직만 담당
 */
class BirthFlowerColorGenerator {
    
    private val predefinedColors = mapOf(
        "장미" to 0xFFFF69B4L,
        "국화" to 0xFFFFD700L,
        "백합" to 0xFFFFFFFF,
        "개나리" to 0xFFFFFF00L,
        "벚꽃" to 0xFFFFB6C1L,
        "튤립" to 0xFFFF6347L,
        "수선화" to 0xFFFFFFFL,
        "히아신스" to 0xFF9370DBL,
        "제비꽃" to 0xFF9370DBL,
        "앵초" to 0xFFFFB6C1L
    )
    
    /**
     * 꽃 이름에 따른 배경색 생성
     */
    fun generateColor(flowerName: String): Long {
        // 사전 정의된 색상 매핑 확인
        predefinedColors.entries.forEach { (key, color) ->
            if (flowerName.contains(key)) {
                return color
            }
        }
        
        // 알고리즘 기반 색상 생성
        return generateColorFromName(flowerName)
    }
    
    /**
     * 이름 기반 알고리즘 색상 생성
     */
    private fun generateColorFromName(name: String): Long {
        val hashCode = name.hashCode()
        val hue = (hashCode and 0xFF) * UIConfig.COLOR_HUE_MAX / UIConfig.COLOR_HASH_MAX
        val saturation = UIConfig.COLOR_SATURATION_DEFAULT
        val lightness = UIConfig.COLOR_LIGHTNESS_DEFAULT
        
        return hslToArgb(hue, saturation, lightness)
    }
    
    /**
     * HSL을 ARGB로 변환
     */
    private fun hslToArgb(hue: Int, saturation: Float, lightness: Float): Long {
        val c = (1 - kotlin.math.abs(2 * lightness - 1)) * saturation
        val x = c * (1 - kotlin.math.abs((hue / UIConfig.COLOR_HUE_SEGMENT.toFloat()) % 2 - 1))
        val m = lightness - c / 2
        
        val (r, g, b) = when (hue / UIConfig.COLOR_HUE_SEGMENT) {
            0 -> Triple(c, x, UIConfig.COLOR_MIN)
            1 -> Triple(x, c, UIConfig.COLOR_MIN)
            2 -> Triple(UIConfig.COLOR_MIN, c, x)
            3 -> Triple(UIConfig.COLOR_MIN, x, c)
            4 -> Triple(x, UIConfig.COLOR_MIN, c)
            5 -> Triple(c, UIConfig.COLOR_MIN, x)
            else -> Triple(UIConfig.COLOR_MIN, UIConfig.COLOR_MIN, UIConfig.COLOR_MIN)
        }
        
        val red = ((r + m) * UIConfig.COLOR_RGB_MAX).toInt()
        val green = ((g + m) * UIConfig.COLOR_RGB_MAX).toInt()
        val blue = ((b + m) * UIConfig.COLOR_RGB_MAX).toInt()
        
        return (UIConfig.COLOR_ALPHA_OPAQUE shl 24) or (red shl 16) or (green shl 8) or blue
    }
}