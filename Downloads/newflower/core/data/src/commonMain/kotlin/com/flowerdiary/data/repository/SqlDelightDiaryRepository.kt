package com.flowerdiary.data.repository

import com.flowerdiary.data.FlowerDiaryDatabase
import com.flowerdiary.data.service.DiaryDataMapper
import com.flowerdiary.data.service.DiaryErrorHandler
import com.flowerdiary.data.service.DiaryFlowTransformer
import com.flowerdiary.data.service.DiaryQueryExecutor
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.model.DiaryId
import com.flowerdiary.domain.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow

/**
 * 현대적 Diary Repository 구현
 * SRP: 마이크로 서비스들을 조합하여 Repository 인터페이스 구현
 * 각 서비스에 적절한 책임 위임
 */
class SqlDelightDiaryRepository(
    database: FlowerDiaryDatabase
) : DiaryRepository {
    
    private val queryExecutor = DiaryQueryExecutor(database.diaryQueries)
    private val flowTransformer = DiaryFlowTransformer(database.diaryQueries)
    
    override suspend fun findById(id: DiaryId): Result<Diary?> {
        return DiaryErrorHandler.handleSingleResult(
            operation = { 
                queryExecutor.findById(id)?.let { DiaryDataMapper.toDomain(it) }
            },
            operationName = "findById"
        )
    }
    
    override suspend fun findAll(): Result<List<Diary>> {
        return DiaryErrorHandler.handleListResult(
            operation = { 
                DiaryDataMapper.toDomainList(queryExecutor.findAll())
            },
            operationName = "findAll"
        )
    }
    
    override suspend fun findByDateRange(startTimestamp: Long, endTimestamp: Long): Result<List<Diary>> {
        return DiaryErrorHandler.handleListResult(
            operation = { 
                DiaryDataMapper.toDomainList(queryExecutor.findByDateRange(startTimestamp, endTimestamp))
            },
            operationName = "findByDateRange"
        )
    }
    
    override suspend fun findByYearMonth(year: Int, month: Int): Result<List<Diary>> {
        return DiaryErrorHandler.handleListResult(
            operation = { 
                DiaryDataMapper.toDomainList(queryExecutor.findByYearMonth(year, month))
            },
            operationName = "findByYearMonth"
        )
    }
    
    override suspend fun findByFlowerId(flowerId: Int): Result<List<Diary>> {
        return DiaryErrorHandler.handleListResult(
            operation = { 
                DiaryDataMapper.toDomainList(queryExecutor.findByFlowerId(flowerId))
            },
            operationName = "findByFlowerId"
        )
    }
    
    override suspend fun save(diary: Diary): Result<Unit> {
        return DiaryErrorHandler.handleUnitResult(
            operation = { 
                queryExecutor.save(DiaryDataMapper.toDbParams(diary))
            },
            operationName = "save"
        )
    }
    
    override suspend fun delete(id: DiaryId): Result<Unit> {
        return DiaryErrorHandler.handleUnitResult(
            operation = { 
                queryExecutor.delete(id)
            },
            operationName = "delete"
        )
    }
    
    override suspend fun count(): Result<Int> {
        return DiaryErrorHandler.handleCountResult(
            operation = { 
                queryExecutor.count()
            },
            operationName = "count"
        )
    }
    
    override fun observeAll(): Flow<List<Diary>> = flowTransformer.observeAll()
    
    override fun observeById(id: DiaryId): Flow<Diary?> = flowTransformer.observeById(id)
}