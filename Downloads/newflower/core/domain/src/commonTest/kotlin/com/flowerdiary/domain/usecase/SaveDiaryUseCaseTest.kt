package com.flowerdiary.domain.usecase

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.domain.model.*
import com.flowerdiary.domain.repository.DiaryRepository
import com.flowerdiary.domain.usecase.diary.SaveDiaryUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.*

/**
 * 일기 저장 유스케이스 테스트
 * SRP: SaveDiaryUseCase의 비즈니스 로직 검증만 담당
 * 모킹을 통한 단위 테스트
 */
class SaveDiaryUseCaseTest {
    
    private val mockRepository = MockDiaryRepository()
    private val useCase = SaveDiaryUseCase(mockRepository)
    
    private val validDiary = Diary(
        id = DiaryId("test-id"),
        title = "테스트 제목",
        content = "테스트 내용",
        mood = Mood.HAPPY,
        weather = Weather.SUNNY,
        flowerId = FlowerId(1),
        createdAt = DateTimeUtil.now(),
        updatedAt = DateTimeUtil.now(),
        fontFamily = "default",
        fontSize = 16.0f,
        fontColor = 0xFF000000L,
        backgroundTheme = "default"
    )
    
    @Test
    fun `유효한 일기를 저장하면 성공한다`() = runTest {
        // Given
        mockRepository.saveResult = Result.success(Unit)
        
        // When
        val result = useCase(validDiary)
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(validDiary, mockRepository.savedDiary)
    }
    
    @Test
    fun `제목이 너무 길면 저장에 실패한다`() = runTest {
        // Given
        val longTitle = "a".repeat(1000)
        val invalidDiary = validDiary.copy(title = longTitle)
        
        // When
        val result = useCase(invalidDiary)
        
        // Then
        assertTrue(result.isFailure)
        assertNull(mockRepository.savedDiary)
    }
    
    @Test
    fun `내용이 너무 길면 저장에 실패한다`() = runTest {
        // Given
        val longContent = "a".repeat(10000)
        val invalidDiary = validDiary.copy(content = longContent)
        
        // When
        val result = useCase(invalidDiary)
        
        // Then
        assertTrue(result.isFailure)
        assertNull(mockRepository.savedDiary)
    }
    
    @Test
    fun `빈 제목과 내용으로도 저장할 수 있다`() = runTest {
        // Given
        val emptyDiary = validDiary.copy(title = "", content = "")
        mockRepository.saveResult = Result.success(Unit)
        
        // When
        val result = useCase(emptyDiary)
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyDiary, mockRepository.savedDiary)
    }
    
    @Test
    fun `레포지토리에서 오류가 발생하면 실패한다`() = runTest {
        // Given
        val error = RuntimeException("DB 오류")
        mockRepository.saveResult = Result.failure(error)
        
        // When
        val result = useCase(validDiary)
        
        // Then
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
    }
    
    @Test
    fun `저장 시 업데이트 시간이 자동으로 설정된다`() = runTest {
        // Given
        val originalTime = DateTimeUtil.now()
        val diary = validDiary.copy(updatedAt = originalTime)
        mockRepository.saveResult = Result.success(Unit)
        
        // When
        useCase(diary)
        
        // Then
        val savedDiary = mockRepository.savedDiary
        assertNotNull(savedDiary)
        assertTrue(savedDiary.updatedAt >= originalTime)
    }
    
    @Test
    fun `폰트 크기가 유효 범위를 벗어나면 저장에 실패한다`() = runTest {
        // Given
        val invalidFontSize = 100.0f
        val invalidDiary = validDiary.copy(fontSize = invalidFontSize)
        
        // When
        val result = useCase(invalidDiary)
        
        // Then
        assertTrue(result.isFailure)
        assertNull(mockRepository.savedDiary)
    }
}

/**
 * 테스트용 Mock Repository
 */
class MockDiaryRepository : DiaryRepository {
    var saveResult: Result<Unit> = Result.success(Unit)
    var savedDiary: Diary? = null
    
    private val diaries = mutableMapOf<String, Diary>()
    
    override suspend fun save(diary: Diary): Result<Unit> {
        savedDiary = diary
        return saveResult.onSuccess {
            diaries[diary.id.value] = diary
        }
    }
    
    override suspend fun findById(id: DiaryId): Result<Diary?> {
        return Result.success(diaries[id.value])
    }
    
    override suspend fun findAll(): Result<List<Diary>> {
        return Result.success(diaries.values.toList())
    }
    
    override suspend fun findByYearMonth(year: Int, month: Int): Result<List<Diary>> {
        return Result.success(emptyList())
    }
    
    override suspend fun findByDateRange(startTime: Long, endTime: Long): Result<List<Diary>> {
        return Result.success(emptyList())
    }
    
    override suspend fun search(query: String): Result<List<Diary>> {
        return Result.success(emptyList())
    }
    
    override suspend fun delete(id: DiaryId): Result<Unit> {
        diaries.remove(id.value)
        return Result.success(Unit)
    }
    
    override suspend fun deleteAll(): Result<Unit> {
        diaries.clear()
        return Result.success(Unit)
    }
}
