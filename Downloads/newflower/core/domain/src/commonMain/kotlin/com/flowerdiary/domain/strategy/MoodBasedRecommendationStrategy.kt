package com.flowerdiary.domain.strategy

import com.flowerdiary.common.constants.FlowerRecommendationConstants
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather

/**
 * 기분 기반 꽃 추천 전략
 * SRP: 기분에 따른 꽃 추천 점수 계산만 담당
 * 기분과 꽃의 의미를 매칭하여 적합도 점수 산출
 */
class MoodBasedRecommendationStrategy : FlowerRecommendationStrategy {
    
    override val priority: Int = 100
    override val name: String = "MoodBased"
    
    override fun calculateScore(flower: BirthFlower, mood: Mood, weather: Weather): Int {
        val score = when (mood) {
            Mood.HAPPY, Mood.EXCITED, Mood.LOVE -> {
                calculateKeywordMatchScore(flower, FlowerRecommendationConstants.MoodKeywords.HAPPINESS_KEYWORDS)
            }
            Mood.SAD, Mood.TIRED -> {
                calculateKeywordMatchScore(flower, FlowerRecommendationConstants.MoodKeywords.COMFORT_KEYWORDS)
            }
            Mood.PEACEFUL, Mood.GRATEFUL -> {
                calculateKeywordMatchScore(flower, FlowerRecommendationConstants.MoodKeywords.GRATITUDE_KEYWORDS)
            }
            else -> FlowerRecommendationConstants.Scoring.DEFAULT_MOOD_SCORE
        }
        
        Logger.debug(
            FlowerRecommendationConstants.LogTags.MOOD_STRATEGY,
            "Mood score for ${flower.nameKr}: $score (mood: $mood)"
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
            FlowerRecommendationConstants.Scoring.MOOD_MATCH_SCORE * matchCount
        } else {
            FlowerRecommendationConstants.Scoring.DEFAULT_MOOD_SCORE
        }
    }
}