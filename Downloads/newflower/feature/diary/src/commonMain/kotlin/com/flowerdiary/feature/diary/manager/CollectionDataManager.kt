package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.usecase.flower.GetBirthFlowerUseCase

/**
 * 도감 데이터 관리 매니저
 * SRP: 도감 데이터 로드 및 필터링 책임만 담당
 */
class CollectionDataManager(
    private val getBirthFlowerUseCase: GetBirthFlowerUseCase
) {
    
    /**
     * 모든 꽃 데이터 로드
     */
    suspend fun loadAllFlowers(): Result<List<BirthFlower>> {
        return getBirthFlowerUseCase.getAll()
            .onFailure { error ->
                Logger.error(TAG, "Failed to load collection", error)
            }
    }
    
    /**
     * 특정 날짜의 꽃 정보 가져오기
     */
    suspend fun getFlowerByDate(month: Int, day: Int): Result<BirthFlower?> {
        return getBirthFlowerUseCase.getByDate(month, day)
            .onFailure { error ->
                Logger.error(TAG, "Failed to get flower by date", error)
            }
    }
    
    /**
     * 월별 꽃 필터링
     */
    fun filterFlowersByMonth(
        flowers: List<BirthFlower>, 
        month: Int?
    ): List<BirthFlower> {
        return if (month == null) {
            flowers
        } else {
            flowers.filter { flower ->
                flower.month == month
            }
        }
    }
    
    /**
     * 해금된 꽃 개수 계산
     */
    fun countUnlockedFlowers(flowers: List<BirthFlower>): Int {
        return flowers.count { it.isUnlocked }
    }
    
    companion object {
        private const val TAG = "CollectionDataManager"
    }
}