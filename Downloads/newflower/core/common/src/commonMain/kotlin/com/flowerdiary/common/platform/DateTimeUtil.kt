package com.flowerdiary.common.platform

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toEpochMilliseconds
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

/**
 * 플랫폼 중립적 날짜/시간 유틸리티
 * kotlinx-datetime 기반으로 구현
 */
object DateTimeUtil {
    
    /**
     * 현재 시간을 밀리초로 반환
     */
    fun now(): Long = Clock.System.now().toEpochMilliseconds()
    
    /**
     * 현재 날짜 정보 반환
     */
    fun getCurrentDate(): DateInfo {
        val now = Clock.System.now()
        val localDate = now.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return DateInfo(
            year = localDate.year,
            month = localDate.monthNumber,
            day = localDate.dayOfMonth,
            dayOfYear = localDate.dayOfYear
        )
    }
    
    /**
     * 타임스탬프를 날짜 정보로 변환
     */
    fun toDateInfo(timestamp: Long): DateInfo {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return DateInfo(
            year = localDate.year,
            month = localDate.monthNumber,
            day = localDate.dayOfMonth,
            dayOfYear = localDate.dayOfYear
        )
    }
    
    /**
     * 날짜 정보를 타임스탬프로 변환
     */
    fun toTimestamp(year: Int, month: Int, day: Int): Long {
        val localDate = LocalDate(year, month, day)
        val localDateTime = LocalDateTime(localDate, LocalTime(0, 0, 0, 0))
        return localDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }
    
    /**
     * 두 날짜 사이의 일수 계산
     */
    fun daysBetween(timestamp1: Long, timestamp2: Long): Int {
        val instant1 = Instant.fromEpochMilliseconds(timestamp1)
        val instant2 = Instant.fromEpochMilliseconds(timestamp2)
        val date1 = instant1.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val date2 = instant2.toLocalDateTime(TimeZone.currentSystemDefault()).date
        return (date2.toEpochDays() - date1.toEpochDays()).toInt()
    }
    
    /**
     * 오늘이 특정 날짜와 같은지 확인
     */
    fun isToday(timestamp: Long): Boolean {
        val today = getCurrentDate()
        val date = toDateInfo(timestamp)
        return today.year == date.year && 
               today.month == date.month && 
               today.day == date.day
    }
}

/**
 * 날짜 정보 데이터 클래스
 */
data class DateInfo(
    val year: Int,
    val month: Int,
    val day: Int,
    val dayOfYear: Int
)
