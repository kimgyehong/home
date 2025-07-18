package com.flowerdiary.domain.usecase.flower

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * 오늘의 꽃 추천 테스트
 * SRP: 오늘의 꽃 추천 기능만 검증
 */
class TodayFlowerRecommendationTest {
    
    private val mockRepository = MockBirthFlowerRepository()
    private val useCase = RecommendFlowerUseCase(mockRepository)
    
    @Test
    fun `오늘의 추천 꽃 조회 성공`() = runTest {
        // Given
        val todayFlower = TestDataFactory.happyFlower.copy(
            month = TestDateTimeUtil.getCurrentDate().month,
            day = TestDateTimeUtil.getCurrentDate().day
        )
        mockRepository.findByDateResult = Result.success(todayFlower)
        
        // When
        val result = useCase.recommendTodayFlower()
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(todayFlower, result.getOrNull())
    }
    
    @Test
    fun `오늘의 추천 꽃이 없을 때 null 반환`() = runTest {
        // Given
        mockRepository.findByDateResult = Result.success(null)
        
        // When
        val result = useCase.recommendTodayFlower()
        
        // Then
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }
    
    @Test
    fun `오늘의 추천 꽃 조회 실패 시 오류 전파`() = runTest {
        // Given
        val error = RuntimeException("DB 오류")
        mockRepository.findByDateResult = Result.failure(error)
        
        // When
        val result = useCase.recommendTodayFlower()
        
        // Then
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}
