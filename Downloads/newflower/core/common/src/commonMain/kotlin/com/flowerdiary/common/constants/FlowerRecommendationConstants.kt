package com.flowerdiary.common.constants

/**
 * 꽃 추천 시스템 관련 상수
 * SRP: 꽃 추천 로직에 사용되는 상수만 담당
 * 하드코딩 제거를 위한 중앙집중식 관리
 */
object FlowerRecommendationConstants {
    
    /**
     * 추천 점수 상수
     */
    object Scoring {
        const val MOOD_MATCH_SCORE = 10
        const val WEATHER_MATCH_SCORE = 5
        const val SEASONAL_BONUS = 15
        const val ADJACENT_MONTH_BONUS = 8
        const val DEFAULT_MOOD_SCORE = 5
        const val DEFAULT_WEATHER_SCORE = 2
        const val MONTH_DIFFERENCE_THRESHOLD = 1
    }
    
    /**
     * 기분별 키워드 매핑
     */
    object MoodKeywords {
        val HAPPINESS_KEYWORDS = setOf("행복", "사랑", "기쁨", "즐거움")
        val COMFORT_KEYWORDS = setOf("위로", "희망", "평안", "안정")
        val GRATITUDE_KEYWORDS = setOf("감사", "평화", "조화", "만족")
        val ENERGY_KEYWORDS = setOf("활력", "열정", "용기", "도전")
    }
    
    /**
     * 날씨별 키워드 매핑
     */
    object WeatherKeywords {
        val SUNNY_KEYWORDS = setOf("밝", "빛", "환", "명랑")
        val CALM_KEYWORDS = setOf("우아", "고요", "평온", "차분")
        val FRESH_KEYWORDS = setOf("신선", "청량", "상쾌", "맑")
    }
    
    /**
     * 로깅 태그
     */
    object LogTags {
        const val RECOMMENDATION_USECASE = "RecommendFlowerUseCase"
        const val MOOD_STRATEGY = "MoodBasedStrategy"
        const val WEATHER_STRATEGY = "WeatherBasedStrategy"
        const val SEASONAL_STRATEGY = "SeasonalStrategy"
        const val ORCHESTRATOR = "FlowerOrchestrator"
    }
}