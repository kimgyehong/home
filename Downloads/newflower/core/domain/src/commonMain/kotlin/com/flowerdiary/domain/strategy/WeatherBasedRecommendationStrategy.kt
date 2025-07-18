package com.flowerdiary.domain.strategy

import com.flowerdiary.common.constants.FlowerRecommendationConstants
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather

/**
 * 날씨 기반 꽃 추천 전략
 * SRP: 날씨에 따른 꽃 추천 점수 계산만 담당
 * 날씨와 꽃의 의미를 매칭하여 적합도 점수 산출
 */
class WeatherBasedRecommendationStrategy : FlowerRecommendationStrategy {
    
    override val priority: Int = 80
    override val name: String = "WeatherBased"
    
    override fun calculateScore(flower: BirthFlower, mood: Mood, weather: Weather): Int {
        val score = when (weather) {
            Weather.SUNNY -> {
                calculateKeywordMatchScore(flower, FlowerRecommendationConstants.WeatherKeywords.SUNNY_KEYWORDS)
            }
            Weather.RAINY, Weather.CLOUDY -> {
                calculateKeywordMatchScore(flower, FlowerRecommendationConstants.WeatherKeywords.CALM_KEYWORDS)
            }
            Weather.SNOWY -> {
                calculateKeywordMatchScore(flower, FlowerRecommendationConstants.WeatherKeywords.FRESH_KEYWORDS)
            }
            else -> FlowerRecommendationConstants.Scoring.DEFAULT_WEATHER_SCORE
        }
        
        Logger.debug(
            FlowerRecommendationConstants.LogTags.WEATHER_STRATEGY,
            "Weather score for ${flower.nameKr}: $score (weather: $weather)"
        )
        
        return score
    }
    
    /**
     * 키워드 매칭 점수 계산
     * 꽃의 의미에 포함된 키워드 개수에 따라 점수 산출
     */
    private fun calculateKeywordMatchScore(flower: BirthFlower, keywords: Set<String>): Int {
        val matchCount = keywords.count { keyword -> 
            flower.meaning.contains(keyword, ignoreCase = true) 
        }
        
        return if (matchCount > 0) {
            FlowerRecommendationConstants.Scoring.WEATHER_MATCH_SCORE * matchCount
        } else {
            FlowerRecommendationConstants.Scoring.DEFAULT_WEATHER_SCORE
        }
    }
}