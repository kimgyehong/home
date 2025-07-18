package com.flowerdiary.domain.repository

import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId
import kotlinx.coroutines.flow.Flow

/**
 * 탄생화 저장소 인터페이스
 * 365일 탄생화 데이터 관리
 * ISP(인터페이스 분리 원칙) 적용 - 필요한 메서드만 정의
 */
interface BirthFlowerRepository {
    /**
     * ID로 탄생화 조회
     */
    suspend fun findById(id: FlowerId): Result<BirthFlower?>
    
    /**
     * 날짜로 탄생화 조회
     */
    suspend fun findByDate(month: Int, day: Int): Result<BirthFlower?>
    
    /**
     * 모든 탄생화 조회
     */
    suspend fun findAll(): Result<List<BirthFlower>>
    
    /**
     * 해금된 탄생화만 조회
     */
    suspend fun findUnlocked(): Result<List<BirthFlower>>
    
    /**
     * 특정 월의 탄생화 조회
     */
    suspend fun findByMonth(month: Int): Result<List<BirthFlower>>
    
    /**
     * 탄생화 해금
     */
    suspend fun unlock(id: FlowerId): Result<Unit>
    
    /**
     * 오늘의 탄생화 해금 (자동 해금용)
     */
    suspend fun unlockToday(month: Int, day: Int): Result<Unit>
    
    /**
     * 해금된 탄생화 개수
     */
    suspend fun countUnlocked(): Result<Int>
    
    /**
     * 탄생화 변경사항 관찰
     */
    fun observeAll(): Flow<List<BirthFlower>>
    
    /**
     * 특정 탄생화 변경사항 관찰
     */
    fun observeById(id: FlowerId): Flow<BirthFlower?>
    
    /**
     * 해금 상태 변경사항 관찰
     */
    fun observeUnlockStatus(): Flow<Int> // 해금된 개수
}
