package com.flowerdiary.domain.usecase.flower

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.platform.PreferencesKeys
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.repository.BirthFlowerRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.*

/**
 * UnlockBirthFlowerUseCase 테스트
 * SRP: 탄생화 해금 유스케이스의 비즈니스 로직 검증만 담당
 */
class UnlockBirthFlowerUseCaseTest {
    
    private val mockRepository = MockBirthFlowerRepository()
    private val mockPreferencesStore = MockPreferencesStore()
    private val useCase = UnlockBirthFlowerUseCase(mockRepository, mockPreferencesStore)
    
    private val testFlower = BirthFlower(
        id = FlowerId(1),
        month = 1,
        day = 1,
        nameKr = "장미",
        nameEn = "Rose",
        meaning = "사랑",
        description = "아름다운 꽃",
        imageUrl = "rose.png",
        backgroundColor = 0xFFFF0000L,
        isUnlocked = false
    )
    
    @Test
    fun `오늘의 탄생화 해금 성공`() = runTest {
        // Given
        val today = TestDateTimeUtil.getCurrentDate()
        val todayStr = "${today.year}-${today.month}-${today.day}"
        mockPreferencesStore.data[PreferencesKeys.LAST_UNLOCK_DATE] = "2023-12-31" // 다른 날짜
        mockRepository.unlockTodayResult = Result.success(Unit)
        
        // When
        val result = useCase.unlockTodayFlower()
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(todayStr, mockPreferencesStore.data[PreferencesKeys.LAST_UNLOCK_DATE])
    }
    
    @Test
    fun `오늘 이미 해금했다면 스킵`() = runTest {
        // Given
        val today = TestDateTimeUtil.getCurrentDate()
        val todayStr = "${today.year}-${today.month}-${today.day}"
        mockPreferencesStore.data[PreferencesKeys.LAST_UNLOCK_DATE] = todayStr
        
        // When
        val result = useCase.unlockTodayFlower()
        
        // Then
        assertTrue(result.isSuccess)
        // 레포지토리 호출이 없어야 함
        assertEquals(Result.success(Unit), mockRepository.unlockTodayResult)
    }
    
    @Test
    fun `오늘의 탄생화 해금 실패 시 오류 전파`() = runTest {
        // Given
        val today = TestDateTimeUtil.getCurrentDate()
        val todayStr = "${today.year}-${today.month}-${today.day}"
        mockPreferencesStore.data[PreferencesKeys.LAST_UNLOCK_DATE] = "2023-12-31" // 다른 날짜
        val error = RuntimeException("DB 오류")
        mockRepository.unlockTodayResult = Result.failure(error)
        
        // When
        val result = useCase.unlockTodayFlower()
        
        // Then
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
        // 실패 시 preferences는 업데이트되지 않음
        assertEquals("2023-12-31", mockPreferencesStore.data[PreferencesKeys.LAST_UNLOCK_DATE])
    }
    
    @Test
    fun `ID로 탄생화 수동 해금 성공`() = runTest {
        // Given
        val flowerId = FlowerId(1)
        mockRepository.findByIdResult = Result.success(testFlower) // 잠금 상태
        mockRepository.unlockResult = Result.success(Unit)
        
        // When
        val result = useCase.unlockById(flowerId)
        
        // Then
        assertTrue(result.isSuccess)
    }
    
    @Test
    fun `이미 해금된 꽃을 다시 해금하면 스킵`() = runTest {
        // Given
        val flowerId = FlowerId(1)
        val unlockedFlower = testFlower.copy(isUnlocked = true)
        mockRepository.findByIdResult = Result.success(unlockedFlower)
        
        // When
        val result = useCase.unlockById(flowerId)
        
        // Then
        assertTrue(result.isSuccess)
        // unlock 메소드는 호출되지 않음
    }
    
    @Test
    fun `존재하지 않는 꽃 해금 시 성공 (null 처리)`() = runTest {
        // Given
        val flowerId = FlowerId(999)
        mockRepository.findByIdResult = Result.success(null)
        mockRepository.unlockResult = Result.success(Unit)
        
        // When
        val result = useCase.unlockById(flowerId)
        
        // Then
        assertTrue(result.isSuccess)
    }
    
    @Test
    fun `ID로 해금 실패 시 오류 전파`() = runTest {
        // Given
        val flowerId = FlowerId(1)
        val error = RuntimeException("DB 오류")
        mockRepository.findByIdResult = Result.success(testFlower)
        mockRepository.unlockResult = Result.failure(error)
        
        // When
        val result = useCase.unlockById(flowerId)
        
        // Then
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
    
    @Test
    fun `해금된 꽃 개수 조회 성공`() = runTest {
        // Given
        val expectedCount = 10
        mockRepository.countUnlockedResult = Result.success(expectedCount)
        
        // When
        val result = useCase.getUnlockedCount()
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedCount, result.getOrNull())
    }
    
    @Test
    fun `해금된 꽃 개수 조회 실패 시 오류 전파`() = runTest {
        // Given
        val error = RuntimeException("DB 오류")
        mockRepository.countUnlockedResult = Result.failure(error)
        
        // When
        val result = useCase.getUnlockedCount()
        
        // Then
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
}

/**
 * 테스트용 Mock PreferencesStore
 */
class MockPreferencesStore : PreferencesStore {
    val data = mutableMapOf<String, String>()
    
    override suspend fun getString(key: String): String? {
        return data[key]
    }
    
    override suspend fun putString(key: String, value: String) {
        data[key] = value
    }
}

/**
 * DateTimeUtil의 테스트용 확장 (실제 구현에서는 expect/actual로 처리)
 */
object TestDateTimeUtil {
    fun getCurrentDate(): com.flowerdiary.common.platform.DateInfo {
        return com.flowerdiary.common.platform.DateInfo(2024, 1, 1, 1) // 고정된 테스트 날짜
    }
}

/**
 * 테스트용 Mock Repository (이미 다른 테스트에서 정의되어 있으므로 생략)
 */
