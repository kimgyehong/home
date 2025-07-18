package com.flowerdiary.domain.specification

import com.flowerdiary.domain.model.Diary

/**
 * 모든 일기 조회
 */
data object AllDiaries : DiaryQuerySpecification {
    override fun toSqlWhereClause(): String = "1=1"
    
    override fun toParameters(): Map<String, Any> = emptyMap()
    
    override fun isSatisfiedBy(diary: Diary): Boolean = true
}

/**
 * 기분으로 조회
 */
data class MoodDiaries(
    val mood: String
) : DiaryQuerySpecification {
    
    override fun toSqlWhereClause(): String = "mood = :mood"
    
    override fun toParameters(): Map<String, Any> = mapOf("mood" to mood)
    
    override fun isSatisfiedBy(diary: Diary): Boolean = diary.mood.name == mood
}

/**
 * 날씨로 조회
 */
data class WeatherDiaries(
    val weather: String
) : DiaryQuerySpecification {
    
    override fun toSqlWhereClause(): String = "weather = :weather"
    
    override fun toParameters(): Map<String, Any> = mapOf("weather" to weather)
    
    override fun isSatisfiedBy(diary: Diary): Boolean = diary.weather.name == weather
}