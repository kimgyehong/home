package com.flowerdiary.data.repository

import com.flowerdiary.data.FlowerDiaryDatabase
import com.flowerdiary.data.service.BirthFlowerDataMapper
import com.flowerdiary.data.service.BirthFlowerFlowTransformer
import com.flowerdiary.data.service.BirthFlowerQueryExecutor
import com.flowerdiary.data.service.DiaryErrorHandler
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.repository.BirthFlowerRepository
import kotlinx.coroutines.flow.Flow

/**
 * 현대적 BirthFlower Repository 구현
 * SRP: 마이크로 서비스들을 조합하여 Repository 인터페이스 구현
 */
class SqlDelightBirthFlowerRepository(
    database: FlowerDiaryDatabase
) : BirthFlowerRepository {
    
    private val queryExecutor = BirthFlowerQueryExecutor(database.birthFlowerQueries)
    private val flowTransformer = BirthFlowerFlowTransformer(database.birthFlowerQueries)
    
    override suspend fun findById(id: FlowerId): Result<BirthFlower?> =
        DiaryErrorHandler.handleSingleResult(
            operation = { queryExecutor.findById(id)?.let { BirthFlowerDataMapper.toDomain(it) } },
            operationName = "findById"
        )
    
    override suspend fun findByDate(month: Int, day: Int): Result<BirthFlower?> =
        DiaryErrorHandler.handleSingleResult(
            operation = { queryExecutor.findByDate(month, day)?.let { BirthFlowerDataMapper.toDomain(it) } },
            operationName = "findByDate"
        )
    
    override suspend fun findAll(): Result<List<BirthFlower>> =
        DiaryErrorHandler.handleListResult(
            operation = { BirthFlowerDataMapper.toDomainList(queryExecutor.findAll()) },
            operationName = "findAll"
        )
    
    override suspend fun findUnlocked(): Result<List<BirthFlower>> =
        DiaryErrorHandler.handleListResult(
            operation = { BirthFlowerDataMapper.toDomainList(queryExecutor.findUnlocked()) },
            operationName = "findUnlocked"
        )
    
    override suspend fun findByMonth(month: Int): Result<List<BirthFlower>> =
        DiaryErrorHandler.handleListResult(
            operation = { BirthFlowerDataMapper.toDomainList(queryExecutor.findByMonth(month)) },
            operationName = "findByMonth"
        )
    
    override suspend fun save(birthFlower: BirthFlower): Result<Unit> =
        DiaryErrorHandler.handleUnitResult(
            operation = { queryExecutor.save(BirthFlowerDataMapper.toDbParams(birthFlower)) },
            operationName = "save"
        )
    
    override suspend fun updateUnlocked(id: FlowerId, isUnlocked: Boolean): Result<Unit> =
        DiaryErrorHandler.handleUnitResult(
            operation = { queryExecutor.updateUnlocked(id, isUnlocked) },
            operationName = "updateUnlocked"
        )
    
    override suspend fun count(): Result<Int> =
        DiaryErrorHandler.handleCountResult(
            operation = { queryExecutor.count() },
            operationName = "count"
        )
    
    override suspend fun countUnlocked(): Result<Int> =
        DiaryErrorHandler.handleCountResult(
            operation = { queryExecutor.countUnlocked() },
            operationName = "countUnlocked"
        )
    
    override fun observeAll(): Flow<List<BirthFlower>> = flowTransformer.observeAll()
    
    override fun observeById(id: FlowerId): Flow<BirthFlower?> = flowTransformer.observeById(id)
    
    override fun observeUnlocked(): Flow<List<BirthFlower>> = flowTransformer.observeUnlocked()
    
    override fun observeByMonth(month: Int): Flow<List<BirthFlower>> = flowTransformer.observeByMonth(month)
}