package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.constants.Messages
import com.flowerdiary.feature.diary.state.DiaryEditorState

/**
 * 일기 유효성 검사 관리자
 * SRP: 일기 입력값 유효성 검사만 담당
 */
class DiaryValidationManager {
    
    /**
     * 제목 유효성 검사
     */
    fun validateTitle(title: String): ValidationResult {
        return when {
            title.isBlank() -> ValidationResult.Error(Messages.ERROR_TITLE_EMPTY)
            title.length > Config.MAX_TITLE_LENGTH -> 
                ValidationResult.Error(Messages.validationTitleLength(Config.MAX_TITLE_LENGTH))
            else -> ValidationResult.Success
        }
    }
    
    /**
     * 내용 유효성 검사
     */
    fun validateContent(content: String): ValidationResult {
        return when {
            content.isBlank() -> ValidationResult.Error(Messages.ERROR_CONTENT_EMPTY)
            content.length > Config.MAX_CONTENT_LENGTH -> 
                ValidationResult.Error(Messages.validationContentLength(Config.MAX_CONTENT_LENGTH))
            else -> ValidationResult.Success
        }
    }
    
    /**
     * 일기 전체 유효성 검사
     */
    fun validateDiary(state: DiaryEditorState): ValidationResult {
        // 제목 검사
        val titleResult = validateTitle(state.title)
        if (titleResult is ValidationResult.Error) {
            return titleResult
        }
        
        // 내용 검사
        val contentResult = validateContent(state.content)
        if (contentResult is ValidationResult.Error) {
            return contentResult
        }
        
        // 꽃 선택 검사
        if (state.selectedFlower == null) {
            return ValidationResult.Error(Messages.ERROR_FLOWER_NOT_SELECTED)
        }
        
        return ValidationResult.Success
    }
    
    /**
     * 저장 가능 여부 확인
     */
    fun canSave(state: DiaryEditorState): Boolean {
        return state.title.isNotBlank() && 
               state.content.isNotBlank() && 
               state.selectedFlower != null
    }
}

/**
 * 유효성 검사 결과
 */
sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}