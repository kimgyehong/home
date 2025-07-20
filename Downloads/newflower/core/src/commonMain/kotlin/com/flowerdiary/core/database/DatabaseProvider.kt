package com.flowerdiary.core.database

/**
 * 데이터베이스 제공자 인터페이스
 * 플랫폼별 데이터베이스 초기화 및 관리
 */
interface DatabaseProvider {
  
  /**
   * 데이터베이스 초기화
   */
  suspend fun initializeDatabase(): Boolean
  
  /**
   * 데이터베이스 연결 확인
   */
  suspend fun isConnected(): Boolean
  
  /**
   * 데이터베이스 닫기
   */
  suspend fun closeDatabase()
  
  /**
   * 트랜잭션 실행
   */
  suspend fun <T> withTransaction(block: suspend () -> T): T
  
  /**
   * 데이터베이스 버전 확인
   */
  suspend fun getDatabaseVersion(): Int
  
  /**
   * 테이블 존재 여부 확인
   */
  suspend fun tableExists(tableName: String): Boolean
  
  /**
   * 초기 데이터 삽입 필요 여부 확인
   */
  suspend fun needsInitialData(): Boolean
  
  /**
   * 초기 데이터 삽입
   */
  suspend fun insertInitialData(): Boolean
  
  /**
   * 데이터베이스 백업
   */
  suspend fun backupDatabase(backupPath: String): Boolean
  
  /**
   * 데이터베이스 복원
   */
  suspend fun restoreDatabase(backupPath: String): Boolean
  
  /**
   * 데이터베이스 최적화
   */
  suspend fun optimizeDatabase(): Boolean
  
  /**
   * 데이터베이스 크기 조회
   */
  suspend fun getDatabaseSize(): Long
  
  /**
   * 캐시 지우기
   */
  suspend fun clearCache()
}