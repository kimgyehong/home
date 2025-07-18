package com.flowerdiary.ui.android.constants

/**
 * UI 관련 상수들
 * SRP: UI 상수 정의만 담당
 * 하드코딩 방지를 위한 중앙집중식 상수 관리
 */
object UiConstants {
    
    /**
     * 간격 관련 상수들
     */
    object Spacing {
        const val CONTENT_PADDING = 16
        const val SECTION_SPACING = 24
        const val MEDIUM_SPACING = 16
        const val SMALL_SPACING = 8
        const val ITEM_SPACING = 8
        const val CARD_PADDING = 16
        const val DIARY_LIST_ITEM_SPACING = 12
    }
    
    /**
     * 크기 관련 상수들
     */
    object Size {
        const val CONTENT_FIELD_HEIGHT = 200
    }
    
    /**
     * 투명도 관련 상수들
     */
    object Alpha {
        const val FLOWER_BACKGROUND_ALPHA = 0.15f
        const val NOTEBOOK_ALPHA = 0.9f
    }
    
    /**
     * 문자열 관련 상수들
     */
    object Strings {
        const val NEW_DIARY_TITLE = "새 일기"
        const val EDIT_DIARY_TITLE = "일기 수정"
        const val BACK_BUTTON_DESCRIPTION = "뒤로가기"
        const val SAVE_BUTTON_DESCRIPTION = "저장"
        
        // DiaryEditor 관련 문자열
        const val TITLE_FIELD_PLACEHOLDER = "일기 제목을 입력하세요"
        const val CONTENT_FIELD_PLACEHOLDER = "오늘 하루는 어떠셨나요?"
        const val MOOD_SELECTOR_TITLE = "오늘의 기분"
        const val WEATHER_SELECTOR_TITLE = "오늘의 날씨"
        const val FLOWER_SELECTOR_TITLE = "오늘의 꽃"
        const val DECORATION_SETTINGS_TITLE = "꾸미기"
        const val USE_TODAY_FLOWER = "오늘의 꽃 사용"
        const val RECOMMEND_FLOWER = "꽃 추천"
        const val RECOMMENDED_FLOWERS = "추천 꽃"
        
        // Settings 관련 문자열
        const val SETTINGS_TITLE = "설정"
        const val DISPLAY_SETTINGS_TITLE = "화면 설정"
        const val NOTIFICATION_SETTINGS_TITLE = "알림 설정"
        const val DATA_SETTINGS_TITLE = "데이터 설정"
        const val BGM_SETTINGS_TITLE = "배경음 설정"
        const val RESET_SETTINGS_TITLE = "초기화"
        const val THEME_LIGHT = "라이트"
        const val THEME_DARK = "다크"
        const val THEME_SYSTEM = "시스템"
        
        // Dialog 관련 문자열
        const val RESET_DIALOG_TITLE = "설정 초기화"
        const val RESET_DIALOG_MESSAGE = "모든 설정을 초기화하시겠습니까? 이 작업은 되돌릴 수 없습니다."
        const val CONFIRM_BUTTON = "확인"
        const val CANCEL_BUTTON = "취소"
        
        // 알림 설정 관련 문자열
        const val DAILY_NOTIFICATION = "일일 알림"
        const val NOTIFICATION_TIME = "알림 시간"
        const val FONT_SIZE = "폰트 크기"
        const val AUTO_UNLOCK = "자동 해금"
        
        // 데이터 설정 관련 문자열
        const val AUTO_BACKUP = "자동 백업"
        const val LAST_BACKUP = "마지막 백업"
        const val EXPORT_DATA = "데이터 내보내기"
        const val IMPORT_DATA = "데이터 가져오기"
        
        // 배경음 설정 관련 문자열
        const val BGM_PLAYBACK = "배경음 재생"
        const val VOLUME = "볼륨"
        
        // 초기화 관련 문자열
        const val RESET_ALL_SETTINGS = "모든 설정 초기화"
        const val RESET_DESCRIPTION = "모든 설정을 기본값으로 되돌립니다"
        
        // DiaryList 관련 문자열
        const val DIARY_LIST_TITLE = "꽃 다이어리"
        const val COLLECTION_BUTTON = "도감"
        const val NEW_DIARY_BUTTON = "새 일기 작성"
        const val EMPTY_DIARY_MESSAGE = "아직 작성한 일기가 없어요"
        const val EMPTY_DIARY_SUBTITLE = "첫 일기를 작성해보세요"
        const val RETRY_BUTTON = "다시 시도"
    }
    
    /**
     * 제한 관련 상수들
     */
    object Limits {
        const val TITLE_MAX_LENGTH = 50
        const val CONTENT_MAX_LENGTH = 5000
        const val DIARY_PREVIEW_MAX_LINES = 2
    }
}