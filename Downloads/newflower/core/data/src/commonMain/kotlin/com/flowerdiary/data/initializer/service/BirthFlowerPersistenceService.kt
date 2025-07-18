package com.flowerdiary.data.initializer.service

import com.flowerdiary.common.data.UnitOfWork
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.data.FlowerDiaryDatabase
import com.flowerdiary.data.model.BirthFlowerMapper.toDbParams
import com.flowerdiary.domain.model.BirthFlower

/**
 * 탄생화 데이터 저장 서비스
 * SRP: 데이터베이스 저장만 담당
 * 트랜잭션 관리를 위해 UnitOfWork 사용
 */
class BirthFlowerPersistenceService(
    private val database: FlowerDiaryDatabase,
    private val unitOfWork: UnitOfWork
) {
    
    /**
     * 탄생화 목록을 데이터베이스에 저장
     * 트랜잭션으로 원자성 보장
     */
    suspend fun saveAll(flowers: List<BirthFlower>): Result<Unit> =
        unitOfWork.transaction {
            ExceptionUtil.runCatchingSuspend {
                Logger.info(TAG, "Saving ${flowers.size} birth flowers to database")
                
                flowers.forEach { flower ->
                    saveFlower(flower)
                }
                
                Logger.info(TAG, "Successfully saved all birth flowers")
            }.getOrThrow()
        }
    
    /**
     * 단일 탄생화 저장
     */
    private fun saveFlower(flower: BirthFlower) {
        database.birthFlowerQueries.insertFlower(
            id = flower.id.value.toLong(),
            month = flower.month.toLong(),
            day = flower.day.toLong(),
            name_kr = flower.nameKr,
            name_en = flower.nameEn,
            meaning = flower.meaning,
            description = flower.description,
            image_url = flower.imageUrl,
            background_color = flower.backgroundColor,
            is_unlocked = if (flower.isUnlocked) UNLOCKED else LOCKED
        )
        
        Logger.debug(TAG, "Saved flower: ${flower.nameKr} (${flower.month}/${flower.day})")
    }
    
    companion object {
        private const val TAG = "BirthFlowerPersistenceService"
        private const val UNLOCKED = 1L
        private const val LOCKED = 0L
    }
}
