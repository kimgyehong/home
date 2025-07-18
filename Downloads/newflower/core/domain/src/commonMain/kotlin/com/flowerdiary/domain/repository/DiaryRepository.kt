package com.flowerdiary.domain.repository

import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.model.DiaryId
import com.flowerdiary.domain.specification.DiaryQuerySpecification
import kotlinx.coroutines.flow.Flow

/**
 * 일기 저장소 인터페이스
 * SOLID 원칙 중 DIP(의존성 역전 원칙) 적용
 * 도메인 계층은 구현체가 아닌 추상화에 의존
 */
interface DiaryRepository {
    /**
     * ID로 일기 조회
     */
    suspend fun findById(id: DiaryId): Result<Diary?>
    
    /**
     * 명세에 따른 일기 조회
     * OCP 준수: 새로운 쿼리 조건 추가 시 인터페이스 수정 없이 확장 가능
     */
    suspend fun findBySpecification(specification: DiaryQuerySpecification): Result<List<Diary>>
    
    /**
     * 모든 일기 조회 (최신순)
     */
    suspend fun findAll(): Result<List<Diary>>
    
    /**
     * 날짜 범위로 일기 조회
     */
    suspend fun findByDateRange(startTimestamp: Long, endTimestamp: Long): Result<List<Diary>>
    
    /**
     * 특정 년월의 일기 조회
     */
    suspend fun findByYearMonth(year: Int, month: Int): Result<List<Diary>>
    
    /**
     * 일기 저장 (생성 또는 수정)
     */
    suspend fun save(diary: Diary): Result<Unit>
    
    /**
     * 일기 삭제
     */
    suspend fun delete(id: DiaryId): Result<Unit>
    
    /**
     * 일기 개수 조회
     */
    suspend fun count(): Result<Int>
    
    /**
     * 특정 꽃 ID를 가진 일기 조회
     */
    suspend fun findByFlowerId(flowerId: Int): Result<List<Diary>>
    
    /**
     * 일기 변경사항 관찰 (Reactive)
     */
    fun observeAll(): Flow<List<Diary>>
    
    /**
     * 특정 일기 변경사항 관찰
     */
    fun observeById(id: DiaryId): Flow<Diary?>
}
