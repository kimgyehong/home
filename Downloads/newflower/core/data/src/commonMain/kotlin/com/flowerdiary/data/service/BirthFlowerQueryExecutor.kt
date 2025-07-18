package com.flowerdiary.data.service

import com.flowerdiary.common.constants.RepositoryConstants
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.data.BirthFlowerQueries
import com.flowerdiary.data.BirthFlower as BirthFlowerDb
import com.flowerdiary.domain.model.FlowerId

/**
 * 탄생화 쿼리 실행 서비스
 * SRP: 데이터베이스 쿼리 실행만 담당
 * 순수한 쿼리 실행 로직만 포함
 */
class BirthFlowerQueryExecutor(
    private val queries: BirthFlowerQueries
) {
    
    /**
     * ID로 탄생화 조회
     */
    suspend fun findById(id: FlowerId): BirthFlowerDb? {
        Logger.debug(RepositoryConstants.LogTags.BIRTH_FLOWER_REPOSITORY, "Executing findById: ${id.value}")
        return queries.selectById(id.value.toLong()).executeAsOneOrNull()
    }
    
    /**
     * 날짜로 탄생화 조회
     */
    suspend fun findByDate(month: Int, day: Int): BirthFlowerDb? {
        Logger.debug(RepositoryConstants.LogTags.BIRTH_FLOWER_REPOSITORY, "Executing findByDate: $month/$day")
        return queries.selectByDate(month.toLong(), day.toLong()).executeAsOneOrNull()
    }
    
    /**
     * 모든 탄생화 조회
     */
    suspend fun findAll(): List<BirthFlowerDb> {
        Logger.debug(RepositoryConstants.LogTags.BIRTH_FLOWER_REPOSITORY, "Executing findAll")
        return queries.selectAll().executeAsList()
    }
    
    /**
     * 해금된 탄생화 조회
     */
    suspend fun findUnlocked(): List<BirthFlowerDb> {
        Logger.debug(RepositoryConstants.LogTags.BIRTH_FLOWER_REPOSITORY, "Executing findUnlocked")
        return queries.selectUnlocked().executeAsList()
    }
    
    /**
     * 월별 탄생화 조회
     */
    suspend fun findByMonth(month: Int): List<BirthFlowerDb> {
        Logger.debug(RepositoryConstants.LogTags.BIRTH_FLOWER_REPOSITORY, "Executing findByMonth: $month")
        return queries.selectByMonth(month.toLong()).executeAsList()
    }
    
    /**
     * 탄생화 저장
     */
    suspend fun save(params: BirthFlowerInsertParams) {
        Logger.debug(RepositoryConstants.LogTags.BIRTH_FLOWER_REPOSITORY, "Executing save: ${params.id}")
        queries.insertOrReplace(
            id = params.id,
            month = params.month,
            day = params.day,
            name_kr = params.nameKr,
            name_en = params.nameEn,
            meaning = params.meaning,
            description = params.description,
            image_url = params.imageUrl,
            background_color = params.backgroundColor,
            is_unlocked = params.isUnlocked
        )
    }
    
    /**
     * 탄생화 해금 상태 업데이트
     */
    suspend fun updateUnlocked(id: FlowerId, isUnlocked: Boolean) {
        Logger.debug(RepositoryConstants.LogTags.BIRTH_FLOWER_REPOSITORY, "Executing updateUnlocked: ${id.value} -> $isUnlocked")
        queries.updateUnlocked(isUnlocked, id.value.toLong())
    }
    
    /**
     * 탄생화 개수 조회
     */
    suspend fun count(): Long {
        Logger.debug(RepositoryConstants.LogTags.BIRTH_FLOWER_REPOSITORY, "Executing count")
        return queries.countAll().executeAsOne()
    }
    
    /**
     * 해금된 탄생화 개수 조회
     */
    suspend fun countUnlocked(): Long {
        Logger.debug(RepositoryConstants.LogTags.BIRTH_FLOWER_REPOSITORY, "Executing countUnlocked")
        return queries.countUnlocked().executeAsOne()
    }
}