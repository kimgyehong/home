package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.usecase.flower.GetBirthFlowerUseCase
import com.flowerdiary.domain.usecase.flower.UnlockBirthFlowerUseCase

/**
 * 오늘의 꽃 해금 처리
 * SRP: 오늘의 꽃 해금 로직만 담당
 */
internal class TodayFlowerUnlocker(
    private val getBirthFlowerUseCase: GetBirthFlowerUseCase,
    private val unlockBirthFlowerUseCase: UnlockBirthFlowerUseCase
) {
    
    private companion object {
        private const val TAG = "TodayFlowerUnlocker"
    }
    
    /**
     * 오늘의 꽃 해금 처리
     */
    suspend fun unlockTodayFlower(): Result<TodayFlowerUnlockResult> {
        return try {
            val today = DateTimeUtil.getCurrentDate()
            
            getBirthFlowerUseCase.getByDate(today.month, today.day)
                .fold(
                    onSuccess = { flower ->
                        if (flower != null) {
                            handleFlowerUnlock(flower)
                        } else {
                            Result.success(TodayFlowerUnlockResult.NoFlowerFound)
                        }
                    },
                    onFailure = { error ->
                        Logger.error(TAG, "Failed to get today's flower", error)
                        Result.failure(error)
                    }
                )
        } catch (e: Exception) {
            Logger.error(TAG, "Unexpected error unlocking today's flower", e)
            Result.failure(e)
        }
    }
    
    /**
     * 꽃 해금 처리
     */
    private suspend fun handleFlowerUnlock(
        flower: BirthFlower
    ): Result<TodayFlowerUnlockResult> {
        return if (flower.isUnlocked) {
            Result.success(TodayFlowerUnlockResult.AlreadyUnlocked(flower))
        } else {
            unlockBirthFlowerUseCase.unlockTodayFlower()
                .fold(
                    onSuccess = {
                        Result.success(TodayFlowerUnlockResult.NewlyUnlocked(flower))
                    },
                    onFailure = { error ->
                        Logger.error(TAG, "Failed to unlock flower", error)
                        Result.failure(error)
                    }
                )
        }
    }
    
    /**
     * 오늘의 꽃 해금 결과
     */
    sealed class TodayFlowerUnlockResult {
        data class NewlyUnlocked(val flower: BirthFlower) : TodayFlowerUnlockResult()
        data class AlreadyUnlocked(val flower: BirthFlower) : TodayFlowerUnlockResult()
        object NoFlowerFound : TodayFlowerUnlockResult()
    }
}
