package com.flowerdiary.data.initializer.validator

import com.flowerdiary.common.constants.BirthFlowerConstants
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.data.initializer.BirthFlowerEntry

/**
 * 탄생화 데이터 검증기
 * SRP: 데이터 유효성 검증만 담당
 * Detekt: 메서드 분리로 복잡도 관리
 */
class BirthFlowerValidator {
    
    /**
     * 탄생화 엔트리 유효성 검증
     */
    fun validate(entry: BirthFlowerEntry): Result<BirthFlowerEntry> =
        runCatching {
            validateDateKey(entry.dateKey)
            validateMonth(entry.month)
            validateDay(entry.day, entry.month)
            validateFlowerName(entry.data.flower)
            validateMeaning(entry.data.meaning)
            
            Logger.debug(TAG, "Validated entry for ${entry.dateKey}")
            entry
        }.onFailure { error ->
            Logger.error(TAG, "Validation failed for ${entry.dateKey}", error)
        }
    
    /**
     * 날짜 키 형식 검증
     */
    private fun validateDateKey(dateKey: String) {
        require(dateKey.length == BirthFlowerConstants.DATE_KEY_LENGTH) {
            "${BirthFlowerConstants.ERROR_INVALID_DATE_KEY}: $dateKey"
        }
        require(dateKey[BirthFlowerConstants.MONTH_END_INDEX] == BirthFlowerConstants.DATE_KEY_SEPARATOR[0]) {
            "${BirthFlowerConstants.ERROR_INVALID_DATE_KEY}: $dateKey"
        }
    }
    
    /**
     * 월 값 검증
     */
    private fun validateMonth(month: Int) {
        require(month in BirthFlowerConstants.MIN_MONTH..BirthFlowerConstants.MAX_MONTH) {
            "${BirthFlowerConstants.ERROR_INVALID_MONTH}: $month"
        }
    }
    
    /**
     * 일 값 검증 (월별 최대 일수 체크)
     */
    private fun validateDay(day: Int, month: Int) {
        val maxDay = BirthFlowerConstants.DAYS_IN_MONTH[month] 
            ?: BirthFlowerConstants.MAX_DAY
        
        require(day in BirthFlowerConstants.MIN_DAY..maxDay) {
            "${BirthFlowerConstants.ERROR_INVALID_DAY_FOR_MONTH}: $day for month $month"
        }
    }
    
    /**
     * 꽃 이름 검증
     */
    private fun validateFlowerName(flowerName: String) {
        require(flowerName.length >= BirthFlowerConstants.MIN_FLOWER_NAME_LENGTH) {
            BirthFlowerConstants.ERROR_EMPTY_FLOWER_NAME
        }
    }
    
    /**
     * 꽃말 검증
     */
    private fun validateMeaning(meaning: String) {
        require(meaning.length >= BirthFlowerConstants.MIN_MEANING_LENGTH) {
            BirthFlowerConstants.ERROR_EMPTY_MEANING
        }
    }
    
    companion object {
        private const val TAG = "BirthFlowerValidator"
    }
}
