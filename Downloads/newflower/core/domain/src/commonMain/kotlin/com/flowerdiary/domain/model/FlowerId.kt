package com.flowerdiary.domain.model

import com.flowerdiary.common.constants.Config

/**
 * 꽃 ID 값 객체
 * 1-365 범위의 일 년 중 날짜를 나타냄
 */
value class FlowerId(val value: Int) {
    init {
        require(value in 1..Config.DAYS_PER_YEAR) { "FlowerId must be between 1 and ${Config.DAYS_PER_YEAR}" }
    }
    
    companion object {
        /**
         * 월과 일로부터 FlowerId 생성
         */
        fun fromDate(month: Int, day: Int): FlowerId {
            require(month in 1..Config.MONTHS_PER_YEAR) { "Month must be between 1 and ${Config.MONTHS_PER_YEAR}" }
            
            val daysBeforeMonth = when (month) {
                1 -> 0
                2 -> 31
                3 -> 59
                4 -> 90
                5 -> 120
                6 -> 151
                7 -> 181
                8 -> 212
                9 -> 243
                10 -> 273
                11 -> 304
                12 -> 334
                else -> throw IllegalArgumentException("Invalid month: $month")
            }
            
            val dayOfYear = daysBeforeMonth + day
            require(dayOfYear in 1..Config.DAYS_PER_YEAR) { "Invalid date: $month/$day" }
            
            return FlowerId(dayOfYear)
        }
    }
}
