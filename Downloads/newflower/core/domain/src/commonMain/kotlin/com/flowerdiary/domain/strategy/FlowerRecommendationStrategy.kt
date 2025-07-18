package com.flowerdiary.domain.strategy

import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather

/**
 * 꽃 추천 전략 인터페이스
 * Strategy Pattern 적용으로 다양한 추천 전략을 플러그인 방식으로 확장 가능
 * OCP(개방-폐쇄 원칙) 준수: 새로운 전략 추가 시 기존 코드 수정 없음
 */
interface FlowerRecommendationStrategy {
    
    /**
     * 꽃에 대한 추천 점수 계산
     * @param flower 평가할 꽃
     * @param mood 현재 기분
     * @param weather 현재 날씨
     * @return 추천 점수 (높을수록 더 적합)
     */
    fun calculateScore(flower: BirthFlower, mood: Mood, weather: Weather): Int
    
    /**
     * 전략의 우선순위 (높을수록 먼저 적용)
     */
    val priority: Int
    
    /**
     * 전략 이름 (로깅용)
     */
    val name: String
}

/**
 * 추천 결과 데이터 클래스
 */
data class RecommendationResult(
    val flower: BirthFlower,
    val totalScore: Int,
    val strategyScores: Map<String, Int>
)