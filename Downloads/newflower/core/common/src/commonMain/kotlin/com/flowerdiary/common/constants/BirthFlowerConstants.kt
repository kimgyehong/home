package com.flowerdiary.common.constants

/**
 * 탄생화 관련 상수 정의
 * 하드코딩 방지 및 중앙집중식 관리
 */
object BirthFlowerConstants {
    // 날짜 관련 상수
    const val DATE_KEY_SEPARATOR = "-"
    const val DATE_KEY_LENGTH = 5
    const val MONTH_START_INDEX = 0
    const val MONTH_END_INDEX = 2
    const val DAY_START_INDEX = 3
    const val DAY_END_INDEX = 5
    
    // 이미지 파일 관련 상수
    const val IMAGE_FILE_EXTENSION = ".jpg"
    const val IMAGE_FILE_NAME_LENGTH = 8 // "0101.jpg"
    const val DATE_PAD_LENGTH = 2
    const val DATE_PAD_CHAR = '0'
    
    // 데이터 검증 상수
    const val MIN_MONTH = 1
    const val MAX_MONTH = 12
    const val MIN_DAY = 1
    const val MAX_DAY = 31
    const val MIN_FLOWER_NAME_LENGTH = 1
    const val MIN_MEANING_LENGTH = 1
    
    // 월별 일수
    val DAYS_IN_MONTH = mapOf(
        1 to 31, 2 to 29, 3 to 31, 4 to 30,
        5 to 31, 6 to 30, 7 to 31, 8 to 31,
        9 to 30, 10 to 31, 11 to 30, 12 to 31
    )
    
    // 에러 메시지
    const val ERROR_INVALID_DATE_KEY = "Invalid date key format"
    const val ERROR_INVALID_MONTH = "Invalid month value"
    const val ERROR_INVALID_DAY = "Invalid day value"
    const val ERROR_EMPTY_FLOWER_NAME = "Flower name cannot be empty"
    const val ERROR_EMPTY_MEANING = "Meaning cannot be empty"
    const val ERROR_INVALID_DAY_FOR_MONTH = "Invalid day for given month"
}
