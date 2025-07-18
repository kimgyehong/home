package com.flowerdiary.domain.usecase.flower

import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * 꽃 추천 로직 테스트
 * SRP: 추천 알고리즘의 세부 로직만 검증
 */
class FlowerRecommendationLogicTest {
    
    private val mockRepository = MockBirthFlowerRepository()
    private val useCase = RecommendFlowerUseCase(mockRepository)
    
    @Test
    fun `해금된 꽃이 없을 때 null 반환`() = runTest {
        // Given
        mockRepository.findUnlockedResult = Result.success(emptyList())
        
        // When
        val result = useCase.recommendByMoodAndWeather(Mood.HAPPY, Weather.SUNNY)
        
        // Then
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }
    
    @Test
    fun `레포지토리 오류 시 null 반환`() = runTest {
        // Given
        val error = RuntimeException("DB 오류")
        mockRepository.findUnlockedResult = Result.failure(error)
        
        // When
        val result = useCase.recommendByMoodAndWeather(Mood.HAPPY, Weather.SUNNY)
        
        // Then
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }
    
    @Test
    fun `같은 점수일 때 일관된 결과 반환`() = runTest {
        // Given
        val flower1 = TestDataFactory.happyFlower.copy(
            id = FlowerId(1),
            meaning = "일반"
        )
        val flower2 = TestDataFactory.sadFlower.copy(
            id = FlowerId(2),
            meaning = "일반"
        )
        val flowers = listOf(flower1, flower2)
        mockRepository.findUnlockedResult = Result.success(flowers)
        
        // When
        val result1 = useCase.recommendByMoodAndWeather(Mood.NORMAL, Weather.SUNNY)
        val result2 = useCase.recommendByMoodAndWeather(Mood.NORMAL, Weather.SUNNY)
        
        // Then
        assertTrue(result1.isSuccess)
        assertTrue(result2.isSuccess)
        assertEquals(result1.getOrNull()?.id, result2.getOrNull()?.id)
    }
    
    @Test
    fun `현재 월의 꽃이 우선적으로 추천됨`() = runTest {
        // Given
        val currentMonth = TestDateTimeUtil.getCurrentDate().month
        val nextMonth = if (currentMonth == 12) 1 else currentMonth + 1
        
        val currentMonthFlower = TestDataFactory.happyFlower.copy(
            month = currentMonth,
            meaning = "일반"
        )
        val otherMonthFlower = TestDataFactory.sadFlower.copy(
            month = nextMonth,
            meaning = "행복"
        )
        val flowers = listOf(currentMonthFlower, otherMonthFlower)
        mockRepository.findUnlockedResult = Result.success(flowers)
        
        // When
        val result = useCase.recommendByMoodAndWeather(Mood.NORMAL, Weather.SUNNY)
        
        // Then
        assertTrue(result.isSuccess)
        val recommendedFlower = result.getOrNull()
        assertNotNull(recommendedFlower)
        assertEquals(currentMonthFlower.id, recommendedFlower.id)
    }
}
