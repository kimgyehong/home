package com.flowerdiary.domain.usecase.flower

import com.flowerdiary.common.constants.FlowerRecommendationConstants
import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather
import com.flowerdiary.domain.repository.BirthFlowerRepository
import com.flowerdiary.domain.service.FlowerRecommendationOrchestrator

/**
 * 꽃 추천 유스케이스 (리팩토링 버전)
 * SRP: 꽃 추천 요청 처리와 결과 반환만 담당
 * 실제 추천 로직은 FlowerRecommendationOrchestrator에 위임
 */
class RecommendFlowerUseCase(
    private val birthFlowerRepository: BirthFlowerRepository,
    private val orchestrator: FlowerRecommendationOrchestrator
) {
    
    /**
     * 기분과 날씨에 따른 꽃 추천
     */
    suspend fun recommendByMoodAndWeather(
        mood: Mood,
        weather: Weather
    ): Result<BirthFlower?> {
        return try {
            val unlockedFlowers = birthFlowerRepository.findUnlocked().getOrNull()
            if (unlockedFlowers.isNullOrEmpty()) {
                Logger.warning(TAG, "No unlocked flowers available for recommendation")
                return Result.success(null)
            }
            
            val result = orchestrator.recommend(unlockedFlowers, mood, weather)
            Result.success(result?.flower)
        } catch (e: Exception) {
            Logger.error(TAG, "Failed to recommend flower by mood and weather", e)
            Result.failure(e)
        }
    }
    
    /**
     * 오늘의 추천 꽃 (오늘 날짜의 탄생화)
     */
    suspend fun recommendTodayFlower(): Result<BirthFlower?> {
        return try {
            val today = DateTimeUtil.getCurrentDate()
            Logger.debug(TAG, "Getting today's flower: ${today.month}/${today.day}")
            
            birthFlowerRepository.findByDate(today.month, today.day)
        } catch (e: Exception) {
            Logger.error(TAG, "Failed to get today's flower", e)
            Result.failure(e)
        }
    }
    
    companion object {
        private const val TAG = FlowerRecommendationConstants.LogTags.RECOMMENDATION_USECASE
    }
}