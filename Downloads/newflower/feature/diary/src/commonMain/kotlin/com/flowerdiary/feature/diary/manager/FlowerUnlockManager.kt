package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.usecase.flower.UnlockBirthFlowerUseCase

/**
 * 꽃 해금 관리 매니저
 * SRP: 꽃 해금 로직만 담당
 */
class FlowerUnlockManager(
    private val unlockBirthFlowerUseCase: UnlockBirthFlowerUseCase
) {
    
    /**
     * 오늘의 꽃 해금
     */
    suspend fun unlockTodayFlower(): Result<Unit> {
        return unlockBirthFlowerUseCase.unlockTodayFlower()
            .onSuccess {
                Logger.info(TAG, "Today's flower unlocked successfully")
            }
            .onFailure { error ->
                Logger.error(TAG, "Failed to unlock today's flower", error)
            }
    }
    
    /**
     * 특정 꽃 해금 가능 여부 확인
     */
    fun canUnlockFlower(flower: BirthFlower): Boolean {
        val today = DateTimeUtil.getCurrentDate()
        val isToday = flower.month == today.month && flower.day == today.day
        return isToday && !flower.isUnlocked
    }
    
    /**
     * 해금 완료 확인 (모든 꽃 수집 완료)
     */
    fun isCollectionComplete(unlockedCount: Int, totalCount: Int): Boolean {
        return unlockedCount >= totalCount
    }
    
    /**
     * 해금 가능한 날짜 확인
     */
    fun isUnlockableDate(month: Int, day: Int): Boolean {
        val today = DateTimeUtil.getCurrentDate()
        val targetDate = DateTimeUtil.createDate(today.year, month, day)
        return !targetDate.isAfter(today)
    }
    
    companion object {
        private const val TAG = "FlowerUnlockManager"
    }
}