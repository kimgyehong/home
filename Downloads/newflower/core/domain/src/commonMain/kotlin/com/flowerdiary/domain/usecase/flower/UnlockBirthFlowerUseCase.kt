package com.flowerdiary.domain.usecase.flower

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.platform.PreferencesKeys
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.repository.BirthFlowerRepository

/**
 * 탄생화 해금 유스케이스
 * 매일 자동 해금 및 수동 해금 로직 담당
 */
class UnlockBirthFlowerUseCase(
    private val birthFlowerRepository: BirthFlowerRepository,
    private val preferencesStore: PreferencesStore
) {
    /**
     * 오늘의 탄생화 자동 해금
     * 앱 시작 시 호출되어 오늘 날짜의 꽃을 해금
     */
    suspend fun unlockTodayFlower(): Result<Unit> {
        val today = DateTimeUtil.getCurrentDate()
        val lastUnlockDateStr = preferencesStore.getString(PreferencesKeys.LAST_UNLOCK_DATE)
        val todayStr = "${today.year}-${today.month}-${today.day}"
        
        // 오늘 이미 해금했다면 스킵
        if (lastUnlockDateStr == todayStr) {
            Logger.info(TAG, "Today's flower already unlocked")
            return Result.success(Unit)
        }
        
        Logger.debug(TAG, "Unlocking today's flower: ${today.month}/${today.day}")
        
        return birthFlowerRepository.unlockToday(today.month, today.day)
            .onSuccess {
                preferencesStore.putString(PreferencesKeys.LAST_UNLOCK_DATE, todayStr)
                Logger.info(TAG, "Today's flower unlocked successfully")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to unlock today's flower", it)
            }
    }
    
    /**
     * 특정 탄생화 수동 해금
     * 디버그 모드나 특별 이벤트용
     */
    suspend fun unlockById(id: FlowerId): Result<Unit> {
        Logger.debug(TAG, "Manually unlocking flower: ${id.value}")
        
        // 이미 해금되었는지 확인
        val flower = birthFlowerRepository.findById(id).getOrNull()
        if (flower?.isUnlocked == true) {
            Logger.info(TAG, "Flower already unlocked: ${id.value}")
            return Result.success(Unit)
        }
        
        return birthFlowerRepository.unlock(id)
            .onSuccess { 
                Logger.info(TAG, "Flower unlocked successfully: ${id.value}")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to unlock flower: ${id.value}", it)
            }
    }
    
    /**
     * 해금된 꽃 개수 조회
     */
    suspend fun getUnlockedCount(): Result<Int> {
        return birthFlowerRepository.countUnlocked()
            .onSuccess { count ->
                Logger.info(TAG, "Unlocked flowers count: $count")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to get unlocked count", it)
            }
    }
    
    companion object {
        private const val TAG = "UnlockBirthFlowerUseCase"
    }
}
