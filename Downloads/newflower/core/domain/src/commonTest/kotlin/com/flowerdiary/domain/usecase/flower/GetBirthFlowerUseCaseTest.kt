package com.flowerdiary.domain.usecase.flower

import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.repository.BirthFlowerRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.*

/**
 * GetBirthFlowerUseCase 테스트
 * SRP: 탄생화 조회 유스케이스의 비즈니스 로직 검증만 담당
 */
class GetBirthFlowerUseCaseTest {
    
    private val mockRepository = MockBirthFlowerRepository()
    private val useCase = GetBirthFlowerUseCase(mockRepository)
    
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
        isUnlocked = true
    )
    
    @Test
    fun `ID로 탄생화 조회 성공`() = runTest {
        // Given
        mockRepository.findByIdResult = Result.success(testFlower)
        
        // When
        val result = useCase.getById(FlowerId(1))
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(testFlower, result.getOrNull())
    }
    
    @Test
    fun `존재하지 않는 ID로 조회 시 null 반환`() = runTest {
        // Given
        mockRepository.findByIdResult = Result.success(null)
        
        // When
        val result = useCase.getById(FlowerId(999))
        
        // Then
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
    }
    
    @Test
    fun `ID로 조회 시 레포지토리 오류 전파`() = runTest {
        // Given
        val error = RuntimeException("DB 오류")
        mockRepository.findByIdResult = Result.failure(error)
        
        // When
        val result = useCase.getById(FlowerId(1))
        
        // Then
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
    
    @Test
    fun `날짜로 탄생화 조회 성공`() = runTest {
        // Given
        mockRepository.findByDateResult = Result.success(testFlower)
        
        // When
        val result = useCase.getByDate(1, 1)
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(testFlower, result.getOrNull())
    }
    
    @Test
    fun `유효하지 않은 월로 조회 시 실패`() = runTest {
        // Given & When & Then
        assertFailsWith<IllegalArgumentException> {
            useCase.getByDate(0, 1)
        }
        assertFailsWith<IllegalArgumentException> {
            useCase.getByDate(13, 1)
        }
    }
    
    @Test
    fun `유효하지 않은 일로 조회 시 실패`() = runTest {
        // Given & When & Then
        assertFailsWith<IllegalArgumentException> {
            useCase.getByDate(1, 0)
        }
        assertFailsWith<IllegalArgumentException> {
            useCase.getByDate(1, 32)
        }
    }
    
    @Test
    fun `모든 탄생화 조회 성공`() = runTest {
        // Given
        val flowers = listOf(testFlower)
        mockRepository.findAllResult = Result.success(flowers)
        
        // When
        val result = useCase.getAll()
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(flowers, result.getOrNull())
    }
    
    @Test
    fun `해금된 탄생화만 조회 성공`() = runTest {
        // Given
        val unlockedFlowers = listOf(testFlower)
        mockRepository.findUnlockedResult = Result.success(unlockedFlowers)
        
        // When
        val result = useCase.getUnlocked()
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(unlockedFlowers, result.getOrNull())
    }
    
    @Test
    fun `해금되지 않은 꽃은 해금된 목록에 포함되지 않음`() = runTest {
        // Given
        val lockedFlower = testFlower.copy(isUnlocked = false)
        mockRepository.findUnlockedResult = Result.success(emptyList())
        
        // When
        val result = useCase.getUnlocked()
        
        // Then
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }
}

/**
 * 테스트용 Mock Repository
 */
class MockBirthFlowerRepository : BirthFlowerRepository {
    var findByIdResult: Result<BirthFlower?> = Result.success(null)
    var findByDateResult: Result<BirthFlower?> = Result.success(null)
    var findAllResult: Result<List<BirthFlower>> = Result.success(emptyList())
    var findUnlockedResult: Result<List<BirthFlower>> = Result.success(emptyList())
    var unlockTodayResult: Result<Unit> = Result.success(Unit)
    var unlockResult: Result<Unit> = Result.success(Unit)
    var countUnlockedResult: Result<Int> = Result.success(0)
    
    override suspend fun findById(id: FlowerId): Result<BirthFlower?> {
        return findByIdResult
    }
    
    override suspend fun findByDate(month: Int, day: Int): Result<BirthFlower?> {
        return findByDateResult
    }
    
    override suspend fun findAll(): Result<List<BirthFlower>> {
        return findAllResult
    }
    
    override suspend fun findUnlocked(): Result<List<BirthFlower>> {
        return findUnlockedResult
    }
    
    override suspend fun unlockToday(month: Int, day: Int): Result<Unit> {
        return unlockTodayResult
    }
    
    override suspend fun unlock(id: FlowerId): Result<Unit> {
        return unlockResult
    }
    
    override suspend fun countUnlocked(): Result<Int> {
        return countUnlockedResult
    }
}
