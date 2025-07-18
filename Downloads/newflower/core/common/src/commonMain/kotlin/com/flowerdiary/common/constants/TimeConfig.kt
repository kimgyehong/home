package com.flowerdiary.common.constants

/**
 * 시간 관련 설정 상수
 * SRP: 시간 및 날짜 관련 설정만 담당
 */
object TimeConfig {
    // 시간 관련 상수
    const val MINUTES_PER_HOUR = 60
    const val HOURS_PER_DAY = 24
    const val DAYS_PER_WEEK = 7
    const val DAYS_PER_MONTH = 30  // 평균값
    const val DAYS_PER_YEAR = 365
    const val MONTHS_PER_YEAR = 12
    const val MILLISECONDS_PER_SECOND = 1000L
    
    // 날짜 관련 상수
    const val MONTH_MIN = 1
    const val MONTH_MAX = 12
    const val YEAR_PAD_LENGTH = 4
    const val MONTH_PAD_LENGTH = 2
    const val DAY_MULTIPLIER = 100
    
    // 매직 넘버 대체
    const val ID_RANDOM_RANGE = 9999
    const val HEX_PADDING_LENGTH = 8
    const val HEX_COLOR_START_INDEX = 2
}