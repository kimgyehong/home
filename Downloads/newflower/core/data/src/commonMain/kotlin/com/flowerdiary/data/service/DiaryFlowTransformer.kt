package com.flowerdiary.data.service

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.flowerdiary.common.constants.RepositoryConstants
import com.flowerdiary.common.platform.DefaultDispatcher
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.data.DiaryQueries
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.model.DiaryId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 일기 Flow 변환 서비스
 * SRP: 데이터베이스 쿼리를 Flow로 변환하는 로직만 담당
 * 리액티브 데이터 스트림 생성을 위한 순수 변환 로직
 */
class DiaryFlowTransformer(
    private val queries: DiaryQueries
) {
    
    /**
     * 모든 일기를 관찰하는 Flow 생성
     */
    fun observeAll(): Flow<List<Diary>> {
        Logger.debug(RepositoryConstants.LogTags.FLOW_TRANSFORMER, "Creating observeAll Flow")
        return queries.selectAll()
            .asFlow()
            .mapToList(DefaultDispatcher)
            .map { dbDiaries -> 
                DiaryDataMapper.toDomainList(dbDiaries) 
            }
    }
    
    /**
     * 특정 ID의 일기를 관찰하는 Flow 생성
     */
    fun observeById(id: DiaryId): Flow<Diary?> {
        Logger.debug(RepositoryConstants.LogTags.FLOW_TRANSFORMER, "Creating observeById Flow: ${id.value}")
        return queries.selectById(id.value)
            .asFlow()
            .mapToOneOrNull(DefaultDispatcher)
            .map { dbDiary -> 
                dbDiary?.let { DiaryDataMapper.toDomain(it) }
            }
    }
    
    /**
     * 날짜 범위의 일기를 관찰하는 Flow 생성
     */
    fun observeByDateRange(startTimestamp: Long, endTimestamp: Long): Flow<List<Diary>> {
        Logger.debug(RepositoryConstants.LogTags.FLOW_TRANSFORMER, "Creating observeByDateRange Flow: $startTimestamp - $endTimestamp")
        return queries.selectByDateRange(startTimestamp, endTimestamp)
            .asFlow()
            .mapToList(DefaultDispatcher)
            .map { dbDiaries -> 
                DiaryDataMapper.toDomainList(dbDiaries) 
            }
    }
    
    /**
     * 년월 일기를 관찰하는 Flow 생성
     */
    fun observeByYearMonth(year: Int, month: Int): Flow<List<Diary>> {
        Logger.debug(RepositoryConstants.LogTags.FLOW_TRANSFORMER, "Creating observeByYearMonth Flow: $year-$month")
        return queries.selectByYearMonth(year.toLong(), month.toLong())
            .asFlow()
            .mapToList(DefaultDispatcher)
            .map { dbDiaries -> 
                DiaryDataMapper.toDomainList(dbDiaries) 
            }
    }
}