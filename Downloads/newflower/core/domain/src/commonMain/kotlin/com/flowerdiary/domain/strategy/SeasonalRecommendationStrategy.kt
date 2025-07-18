package com.flowerdiary.domain.strategy

import com.flowerdiary.common.constants.FlowerRecommendationConstants
import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather
import kotlin.math.abs

/**
 * 계절 기반 꽃 추천 전략
 * SRP: 계절(월)에 따른 꽃 추천 점수 계산만 담당
 * 현재 월과 꽃의 탄생월을 비교하여 계절 적합도 점수 산출
 */
class SeasonalRecommendationStrategy : FlowerRecommendationStrategy {
    
    override val priority: Int = 120
    override val name: String = "Seasonal"
    
    override fun calculateScore(flower: BirthFlower, mood: Mood, weather: Weather): Int {
        val currentMonth = DateTimeUtil.getCurrentDate().month
        val flowerMonth = flower.month
        
        val score = when {
            flowerMonth == currentMonth -> {
                FlowerRecommendationConstants.Scoring.SEASONAL_BONUS
            }
            abs(flowerMonth - currentMonth) <= FlowerRecommendationConstants.Scoring.MONTH_DIFFERENCE_THRESHOLD -> {
                FlowerRecommendationConstants.Scoring.ADJACENT_MONTH_BONUS
            }
            else -> 0
        }
        
        Logger.debug(
            FlowerRecommendationConstants.LogTags.SEASONAL_STRATEGY,
            "Seasonal score for ${flower.nameKr}: $score (current: $currentMonth, flower: $flowerMonth)"
        )
        
        return score
    }
}