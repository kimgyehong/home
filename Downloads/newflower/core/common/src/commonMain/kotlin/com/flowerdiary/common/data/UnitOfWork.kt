package com.flowerdiary.common.data

/**
 * Unit of Work 패턴 인터페이스
 * 트랜잭션 관리 및 여러 Repository 작업의 원자성 보장
 * SOLID 원칙 중 ISP(인터페이스 분리 원칙) 적용
 */
interface UnitOfWork {
    /**
     * 트랜잭션 내에서 작업 실행
     * 모든 작업이 성공하거나 모두 롤백됨
     */
    suspend fun <R> transaction(block: suspend () -> R): Result<R>
    
    /**
     * 읽기 전용 트랜잭션 실행
     * 성능 최적화를 위해 읽기 작업만 수행할 때 사용
     */
    suspend fun <R> readTransaction(block: suspend () -> R): Result<R>
}
