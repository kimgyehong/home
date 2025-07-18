package com.flowerdiary.data.service

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.flowerdiary.common.constants.RepositoryConstants
import com.flowerdiary.common.platform.DefaultDispatcher
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.data.BirthFlowerQueries
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 탄생화 Flow 변환 서비스
 * SRP: 데이터베이스 쿼리를 Flow로 변환하는 로직만 담당
 * 리액티브 데이터 스트림 생성을 위한 순수 변환 로직
 */
class BirthFlowerFlowTransformer(
    private val queries: BirthFlowerQueries
) {
    
    /**
     * 모든 탄생화를 관찰하는 Flow 생성
     */
    fun observeAll(): Flow<List<BirthFlower>> {
        Logger.debug(RepositoryConstants.LogTags.FLOW_TRANSFORMER, "Creating observeAll Flow for BirthFlowers")
        return queries.selectAll()
            .asFlow()
            .mapToList(DefaultDispatcher)
            .map { dbBirthFlowers -> 
                BirthFlowerDataMapper.toDomainList(dbBirthFlowers) 
            }
    }
    
    /**
     * 특정 ID의 탄생화를 관찰하는 Flow 생성
     */
    fun observeById(id: FlowerId): Flow<BirthFlower?> {
        Logger.debug(RepositoryConstants.LogTags.FLOW_TRANSFORMER, "Creating observeById Flow: ${id.value}")
        return queries.selectById(id.value.toLong())
            .asFlow()
            .mapToOneOrNull(DefaultDispatcher)
            .map { dbBirthFlower -> 
                dbBirthFlower?.let { BirthFlowerDataMapper.toDomain(it) }
            }
    }
    
    /**
     * 해금된 탄생화를 관찰하는 Flow 생성
     */
    fun observeUnlocked(): Flow<List<BirthFlower>> {
        Logger.debug(RepositoryConstants.LogTags.FLOW_TRANSFORMER, "Creating observeUnlocked Flow")
        return queries.selectUnlocked()
            .asFlow()
            .mapToList(DefaultDispatcher)
            .map { dbBirthFlowers -> 
                BirthFlowerDataMapper.toDomainList(dbBirthFlowers) 
            }
    }
    
    /**
     * 월별 탄생화를 관찰하는 Flow 생성
     */
    fun observeByMonth(month: Int): Flow<List<BirthFlower>> {
        Logger.debug(RepositoryConstants.LogTags.FLOW_TRANSFORMER, "Creating observeByMonth Flow: $month")
        return queries.selectByMonth(month.toLong())
            .asFlow()
            .mapToList(DefaultDispatcher)
            .map { dbBirthFlowers -> 
                BirthFlowerDataMapper.toDomainList(dbBirthFlowers) 
            }
    }
}