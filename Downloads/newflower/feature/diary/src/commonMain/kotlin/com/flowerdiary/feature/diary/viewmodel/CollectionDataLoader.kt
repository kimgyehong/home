package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.usecase.flower.GetBirthFlowerUseCase
import com.flowerdiary.feature.diary.state.CollectionState

/**
 * 도감 데이터 로더
 * SRP: 도감 데이터 로드와 수집 완료 확인만 담당
 */
internal class CollectionDataLoader(
    private val getBirthFlowerUseCase: GetBirthFlowerUseCase
) {
    
    private companion object {
        private const val TAG = "CollectionDataLoader"
    }
    
    /**
     * 도감 데이터 로드
     */
    suspend fun loadCollection(): Result<CollectionLoadResult> {
        return try {
            getBirthFlowerUseCase.getAll()
                .fold(
                    onSuccess = { flowers ->
                        val unlockedCount = flowers.count { it.isUnlocked }
                        Result.success(
                            CollectionLoadResult(
                                flowers = flowers,
                                unlockedCount = unlockedCount
                            )
                        )
                    },
                    onFailure = { error ->
                        Logger.error(TAG, "Failed to load collection", error)
                        Result.failure(error)
                    }
                )
        } catch (e: Exception) {
            Logger.error(TAG, "Unexpected error loading collection", e)
            Result.failure(e)
        }
    }
    
    /**
     * 수집 완료 확인
     */
    fun checkCompletion(
        unlockedCount: Int, 
        totalCount: Int
    ): Boolean {
        return unlockedCount >= totalCount
    }
    
    /**
     * 도감 로드 결과
     */
    data class CollectionLoadResult(
        val flowers: List<BirthFlower>,
        val unlockedCount: Int
    )
}
