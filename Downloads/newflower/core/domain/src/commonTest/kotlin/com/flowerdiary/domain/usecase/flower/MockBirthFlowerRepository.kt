package com.flowerdiary.domain.usecase.flower

import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.repository.BirthFlowerRepository

/**
 * 테스트용 Mock Repository
 * SRP: 테스트를 위한 가짜 데이터 제공만 담당
 */
internal class MockBirthFlowerRepository : BirthFlowerRepository {
    
    var findUnlockedResult: Result<List<BirthFlower>> = Result.success(emptyList())
    var findByDateResult: Result<BirthFlower?> = Result.success(null)
    var findByIdResult: Result<BirthFlower?> = Result.success(null)
    var findAllResult: Result<List<BirthFlower>> = Result.success(emptyList())
    
    override suspend fun findUnlocked(): Result<List<BirthFlower>> = findUnlockedResult
    
    override suspend fun findByDate(
        month: Int,
        day: Int
    ): Result<BirthFlower?> = findByDateResult
    
    override suspend fun findById(id: FlowerId): Result<BirthFlower?> = findByIdResult
    
    override suspend fun findAll(): Result<List<BirthFlower>> = findAllResult
    
    override suspend fun updateUnlockStatus(
        id: FlowerId,
        isUnlocked: Boolean
    ): Result<Unit> = Result.success(Unit)
    
    override suspend fun insertAll(flowers: List<BirthFlower>): Result<Unit> = 
        Result.success(Unit)
    
    override suspend fun deleteAll(): Result<Unit> = Result.success(Unit)
    
    override suspend fun getCount(): Result<Int> = Result.success(0)
}
