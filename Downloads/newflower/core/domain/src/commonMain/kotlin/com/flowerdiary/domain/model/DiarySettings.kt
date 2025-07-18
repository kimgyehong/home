package com.flowerdiary.domain.model

/**
 * 일기 꾸미기 설정 값 객체
 * 폰트, 색상, 배경 테마 관리
 */
data class DiarySettings(
    val fontFamily: String = DEFAULT_FONT_FAMILY,
    val fontColor: Long = DEFAULT_FONT_COLOR,
    val backgroundTheme: String = DEFAULT_BACKGROUND_THEME
) {
    companion object {
        const val DEFAULT_FONT_FAMILY = "default"
        const val DEFAULT_FONT_COLOR = 0xFF000000L // Black
        const val DEFAULT_BACKGROUND_THEME = "default"
        
        // 사용 가능한 폰트 패밀리
        val AVAILABLE_FONTS = listOf(
            "default" to "기본",
            "handwriting" to "손글씨",
            "serif" to "명조체",
            "sans-serif" to "고딕체",
            "cursive" to "필기체"
        )
        
        // 사용 가능한 폰트 색상
        val AVAILABLE_COLORS = listOf(
            0xFF000000L to "검정",
            0xFF444444L to "진회색",
            0xFF666666L to "회색",
            0xFF1976D2L to "파랑",
            0xFF388E3CL to "초록",
            0xFFD32F2FL to "빨강",
            0xFFF57C00L to "주황",
            0xFF7B1FA2L to "보라"
        )
        
        // 사용 가능한 배경 테마
        val AVAILABLE_THEMES = listOf(
            "default" to "기본",
            "flower" to "꽃 배경",
            "paper" to "종이 질감",
            "watercolor" to "수채화",
            "vintage" to "빈티지"
        )
    }
    
    /**
     * 설정이 기본값인지 확인
     */
    fun isDefault(): Boolean =
        fontFamily == DEFAULT_FONT_FAMILY &&
        fontColor == DEFAULT_FONT_COLOR &&
        backgroundTheme == DEFAULT_BACKGROUND_THEME
}
