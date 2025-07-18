package com.flowerdiary.domain.model

import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.platform.DateTimeUtil
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * 일기 도메인 모델 테스트
 * SRP: Diary 엔티티의 비즈니스 로직 검증만 담당
 * 도메인 규칙과 제약 조건 테스트
 */
class DiaryTest {
    
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
    fun `일기 생성 시 모든 필드가 올바르게 설정된다`() {
        // Given & When
        val diary = validDiary
        
        // Then
        assertEquals("test-id", diary.id.value)
        assertEquals("테스트 제목", diary.title)
        assertEquals("테스트 내용", diary.content)
        assertEquals(Mood.HAPPY, diary.mood)
        assertEquals(Weather.SUNNY, diary.weather)
        assertEquals(1, diary.flowerId.value)
        assertEquals("default", diary.fontFamily)
        assertEquals(16.0f, diary.fontSize)
        assertEquals(0xFF000000L, diary.fontColor)
        assertEquals("default", diary.backgroundTheme)
    }
    
    @Test
    fun `제목이 최대 길이를 초과하면 검증에 실패한다`() {
        // Given
        val longTitle = "a".repeat(Config.MAX_TITLE_LENGTH + 1)
        
        // When & Then
        assertFails {
            validDiary.copy(title = longTitle).validateTitle()
        }
    }
    
    @Test
    fun `내용이 최대 길이를 초과하면 검증에 실패한다`() {
        // Given
        val longContent = "a".repeat(Config.MAX_CONTENT_LENGTH + 1)
        
        // When & Then
        assertFails {
            validDiary.copy(content = longContent).validateContent()
        }
    }
    
    @Test
    fun `제목이 비어있으면 기본값을 반환한다`() {
        // Given
        val diaryWithoutTitle = validDiary.copy(title = "")
        
        // When
        val result = diaryWithoutTitle.getTitleOrDefault()
        
        // Then
        assertEquals("제목 없음", result)
    }
    
    @Test
    fun `내용 미리보기가 올바르게 생성된다`() {
        // Given
        val longContent = "a".repeat(200)
        val diary = validDiary.copy(content = longContent)
        
        // When
        val preview = diary.getPreview()
        
        // Then
        assertTrue(preview.length <= Config.PREVIEW_LENGTH)
        assertTrue(preview.endsWith("..."))
    }
    
    @Test
    fun `일기 수정 여부를 올바르게 판단한다`() {
        // Given
        val createdTime = DateTimeUtil.now()
        val updatedTime = createdTime + 1000
        
        val editedDiary = validDiary.copy(
            createdAt = createdTime,
            updatedAt = updatedTime
        )
        
        val notEditedDiary = validDiary.copy(
            createdAt = createdTime,
            updatedAt = createdTime
        )
        
        // When & Then
        assertTrue(editedDiary.isEdited())
        assertFalse(notEditedDiary.isEdited())
    }
    
    @Test
    fun `빈 제목과 내용으로 일기를 생성할 수 있다`() {
        // Given & When
        val emptyDiary = validDiary.copy(title = "", content = "")
        
        // Then
        assertEquals("", emptyDiary.title)
        assertEquals("", emptyDiary.content)
        assertTrue(emptyDiary.isEmpty())
    }
    
    @Test
    fun `폰트 크기가 유효 범위 내에 있는지 검증한다`() {
        // Given
        val validFontSize = 16.0f
        val tooSmallFontSize = 8.0f
        val tooBigFontSize = 32.0f
        
        // When & Then
        assertTrue(validDiary.copy(fontSize = validFontSize).isValidFontSize())
        assertFalse(validDiary.copy(fontSize = tooSmallFontSize).isValidFontSize())
        assertFalse(validDiary.copy(fontSize = tooBigFontSize).isValidFontSize())
    }
    
    @Test
    fun `기본 설정 여부를 올바르게 판단한다`() {
        // Given
        val defaultDiary = validDiary.copy(
            fontFamily = "default",
            fontColor = 0xFF000000L,
            backgroundTheme = "default"
        )
        
        val customDiary = validDiary.copy(
            fontFamily = "custom",
            fontColor = 0xFF123456L,
            backgroundTheme = "spring"
        )
        
        // When & Then
        assertTrue(defaultDiary.isDefaultSettings())
        assertFalse(customDiary.isDefaultSettings())
    }
}
