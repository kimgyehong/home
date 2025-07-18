package com.flowerdiary.common.constants

/**
 * 텍스트 관련 설정 상수
 * SRP: 텍스트 길이 제한 및 형식 설정만 담당
 */
object TextConfig {
    // 텍스트 길이 제한
    const val MAX_TITLE_LENGTH = 50
    const val MAX_CONTENT_LENGTH = 1000
    const val MAX_FLOWER_NAME_LENGTH = 30
    const val MAX_FLOWER_MEANING_LENGTH = 100
    const val MAX_FLOWER_DESCRIPTION_LENGTH = 500
    const val PREVIEW_LENGTH = 100
    
    // 폰트 설정
    const val DEFAULT_FONT_SIZE = 16.0f
    const val MIN_FONT_SIZE = 10.0f
    const val MAX_FONT_SIZE = 24.0f
    const val DEFAULT_FONT_FAMILY = "default"
    
    // 날짜 형식
    const val DATE_FORMAT_DISPLAY = "yyyy년 MM월 dd일"
    const val DATE_FORMAT_ISO = "yyyy-MM-dd"
    const val TIME_FORMAT_DISPLAY = "HH:mm"
    
    // UI 텍스트 상수
    const val DEFAULT_TITLE_PLACEHOLDER = "제목 없음"
}