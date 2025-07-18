package com.flowerdiary.domain.usecase.flower

import com.flowerdiary.common.platform.DateInfo

/**
 * 테스트용 날짜 유틸리티
 * SRP: 테스트에서 일관된 날짜 데이터 제공만 담당
 */
internal object TestDateTimeUtil {
    
    private const val TEST_YEAR = 2024
    private const val TEST_MONTH = 1
    private const val TEST_DAY = 1
    private const val TEST_DAY_OF_WEEK = 1
    
    fun getCurrentDate(): DateInfo {
        return DateInfo(
            year = TEST_YEAR,
            month = TEST_MONTH,
            day = TEST_DAY,
            dayOfWeek = TEST_DAY_OF_WEEK
        )
    }
}
