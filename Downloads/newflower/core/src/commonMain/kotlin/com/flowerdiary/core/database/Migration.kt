package com.flowerdiary.core.database

/**
 * 데이터베이스 마이그레이션 인터페이스
 * 데이터베이스 스키마 변경 관리
 */
interface Migration {
  
  /**
   * 마이그레이션 버전
   */
  val version: Int
  
  /**
   * 마이그레이션 실행
   */
  suspend fun migrate(): Boolean
  
  /**
   * 마이그레이션 롤백
   */
  suspend fun rollback(): Boolean
}

/**
 * 마이그레이션 매니저
 */
interface MigrationManager {
  
  /**
   * 마이그레이션 등록
   */
  fun registerMigration(migration: Migration)
  
  /**
   * 필요한 마이그레이션 실행
   */
  suspend fun runMigrations(currentVersion: Int, targetVersion: Int): Boolean
  
  /**
   * 모든 마이그레이션 실행
   */
  suspend fun runAllMigrations(): Boolean
  
  /**
   * 특정 버전으로 마이그레이션
   */
  suspend fun migrateTo(version: Int): Boolean
  
  /**
   * 마이그레이션 이력 조회
   */
  suspend fun getMigrationHistory(): List<MigrationRecord>
  
  /**
   * 마이그레이션 필요 여부 확인
   */
  suspend fun needsMigration(currentVersion: Int): Boolean
}

/**
 * 마이그레이션 기록
 */
data class MigrationRecord(
  val version: Int,
  val appliedAt: Long,
  val success: Boolean,
  val errorMessage: String? = null
)

/**
 * 기본 마이그레이션 구현
 */
abstract class BaseMigration(override val version: Int) : Migration {
  
  /**
   * 마이그레이션 SQL 스크립트
   */
  abstract fun getMigrationScript(): String
  
  /**
   * 롤백 SQL 스크립트
   */
  abstract fun getRollbackScript(): String
  
  override suspend fun migrate(): Boolean {
    return try {
      executeSql(getMigrationScript())
      true
    } catch (e: Exception) {
      false
    }
  }
  
  override suspend fun rollback(): Boolean {
    return try {
      executeSql(getRollbackScript())
      true
    } catch (e: Exception) {
      false
    }
  }
  
  /**
   * SQL 실행 (플랫폼별 구현 필요)
   */
  protected abstract suspend fun executeSql(sql: String)
}