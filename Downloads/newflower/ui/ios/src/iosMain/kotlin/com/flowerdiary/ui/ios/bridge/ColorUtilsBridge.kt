package com.flowerdiary.ui.ios.bridge

/**
 * 색상 유틸리티 브릿지
 * SRP: 색상 변환 유틸리티만 담당
 */
object ColorUtilsBridge {
    
    private const val HEX_FORMAT = "#%08X"
    private const val SHIFT_ALPHA = 24
    private const val SHIFT_RED = 16
    private const val SHIFT_GREEN = 8
    private const val CHANNEL_MASK = 0xFF
    private const val MAX_COLOR_VALUE = 255.0f
    
    /**
     * ARGB Long을 헥스 스트링으로 변환
     */
    fun argbToHex(argb: Long): String {
        return HEX_FORMAT.format(argb)
    }
    
    /**
     * 헥스 스트링을 ARGB Long으로 변환
     */
    fun hexToArgb(hex: String): Long {
        val cleanHex = hex.removePrefix("#")
        return cleanHex.toLong(16)
    }
    
    /**
     * 알파 채널 분리
     */
    fun getAlpha(argb: Long): Float {
        return ((argb shr SHIFT_ALPHA) and CHANNEL_MASK) / MAX_COLOR_VALUE
    }
    
    /**
     * 빨간색 채널 분리
     */
    fun getRed(argb: Long): Float {
        return ((argb shr SHIFT_RED) and CHANNEL_MASK) / MAX_COLOR_VALUE
    }
    
    /**
     * 초록색 채널 분리
     */
    fun getGreen(argb: Long): Float {
        return ((argb shr SHIFT_GREEN) and CHANNEL_MASK) / MAX_COLOR_VALUE
    }
    
    /**
     * 파란색 채널 분리
     */
    fun getBlue(argb: Long): Float {
        return (argb and CHANNEL_MASK) / MAX_COLOR_VALUE
    }
}
