package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.domain.model.Diary

/**
 * 일기 목록 필터 관리자
 * SRP: 일기 목록의 필터링 로직만 담당
 */
class DiaryListFilterManager {
    
    /**
     * 월별 필터링
     */
    fun filterByMonth(
        diaries: List<Diary>,
        year: Int,
        month: Int
    ): List<Diary> {
        return diaries.filter { diary ->
            val date = DateTimeUtil.toLocalDate(diary.createdAt)
            date.year == year && date.monthNumber == month
        }
    }
    
    /**
     * 선택된 월에 따른 일기 필터링
     */
    fun getFilteredDiaries(
        diaries: List<Diary>,
        selectedYear: Int?,
        selectedMonth: Int?
    ): List<Diary> {
        return if (selectedYear != null && selectedMonth != null) {
            filterByMonth(diaries, selectedYear, selectedMonth)
        } else {
            diaries
        }
    }
    
    /**
     * 현재 년월 가져오기
     */
    fun getCurrentYearMonth(): Pair<Int, Int> {
        val today = DateTimeUtil.getCurrentDate()
        return today.year to today.monthNumber
    }
    
    /**
     * 일기 목록에서 사용 가능한 년월 목록 추출
     */
    fun getAvailableMonths(diaries: List<Diary>): List<Pair<Int, Int>> {
        return diaries
            .map { diary ->
                val date = DateTimeUtil.toLocalDate(diary.createdAt)
                date.year to date.monthNumber
            }
            .distinct()
            .sortedByDescending { it.first * 12 + it.second }
    }
}