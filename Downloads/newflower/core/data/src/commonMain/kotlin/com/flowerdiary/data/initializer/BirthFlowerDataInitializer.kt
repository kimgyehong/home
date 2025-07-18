package com.flowerdiary.data.initializer

import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.data.db.DiaryDb
import com.flowerdiary.data.source.BirthFlowerDataSource

/**
 * 탄생화 데이터 초기화
 * SRP: 탄생화 데이터 초기화만 담당
 * 하드코딩 금지, SOLID 원칙 준수
 */
class BirthFlowerDataInitializer(
    private val database: DiaryDb,
    private val dataSource: BirthFlowerDataSource
) {
    
    /**
     * 탄생화 데이터 초기화
     */
    suspend fun initializeBirthFlowerData() {
        ExceptionUtil.runCatchingSuspend {
            Logger.info(TAG, "Starting birth flower data initialization")
            
            // 기존 데이터 확인
            val existingCount = database.birthFlowerQueries.countAll().executeAsOne()
            if (existingCount > 0) {
                Logger.info(TAG, "Birth flower data already exists. Skipping initialization.")
                return@runCatchingSuspend
            }
            
            // 데이터 소스에서 탄생화 데이터 가져오기
            val birthFlowers = dataSource.createAllBirthFlowers()
            
            // 데이터 검증
            require(birthFlowers.size == Config.TOTAL_BIRTH_FLOWERS) {
                "Expected ${Config.TOTAL_BIRTH_FLOWERS} birth flowers, but got ${birthFlowers.size}"
            }
            
            // 데이터베이스에 삽입
            database.transactionWithResult {
                birthFlowers.forEach { flower ->
                    database.birthFlowerQueries.insertBirthFlower(
                        id = flower.id.value.toLong(),
                        month = flower.month.toLong(),
                        day = flower.day.toLong(),
                        name_kr = flower.nameKr,
                        name_en = flower.nameEn,
                        meaning = flower.meaning,
                        description = flower.description,
                        image_url = flower.imageUrl,
                        background_color = flower.backgroundColor,
                        is_unlocked = if (flower.isUnlocked) 1L else 0L
                    )
                }
            }
            
            Logger.info(TAG, "Birth flower data initialization completed. ${birthFlowers.size} flowers added.")
        }.onFailure { e ->
            Logger.error(TAG, "Failed to initialize birth flower data", e)
            throw e
        }
    }
    
    companion object {
        private const val TAG = "BirthFlowerDataInitializer"
    }
}
