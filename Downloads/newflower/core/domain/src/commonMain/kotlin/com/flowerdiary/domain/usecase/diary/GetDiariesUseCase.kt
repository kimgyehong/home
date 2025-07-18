package com.flowerdiary.domain.usecase.diary

import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.repository.DiaryRepository

/**
 * 일기 목록 조회 유스케이스
 * 다양한 조건으로 일기 목록 조회
 */
class GetDiariesUseCase(
    private val diaryRepository: DiaryRepository
) {
    /**
     * 모든 일기 조회 (최신순)
     */
    suspend fun getAll(): Result<List<Diary>> {
        Logger.debug(TAG, "Getting all diaries")
        
        return diaryRepository.findAll()
            .onSuccess { diaries ->
                Logger.info(TAG, "Found ${diaries.size} diaries")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to get diaries", it)
            }
    }
    
    /**
     * 날짜 범위로 일기 조회
     */
    suspend fun getByDateRange(startTimestamp: Long, endTimestamp: Long): Result<List<Diary>> {
        require(startTimestamp <= endTimestamp) { 
            "Start timestamp must be before or equal to end timestamp" 
        }
        
        Logger.debug(TAG, "Getting diaries by date range: $startTimestamp - $endTimestamp")
        
        return diaryRepository.findByDateRange(startTimestamp, endTimestamp)
            .onSuccess { diaries ->
                Logger.info(TAG, "Found ${diaries.size} diaries in date range")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to get diaries by date range", it)
            }
    }
    
    /**
     * 특정 년월의 일기 조회
     */
    suspend fun getByYearMonth(year: Int, month: Int): Result<List<Diary>> {
        require(year > 0) { "Year must be positive" }
        require(month in 1..12) { "Month must be between 1 and 12" }
        
        Logger.debug(TAG, "Getting diaries for $year-$month")
        
        return diaryRepository.findByYearMonth(year, month)
            .onSuccess { diaries ->
                Logger.info(TAG, "Found ${diaries.size} diaries for $year-$month")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to get diaries for $year-$month", it)
            }
    }
    
    companion object {
        private const val TAG = "GetDiariesUseCase"
    }
}
