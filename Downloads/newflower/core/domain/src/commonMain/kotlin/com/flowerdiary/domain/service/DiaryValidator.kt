package com.flowerdiary.domain.service

import com.flowerdiary.common.constants.DiaryConstants
import com.flowerdiary.common.utils.Logger

/**
 * 일기 검증 서비스
 * SRP: 일기 데이터 유효성 검증만 담당
 * 순수 함수로 구성되어 테스트 가능성 극대화
 */
object DiaryValidator {
    
    /**
     * 제목 유효성 검증
     */
    fun validateTitle(title: String): ValidationResult {
        return when {
            title.length > DiaryConstants.Length.MAX_TITLE_LENGTH -> {
                ValidationResult.Error(DiaryConstants.ValidationMessages.TITLE_TOO_LONG)
            }
            else -> ValidationResult.Success
        }
    }
    
    /**
     * 내용 유효성 검증
     */
    fun validateContent(content: String): ValidationResult {
        return when {
            content.length > DiaryConstants.Length.MAX_CONTENT_LENGTH -> {
                ValidationResult.Error(DiaryConstants.ValidationMessages.CONTENT_TOO_LONG)
            }
            else -> ValidationResult.Success
        }
    }
    
    /**
     * 폰트 크기 유효성 검증
     */
    fun validateFontSize(fontSize: Float): ValidationResult {
        return when {
            fontSize < DiaryConstants.Font.MIN_FONT_SIZE || 
            fontSize > DiaryConstants.Font.MAX_FONT_SIZE -> {
                ValidationResult.Error(DiaryConstants.ValidationMessages.INVALID_FONT_SIZE)
            }
            else -> ValidationResult.Success
        }
    }
    
    /**
     * 타임스탬프 유효성 검증
     */
    fun validateTimestamp(createdAt: Long, updatedAt: Long): ValidationResult {
        return when {
            createdAt <= DiaryConstants.Default.MIN_TIMESTAMP -> {
                ValidationResult.Error(DiaryConstants.ValidationMessages.INVALID_TIMESTAMP)
            }
            updatedAt < createdAt -> {
                ValidationResult.Error(DiaryConstants.ValidationMessages.UPDATED_BEFORE_CREATED)
            }
            else -> ValidationResult.Success
        }
    }
    
    /**
     * 일기 전체 유효성 검증
     */
    fun validateAll(
        title: String,
        content: String,
        fontSize: Float,
        createdAt: Long,
        updatedAt: Long
    ): ValidationResult {
        val validations = listOf(
            validateTitle(title),
            validateContent(content),
            validateFontSize(fontSize),
            validateTimestamp(createdAt, updatedAt)
        )
        
        val errors = validations.filterIsInstance<ValidationResult.Error>()
        return if (errors.isEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(errors.joinToString("; ") { it.message })
        }
    }
}

/**
 * 검증 결과 sealed class
 */
sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
    
    fun isValid(): Boolean = this is Success
    fun getErrorMessage(): String? = (this as? Error)?.message
}