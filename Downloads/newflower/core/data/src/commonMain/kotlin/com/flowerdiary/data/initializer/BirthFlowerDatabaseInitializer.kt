package com.flowerdiary.data.initializer

import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.platform.DatabaseDriverProvider
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.logInfo
import com.flowerdiary.common.utils.logDebug
import com.flowerdiary.data.FlowerDiaryDatabase
import com.flowerdiary.data.SqlDelightUnitOfWork
import com.flowerdiary.data.initializer.loader.BirthFlowerDataLoader
import com.flowerdiary.data.initializer.resource.BirthFlowerResourceProvider
import com.flowerdiary.data.initializer.service.BirthFlowerPersistenceService

/**
 * 탄생화 데이터베이스 초기화 조정자
 * SRP: 초기화 프로세스 조정만 담당
 * 각 컴포넌트의 책임을 조합하여 전체 프로세스 관리
 */
class BirthFlowerDatabaseInitializer(
    private val driverProvider: DatabaseDriverProvider,
    private val resourceProvider: BirthFlowerResourceProvider = BirthFlowerResourceProvider(),
    private val dataLoader: BirthFlowerDataLoader = BirthFlowerDataLoader()
) {
    
    private val database: FlowerDiaryDatabase by lazy {
        FlowerDiaryDatabase(
            driverProvider.createDriver(
                FlowerDiaryDatabase.Schema,
                Config.DB_NAME
            )
        )
    }
    
    private val unitOfWork by lazy {
        SqlDelightUnitOfWork(database)
    }
    
    private val persistenceService by lazy {
        BirthFlowerPersistenceService(database, unitOfWork)
    }
    
    /**
     * 탄생화 데이터 초기화
     */
    suspend fun initialize(): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            logInfo("Starting birth flower database initialization")
            
            // 이미 초기화되었는지 확인
            if (isAlreadyInitialized()) {
                logInfo("Database already initialized")
                return@runCatchingSuspend
            }
            
            // 초기화 프로세스 실행
            performInitialization()
        }
    
    /**
     * 초기화 여부 확인
     */
    private fun isAlreadyInitialized(): Boolean {
        val count = database.birthFlowerQueries
            .countUnlocked()
            .executeAsOne()
            .toInt()
        
        return count > MINIMUM_EXPECTED_FLOWERS
    }
    
    /**
     * 초기화 프로세스 수행
     */
    private suspend fun performInitialization() {
        // 1. JSON 데이터 읽기
        val jsonContent = resourceProvider.readBirthFlowerJson().getOrThrow()
        logDebug("Loaded JSON resource")
        
        // 2. 데이터 로드 및 변환
        val flowers = dataLoader.loadFromJson(jsonContent).getOrThrow()
        logDebug("Loaded ${flowers.size} flowers from JSON")
        
        // 3. 데이터베이스 저장
        persistenceService.saveAll(flowers).getOrThrow()
        logInfo("Birth flower initialization completed")
    }
    
    companion object {
        private const val MINIMUM_EXPECTED_FLOWERS = 0
    }
}
