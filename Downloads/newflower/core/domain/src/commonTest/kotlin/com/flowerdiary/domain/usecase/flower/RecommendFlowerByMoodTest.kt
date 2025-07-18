package com.flowerdiary.domain.usecase.flower

import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * 기분/날씨 기반 꽃 추천 테스트
 * SRP: 기분과 날씨에 따른 꽃 추천 로직만 검증
 */
class RecommendFlowerByMoodTest {
    
    private val mockRepository = MockBirthFlowerRepository()
    private val useCase = RecommendFlowerUseCase(mockRepository)
    
    @Test
    fun `기쁜 기분에 맞는 꽃 추천`() = runTest {
        // Given
        val flowers = listOf(
            TestDataFactory.happyFlower,
            TestDataFactory.sadFlower,
            TestDataFactory.peacefulFlower
        )
        mockRepository.findUnlockedResult = Result.success(flowers)
        
        // When
        val result = useCase.recommendByMoodAndWeather(Mood.HAPPY, Weather.SUNNY)
        
        // Then
        assertTrue(result.isSuccess)
        val recommendedFlower = result.getOrNull()
        assertNotNull(recommendedFlower)
        assertEquals(TestDataFactory.happyFlower.id, recommendedFlower.id)
    }
    
    @Test
    fun `슬픈 기분에 맞는 꽃 추천`() = runTest {
        // Given
        val flowers = listOf(
            TestDataFactory.happyFlower,
            TestDataFactory.sadFlower,
            TestDataFactory.peacefulFlower
        )
        mockRepository.findUnlockedResult = Result.success(flowers)
        
        // When
        val result = useCase.recommendByMoodAndWeather(Mood.SAD, Weather.RAINY)
        
        // Then
        assertTrue(result.isSuccess)
        val recommendedFlower = result.getOrNull()
        assertNotNull(recommendedFlower)
        assertEquals(TestDataFactory.sadFlower.id, recommendedFlower.id)
    }
    
    @Test
    fun `평화로운 기분에 맞는 꽃 추천`() = runTest {
        // Given
        val flowers = listOf(
            TestDataFactory.happyFlower,
            TestDataFactory.sadFlower,
            TestDataFactory.peacefulFlower
        )
        mockRepository.findUnlockedResult = Result.success(flowers)
        
        // When
        val result = useCase.recommendByMoodAndWeather(Mood.PEACEFUL, Weather.CLOUDY)
        
        // Then
        assertTrue(result.isSuccess)
        val recommendedFlower = result.getOrNull()
        assertNotNull(recommendedFlower)
        assertEquals(TestDataFactory.peacefulFlower.id, recommendedFlower.id)
    }
    
    @Test
    fun `맑은 날씨에 맞는 꽃 추천`() = runTest {
        // Given
        val flowers = listOf(
            TestDataFactory.happyFlower,
            TestDataFactory.brightFlower
        )
        mockRepository.findUnlockedResult = Result.success(flowers)
        
        // When
        val result = useCase.recommendByMoodAndWeather(Mood.NORMAL, Weather.SUNNY)
        
        // Then
        assertTrue(result.isSuccess)
        val recommendedFlower = result.getOrNull()
        assertNotNull(recommendedFlower)
        assertEquals(TestDataFactory.brightFlower.id, recommendedFlower.id)
    }
}
