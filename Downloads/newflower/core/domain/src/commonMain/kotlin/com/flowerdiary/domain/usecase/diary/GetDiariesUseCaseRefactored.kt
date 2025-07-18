package com.flowerdiary.domain.usecase.diary

import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.repository.DiaryRepository
import com.flowerdiary.domain.specification.AllDiaries
import com.flowerdiary.domain.specification.DateRangeDiaries
import com.flowerdiary.domain.specification.DiaryQuerySpecification
import com.flowerdiary.domain.specification.MoodDiaries
import com.flowerdiary.domain.specification.WeatherDiaries
import com.flowerdiary.domain.specification.YearMonthDiaries
import com.flowerdiary.domain.specification.and
import com.flowerdiary.domain.specification.or

/**
 * 일기 목록 조회 유스케이스 (리팩토링됨)
 * OCP 준수: Query Specification 패턴으로 새로운 쿼리 조건 확장 가능
 */
class GetDiariesUseCaseRefactored(
    private val diaryRepository: DiaryRepository
) {
    /**
     * 명세에 따른 일기 조회
     * 새로운 쿼리 조건 추가 시 이 메서드 수정 없이 새로운 Specification만 생성
     */
    suspend operator fun invoke(specification: DiaryQuerySpecification): Result<List<Diary>> {
        Logger.debug(TAG, "Getting diaries with specification: ${specification::class.simpleName}")
        
        return diaryRepository.findBySpecification(specification)
            .onSuccess { diaries ->
                Logger.info(TAG, "Found ${diaries.size} diaries")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to get diaries", it)
            }
    }
    
    /**
     * 편의 메서드들 - 자주 사용하는 쿼리들
     */
    suspend fun getAll(): Result<List<Diary>> = invoke(AllDiaries)
    
    suspend fun getByDateRange(startTimestamp: Long, endTimestamp: Long): Result<List<Diary>> = 
        invoke(DateRangeDiaries(startTimestamp, endTimestamp))
    
    suspend fun getByYearMonth(year: Int, month: Int): Result<List<Diary>> = 
        invoke(YearMonthDiaries(year, month))
    
    suspend fun getByMood(mood: String): Result<List<Diary>> = 
        invoke(MoodDiaries(mood))
    
    suspend fun getByWeather(weather: String): Result<List<Diary>> = 
        invoke(WeatherDiaries(weather))
    
    /**
     * 복잡한 쿼리 예시
     * 특정 년월의 행복한 기분의 일기들
     */
    suspend fun getHappyDiariesByYearMonth(year: Int, month: Int): Result<List<Diary>> = 
        invoke(YearMonthDiaries(year, month) and MoodDiaries("HAPPY"))
    
    /**
     * 맑은 날씨이거나 평화로운 기분의 일기들
     */
    suspend fun getSunnyOrPeacefulDiaries(): Result<List<Diary>> = 
        invoke(WeatherDiaries("SUNNY") or MoodDiaries("PEACEFUL"))
    
    companion object {
        private const val TAG = "GetDiariesUseCase"
    }
}
