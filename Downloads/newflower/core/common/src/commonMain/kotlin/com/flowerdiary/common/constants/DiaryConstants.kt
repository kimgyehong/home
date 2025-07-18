package com.flowerdiary.common.constants

/**
 * 일기 관련 상수 - 하드코딩 완전 제거
 * SRP: 일기 도메인 관련 상수만 담당
 * 모든 매직 넘버와 문자열을 중앙집중식 관리
 */
object DiaryConstants {
    
    /**
     * 길이 제한 상수
     */
    object Length {
        const val MAX_TITLE_LENGTH = 100
        const val MAX_CONTENT_LENGTH = 5000
        const val PREVIEW_LENGTH = 150
        const val MIN_CONTENT_FOR_PREVIEW = 1
    }
    
    /**
     * 폰트 관련 상수
     */
    object Font {
        const val DEFAULT_FONT_FAMILY = "NotoSans"
        const val DEFAULT_FONT_SIZE = 16f
        const val MIN_FONT_SIZE = 12f
        const val MAX_FONT_SIZE = 24f
    }
    
    /**
     * 색상 관련 상수
     */
    object Color {
        const val DEFAULT_FONT_COLOR = 0xFF000000L // ARGB Black
        const val DEFAULT_BACKGROUND_COLOR = 0xFFFFFFFFL // ARGB White
    }
    
    /**
     * 테마 관련 상수
     */
    object Theme {
        const val DEFAULT_BACKGROUND_THEME = "classic"
        const val SPRING_THEME = "spring"
        const val SUMMER_THEME = "summer"
        const val AUTUMN_THEME = "autumn"
        const val WINTER_THEME = "winter"
    }
    
    /**
     * 기본값 상수
     */
    object Default {
        const val TITLE_PLACEHOLDER = "제목 없음"
        const val EMPTY_CONTENT = ""
        const val PREVIEW_SUFFIX = "..."
        const val MIN_TIMESTAMP = 0L
    }
    
    /**
     * 검증 메시지 상수
     */
    object ValidationMessages {
        const val TITLE_TOO_LONG = "제목이 너무 깁니다"
        const val CONTENT_TOO_LONG = "내용이 너무 깁니다"
        const val INVALID_TIMESTAMP = "유효하지 않은 시간입니다"
        const val INVALID_FONT_SIZE = "폰트 크기가 유효하지 않습니다"
        const val UPDATED_BEFORE_CREATED = "수정 시간이 생성 시간보다 빠릅니다"
    }
    
    /**
     * 로깅 태그
     */
    object LogTags {
        const val DIARY_VALIDATOR = "DiaryValidator"
        const val DIARY_FORMATTER = "DiaryFormatter"
        const val DIARY_UPDATE_SERVICE = "DiaryUpdateService"
        const val DIARY_STATE_CHECKER = "DiaryStateChecker"
    }
}