package com.flowerdiary.domain.service

import com.flowerdiary.common.constants.FlowerRecommendationConstants
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather
import com.flowerdiary.domain.strategy.FlowerRecommendationStrategy
import com.flowerdiary.domain.strategy.RecommendationResult

/**
 * 꽃 추천 오케스트레이터
 * SRP: 여러 추천 전략을 조합하여 최적의 꽃 추천 결과 생성
 * Strategy Pattern + Composite Pattern 적용
 */
class FlowerRecommendationOrchestrator(
    private val strategies: List<FlowerRecommendationStrategy>
) {
    
    /**
     * 여러 전략을 조합하여 꽃 추천 결과 생성
     */
    fun recommend(
        availableFlowers: List<BirthFlower>,
        mood: Mood,
        weather: Weather
    ): RecommendationResult? {
        if (availableFlowers.isEmpty()) {
            Logger.warning(
                FlowerRecommendationConstants.LogTags.ORCHESTRATOR,
                "No flowers available for recommendation"
            )
            return null
        }
        
        val scoredFlowers = availableFlowers.map { flower ->
            val strategyScores = mutableMapOf<String, Int>()
            var totalScore = 0
            
            strategies.forEach { strategy ->
                val score = strategy.calculateScore(flower, mood, weather)
                strategyScores[strategy.name] = score
                totalScore += score
            }
            
            RecommendationResult(
                flower = flower,
                totalScore = totalScore,
                strategyScores = strategyScores.toMap()
            )
        }
        
        val bestResult = scoredFlowers.maxByOrNull { it.totalScore }
        
        if (bestResult != null) {
            Logger.info(
                FlowerRecommendationConstants.LogTags.ORCHESTRATOR,
                "Best flower: ${bestResult.flower.nameKr} (score: ${bestResult.totalScore})"
            )
        }
        
        return bestResult
    }
    
    /**
     * 추천 전략 목록 조회 (우선순위 순)
     */
    fun getStrategies(): List<FlowerRecommendationStrategy> {
        return strategies.sortedByDescending { it.priority }
    }
}