package com.flowerdiary.domain.model

import kotlin.test.*

/**
 * 탄생화 도메인 모델 테스트
 * SRP: BirthFlower 엔티티의 비즈니스 로직 검증만 담당
 * 도메인 규칙과 제약 조건 테스트
 */
class BirthFlowerTest {
    
    private val validBirthFlower = BirthFlower(
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
    fun `탄생화 생성 시 모든 필드가 올바르게 설정된다`() {
        // Given & When
        val flower = validBirthFlower
        
        // Then
        assertEquals(1, flower.id.value)
        assertEquals(1, flower.month)
        assertEquals(1, flower.day)
        assertEquals("장미", flower.nameKr)
        assertEquals("Rose", flower.nameEn)
        assertEquals("사랑", flower.meaning)
        assertEquals("아름다운 꽃", flower.description)
        assertEquals("rose.png", flower.imageUrl)
        assertEquals(0xFFFF0000L, flower.backgroundColor)
        assertFalse(flower.isUnlocked)
    }
    
    @Test
    fun `월이 유효 범위(1-12)를 벗어나면 검증에 실패한다`() {
        // Given & When & Then
        assertFails {
            validBirthFlower.copy(month = 0).validateMonth()
        }
        assertFails {
            validBirthFlower.copy(month = 13).validateMonth()
        }
    }
    
    @Test
    fun `일이 유효 범위(1-31)를 벗어나면 검증에 실패한다`() {
        // Given & When & Then
        assertFails {
            validBirthFlower.copy(day = 0).validateDay()
        }
        assertFails {
            validBirthFlower.copy(day = 32).validateDay()
        }
    }
    
    @Test
    fun `한국어 이름이 비어있으면 검증에 실패한다`() {
        // Given & When & Then
        assertFails {
            validBirthFlower.copy(nameKr = "").validateName()
        }
        assertFails {
            validBirthFlower.copy(nameKr = "   ").validateName()
        }
    }
    
    @Test
    fun `영어 이름이 비어있으면 검증에 실패한다`() {
        // Given & When & Then
        assertFails {
            validBirthFlower.copy(nameEn = "").validateName()
        }
        assertFails {
            validBirthFlower.copy(nameEn = "   ").validateName()
        }
    }
    
    @Test
    fun `꽃말이 비어있으면 검증에 실패한다`() {
        // Given & When & Then
        assertFails {
            validBirthFlower.copy(meaning = "").validateMeaning()
        }
        assertFails {
            validBirthFlower.copy(meaning = "   ").validateMeaning()
        }
    }
    
    @Test
    fun `날짜 표시 형식이 올바르게 생성된다`() {
        // Given
        val flower = validBirthFlower.copy(month = 3, day = 15)
        
        // When
        val dateDisplay = flower.getDateDisplay()
        
        // Then
        assertEquals("3월 15일", dateDisplay)
    }
    
    @Test
    fun `월 표시 형식이 올바르게 생성된다`() {
        // Given
        val flower = validBirthFlower.copy(month = 7)
        
        // When
        val monthDisplay = flower.getMonthDisplay()
        
        // Then
        assertEquals("7월", monthDisplay)
    }
    
    @Test
    fun `탄생화 해금 상태를 변경할 수 있다`() {
        // Given
        val lockedFlower = validBirthFlower.copy(isUnlocked = false)
        
        // When
        val unlockedFlower = lockedFlower.unlock()
        
        // Then
        assertFalse(lockedFlower.isUnlocked)
        assertTrue(unlockedFlower.isUnlocked)
    }
    
    @Test
    fun `이미 해금된 꽃을 다시 해금해도 상태가 유지된다`() {
        // Given
        val unlockedFlower = validBirthFlower.copy(isUnlocked = true)
        
        // When
        val result = unlockedFlower.unlock()
        
        // Then
        assertTrue(result.isUnlocked)
    }
    
    @Test
    fun `배경색이 유효한 ARGB 값인지 검증한다`() {
        // Given
        val validColor = 0xFF123456L
        val invalidColor = 0x123456L // Alpha 채널 없음
        
        // When & Then
        assertTrue(validBirthFlower.copy(backgroundColor = validColor).isValidBackgroundColor())
        assertFalse(validBirthFlower.copy(backgroundColor = invalidColor).isValidBackgroundColor())
    }
    
    @Test
    fun `같은 날짜의 탄생화끼리 비교할 수 있다`() {
        // Given
        val flower1 = validBirthFlower.copy(month = 5, day = 10)
        val flower2 = validBirthFlower.copy(month = 5, day = 10, nameKr = "다른꽃")
        val flower3 = validBirthFlower.copy(month = 5, day = 11)
        
        // When & Then
        assertTrue(flower1.isSameDate(flower2))
        assertFalse(flower1.isSameDate(flower3))
    }
}
