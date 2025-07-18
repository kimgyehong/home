package com.flowerdiary.domain.specification

import com.flowerdiary.common.constants.Config
import com.flowerdiary.domain.model.Diary

/**
 * 날짜 범위로 조회
 */
data class DateRangeDiaries(
    val startTimestamp: Long,
    val endTimestamp: Long
) : DiaryQuerySpecification {
    
    init {
        require(startTimestamp <= endTimestamp) { 
            "Start timestamp must be before or equal to end timestamp" 
        }
    }
    
    override fun toSqlWhereClause(): String = 
        "created_at >= :startTimestamp AND created_at <= :endTimestamp"
    
    override fun toParameters(): Map<String, Any> = mapOf(
        "startTimestamp" to startTimestamp,
        "endTimestamp" to endTimestamp
    )
    
    override fun isSatisfiedBy(diary: Diary): Boolean =
        diary.createdAt.toEpochMilliseconds() in startTimestamp..endTimestamp
}

/**
 * 년월로 조회
 */
data class YearMonthDiaries(
    val year: Int,
    val month: Int
) : DiaryQuerySpecification {
    
    init {
        require(year > 0) { "Year must be positive" }
        require(month in Config.MONTH_MIN..Config.MONTH_MAX) { 
            "Month must be between ${Config.MONTH_MIN} and ${Config.MONTH_MAX}" 
        }
    }
    
    override fun toSqlWhereClause(): String = 
        "strftime('%Y', datetime(created_at/${Config.MILLISECONDS_PER_SECOND}, 'unixepoch')) = :year " +
        "AND strftime('%m', datetime(created_at/${Config.MILLISECONDS_PER_SECOND}, 'unixepoch')) = :month"
    
    override fun toParameters(): Map<String, Any> = mapOf(
        "year" to year.toString().padStart(Config.YEAR_PAD_LENGTH, '0'),
        "month" to month.toString().padStart(Config.MONTH_PAD_LENGTH, '0')
    )
    
    override fun isSatisfiedBy(diary: Diary): Boolean {
        val diaryDate = diary.createdAt
        return diaryDate.year == year && diaryDate.monthNumber == month
    }
}