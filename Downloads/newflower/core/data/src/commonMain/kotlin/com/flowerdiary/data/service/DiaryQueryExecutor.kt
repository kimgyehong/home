package com.flowerdiary.data.service

import com.flowerdiary.common.constants.RepositoryConstants
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.data.DiaryQueries
import com.flowerdiary.data.Diary as DiaryDb
import com.flowerdiary.domain.model.DiaryId

/**
 * 일기 쿼리 실행 서비스
 * SRP: 데이터베이스 쿼리 실행만 담당
 * 순수한 쿼리 실행 로직만 포함
 */
class DiaryQueryExecutor(
    private val queries: DiaryQueries
) {
    
    /**
     * ID로 일기 조회
     */
    suspend fun findById(id: DiaryId): DiaryDb? {
        Logger.debug(RepositoryConstants.LogTags.DIARY_QUERY_EXECUTOR, "Executing findById: ${id.value}")
        return queries.selectById(id.value).executeAsOneOrNull()
    }
    
    /**
     * 모든 일기 조회
     */
    suspend fun findAll(): List<DiaryDb> {
        Logger.debug(RepositoryConstants.LogTags.DIARY_QUERY_EXECUTOR, "Executing findAll")
        return queries.selectAll().executeAsList()
    }
    
    /**
     * 날짜 범위로 일기 조회
     */
    suspend fun findByDateRange(startTimestamp: Long, endTimestamp: Long): List<DiaryDb> {
        Logger.debug(RepositoryConstants.LogTags.DIARY_QUERY_EXECUTOR, "Executing findByDateRange: $startTimestamp - $endTimestamp")
        return queries.selectByDateRange(startTimestamp, endTimestamp).executeAsList()
    }
    
    /**
     * 년월로 일기 조회
     */
    suspend fun findByYearMonth(year: Int, month: Int): List<DiaryDb> {
        Logger.debug(RepositoryConstants.LogTags.DIARY_QUERY_EXECUTOR, "Executing findByYearMonth: $year-$month")
        return queries.selectByYearMonth(year.toLong(), month.toLong()).executeAsList()
    }
    
    /**
     * 꽃 ID로 일기 조회
     */
    suspend fun findByFlowerId(flowerId: Int): List<DiaryDb> {
        Logger.debug(RepositoryConstants.LogTags.DIARY_QUERY_EXECUTOR, "Executing findByFlowerId: $flowerId")
        return queries.selectByFlowerId(flowerId.toLong()).executeAsList()
    }
    
    /**
     * 일기 저장
     */
    suspend fun save(params: DiaryInsertParams) {
        Logger.debug(RepositoryConstants.LogTags.DIARY_QUERY_EXECUTOR, "Executing save: ${params.id}")
        queries.insertOrReplace(
            id = params.id,
            title = params.title,
            content = params.content,
            mood = params.mood,
            weather = params.weather,
            flower_id = params.flowerId,
            font_family = params.fontFamily,
            font_size = params.fontSize,
            font_color = params.fontColor,
            background_theme = params.backgroundTheme,
            created_at = params.createdAt,
            updated_at = params.updatedAt
        )
    }
    
    /**
     * 일기 삭제
     */
    suspend fun delete(id: DiaryId) {
        Logger.debug(RepositoryConstants.LogTags.DIARY_QUERY_EXECUTOR, "Executing delete: ${id.value}")
        queries.deleteById(id.value)
    }
    
    /**
     * 일기 개수 조회
     */
    suspend fun count(): Long {
        Logger.debug(RepositoryConstants.LogTags.DIARY_QUERY_EXECUTOR, "Executing count")
        return queries.countAll().executeAsOne()
    }
}