package com.flowerdiary.domain.service

import com.flowerdiary.common.constants.BirthFlowerConstants
import com.flowerdiary.common.constants.Config
import com.flowerdiary.domain.model.BirthFlower

/**
 * 탄생화 검증 서비스
 * SRP: 탄생화 데이터 유효성 검증만 담당
 */
object BirthFlowerValidator {
    
    fun validateMonth(month: Int): ValidationResult =
        if (month in BirthFlowerConstants.MIN_MONTH..BirthFlowerConstants.MAX_MONTH) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(BirthFlowerConstants.ERROR_INVALID_MONTH)
        }
    
    fun validateDay(day: Int): ValidationResult =
        if (day in BirthFlowerConstants.MIN_DAY..BirthFlowerConstants.MAX_DAY) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(BirthFlowerConstants.ERROR_INVALID_DAY)
        }
    
    fun validateDate(month: Int, day: Int): ValidationResult {
        validateMonth(month).let { if (!it.isValid()) return it }
        validateDay(day).let { if (!it.isValid()) return it }
        
        val maxDayInMonth = BirthFlowerConstants.DAYS_IN_MONTH[month] ?: 31
        return if (day <= maxDayInMonth) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(BirthFlowerConstants.ERROR_INVALID_DAY_FOR_MONTH)
        }
    }
    
    fun validateName(nameKr: String, nameEn: String): ValidationResult = when {
        nameKr.isBlank() -> ValidationResult.Error(BirthFlowerConstants.ERROR_EMPTY_FLOWER_NAME)
        nameEn.isBlank() -> ValidationResult.Error(BirthFlowerConstants.ERROR_EMPTY_FLOWER_NAME)
        nameKr.length > Config.MAX_FLOWER_NAME_LENGTH -> ValidationResult.Error("Korean name too long")
        nameEn.length > Config.MAX_FLOWER_NAME_LENGTH -> ValidationResult.Error("English name too long")
        else -> ValidationResult.Success
    }
    
    fun validateMeaning(meaning: String): ValidationResult = when {
        meaning.isBlank() -> ValidationResult.Error(BirthFlowerConstants.ERROR_EMPTY_MEANING)
        meaning.length > Config.MAX_FLOWER_MEANING_LENGTH -> ValidationResult.Error("Meaning too long")
        else -> ValidationResult.Success
    }
    
    fun validateAll(birthFlower: BirthFlower): ValidationResult {
        val validations = listOf(
            validateDate(birthFlower.month, birthFlower.day),
            validateName(birthFlower.nameKr, birthFlower.nameEn),
            validateMeaning(birthFlower.meaning)
        )
        
        val errors = validations.filterIsInstance<ValidationResult.Error>()
        return if (errors.isEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(errors.joinToString("; ") { it.message })
        }
    }
}