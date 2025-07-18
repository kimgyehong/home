package com.flowerdiary.common.constants

/**
 * UI 메시지 상수 - 하드코딩 제거를 위한 중앙집중식 관리
 * 향후 다국어 지원을 위해 expect/actual로 변경 가능
 */
object Messages {
    
    // 에러 메시지
    const val ERROR_UNKNOWN = "알 수 없는 오류가 발생했습니다"
    const val ERROR_REFRESH_FAILED = "새로고침 실패"
    const val ERROR_DELETE_FAILED = "삭제 실패"
    const val ERROR_GENERAL = "오류가 발생했습니다"
    const val ERROR_DIARY_NOT_FOUND = "일기를 찾을 수 없습니다"
    const val ERROR_FLOWER_NOT_AVAILABLE = "오늘의 꽃을 불러올 수 없습니다"
    const val ERROR_NO_RECOMMENDED_FLOWER = "추천할 꽃이 없습니다"
    
    // 성공 메시지
    const val SUCCESS_DIARY_DELETED = "일기가 삭제되었습니다"
    const val SUCCESS_DIARY_UPDATED = "일기가 수정되었습니다"
    const val SUCCESS_DIARY_SAVED = "일기가 저장되었습니다"
    const val SUCCESS_FLOWER_SELECTED = "오늘의 꽃이 선택되었습니다"
    
    // 유효성 검증 메시지
    fun validationTitleLength(maxLength: Int) = "제목은 ${maxLength}자까지 입력 가능합니다"
    fun validationContentLength(maxLength: Int) = "내용은 ${maxLength}자까지 입력 가능합니다"
    const val VALIDATION_EMPTY_CONTENT = "제목이나 내용을 입력해주세요"
    const val VALIDATION_NO_FLOWER = "꽃을 선택해주세요"
    
    // 추천 메시지
    fun recommendationFlower(flowerName: String) = "${flowerName}이(가) 추천되었습니다"
    
    // 시간 표시
    const val TIME_JUST_NOW = "방금 전"
    fun timeMinutesAgo(minutes: Long) = "${minutes}분 전"
    fun timeHoursAgo(hours: Long) = "${hours}시간 전"
    fun timeDaysAgo(days: Long) = "${days}일 전"
    fun timeWeeksAgo(weeks: Long) = "${weeks}주 전"
    fun timeMonthsAgo(months: Long) = "${months}개월 전"
    fun timeYearsAgo(years: Long) = "${years}년 전"
    
    // 설정 관련 메시지
    const val ERROR_SETTINGS_LOAD_FAILED = "설정을 불러올 수 없습니다"
    const val ERROR_BGM_CHANGE_FAILED = "BGM 설정 변경 실패"
    const val ERROR_CACHE_DELETE_FAILED = "캐시 삭제 실패"
    const val ERROR_DATA_RESET_FAILED = "데이터 초기화 실패"
    
    const val SUCCESS_FONT_SIZE_CHANGED = "폰트 크기가 변경되었습니다"
    const val SUCCESS_NOTIFICATION_ENABLED = "알림이 활성화되었습니다"
    const val SUCCESS_NOTIFICATION_DISABLED = "알림이 비활성화되었습니다"
    const val SUCCESS_CACHE_DELETED = "캐시가 삭제되었습니다"
    
    // 확인 메시지
    const val CONFIRM_DATA_RESET_TITLE = "데이터 초기화"
    const val CONFIRM_DATA_RESET_MESSAGE = "모든 데이터가 삭제됩니다. 계속하시겠습니까?"
    
    // 타이틀
    const val TITLE_SETTINGS = "설정"
    const val TITLE_BGM_SETTINGS = "BGM 설정"
    const val TITLE_DISPLAY_SETTINGS = "화면 설정"
    const val TITLE_DATA_SETTINGS = "데이터 관리"
}
