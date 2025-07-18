package com.flowerdiary.domain.service

import com.flowerdiary.common.constants.ColorPalette
import com.flowerdiary.domain.model.BirthFlower

/**
 * 탄생화 상태 확인 서비스
 * SRP: 탄생화의 다양한 상태 확인만 담당
 * 순수 함수로 구성되어 테스트하기 쉬움
 */
object BirthFlowerStateChecker {
    
    /**
     * 해당 날짜가 오늘인지 확인
     */
    fun isToday(birthFlower: BirthFlower, currentMonth: Int, currentDay: Int): Boolean {
        return birthFlower.month == currentMonth && birthFlower.day == currentDay
    }
    
    /**
     * 탄생화가 해금되었는지 확인
     */
    fun isUnlocked(birthFlower: BirthFlower): Boolean {
        return birthFlower.isUnlocked
    }
    
    /**
     * 탄생화가 잠겨있는지 확인
     */
    fun isLocked(birthFlower: BirthFlower): Boolean {
        return !birthFlower.isUnlocked
    }
    
    /**
     * 배경색이 유효한지 확인 (ARGB 형식)
     */
    fun isValidBackgroundColor(birthFlower: BirthFlower): Boolean {
        return birthFlower.backgroundColor and ColorPalette.Text.Black != 0L
    }
    
    /**
     * 같은 날짜인지 확인
     */
    fun isSameDate(birthFlower: BirthFlower, other: BirthFlower): Boolean {
        return birthFlower.month == other.month && birthFlower.day == other.day
    }
    
    /**
     * 같은 월인지 확인
     */
    fun isSameMonth(birthFlower: BirthFlower, other: BirthFlower): Boolean {
        return birthFlower.month == other.month
    }
    
    /**
     * 봄 탄생화인지 확인 (3-5월)
     */
    fun isSpringFlower(birthFlower: BirthFlower): Boolean {
        return birthFlower.month in 3..5
    }
    
    /**
     * 여름 탄생화인지 확인 (6-8월)
     */
    fun isSummerFlower(birthFlower: BirthFlower): Boolean {
        return birthFlower.month in 6..8
    }
    
    /**
     * 가을 탄생화인지 확인 (9-11월)
     */
    fun isAutumnFlower(birthFlower: BirthFlower): Boolean {
        return birthFlower.month in 9..11
    }
    
    /**
     * 겨울 탄생화인지 확인 (12, 1-2월)
     */
    fun isWinterFlower(birthFlower: BirthFlower): Boolean {
        return birthFlower.month == 12 || birthFlower.month in 1..2
    }
    
    /**
     * 특별한 날짜인지 확인 (1일, 15일, 말일)
     */
    fun isSpecialDate(birthFlower: BirthFlower): Boolean {
        return birthFlower.day == 1 || birthFlower.day == 15 || birthFlower.day >= 28
    }
}