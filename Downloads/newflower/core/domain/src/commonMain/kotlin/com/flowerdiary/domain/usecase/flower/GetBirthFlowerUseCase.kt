package com.flowerdiary.domain.usecase.flower

import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.repository.BirthFlowerRepository

/**
 * 탄생화 조회 유스케이스
 * 다양한 조건으로 탄생화 조회
 */
class GetBirthFlowerUseCase(
    private val birthFlowerRepository: BirthFlowerRepository
) {
    /**
     * ID로 탄생화 조회
     */
    suspend fun getById(id: FlowerId): Result<BirthFlower?> {
        Logger.debug(TAG, "Getting birth flower by id: ${id.value}")
        
        return birthFlowerRepository.findById(id)
            .onSuccess { flower ->
                if (flower != null) {
                    Logger.info(TAG, "Birth flower found: ${flower.nameKr}")
                } else {
                    Logger.info(TAG, "Birth flower not found for id: ${id.value}")
                }
            }
            .onFailure { 
                Logger.error(TAG, "Failed to get birth flower by id: ${id.value}", it)
            }
    }
    
    /**
     * 날짜로 탄생화 조회
     */
    suspend fun getByDate(month: Int, day: Int): Result<BirthFlower?> {
        require(month in 1..12) { "Month must be between 1 and 12" }
        require(day in 1..31) { "Day must be between 1 and 31" }
        
        Logger.debug(TAG, "Getting birth flower for date: $month/$day")
        
        return birthFlowerRepository.findByDate(month, day)
            .onSuccess { flower ->
                if (flower != null) {
                    Logger.info(TAG, "Birth flower found for $month/$day: ${flower.nameKr}")
                } else {
                    Logger.info(TAG, "Birth flower not found for date: $month/$day")
                }
            }
            .onFailure { 
                Logger.error(TAG, "Failed to get birth flower for date: $month/$day", it)
            }
    }
    
    /**
     * 모든 탄생화 조회
     */
    suspend fun getAll(): Result<List<BirthFlower>> {
        Logger.debug(TAG, "Getting all birth flowers")
        
        return birthFlowerRepository.findAll()
            .onSuccess { flowers ->
                Logger.info(TAG, "Found ${flowers.size} birth flowers")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to get all birth flowers", it)
            }
    }
    
    /**
     * 해금된 탄생화만 조회
     */
    suspend fun getUnlocked(): Result<List<BirthFlower>> {
        Logger.debug(TAG, "Getting unlocked birth flowers")
        
        return birthFlowerRepository.findUnlocked()
            .onSuccess { flowers ->
                Logger.info(TAG, "Found ${flowers.size} unlocked birth flowers")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to get unlocked birth flowers", it)
            }
    }
    
    companion object {
        private const val TAG = "GetBirthFlowerUseCase"
    }
}
