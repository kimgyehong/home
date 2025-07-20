package com.flowerdiary.core.database

import com.flowerdiary.core.constants.DatabaseConstants

/**
 * 데이터베이스 설정 관리
 * 연결, 풀, 트랜잭션 등 데이터베이스 관련 설정
 */
data class DatabaseConfig(
  val databaseName: String = DatabaseConstants.DATABASE_NAME,
  val version: Int = DatabaseConstants.DATABASE_VERSION,
  val connectionPoolSize: Int = DatabaseConstants.CONNECTION_POOL_SIZE,
  val queryTimeout: Long = DatabaseConstants.QUERY_TIMEOUT,
  val transactionTimeout: Long = DatabaseConstants.TRANSACTION_TIMEOUT,
  val maxRetryAttempts: Int = DatabaseConstants.MAX_RETRY_ATTEMPTS,
  val retryDelay: Long = DatabaseConstants.RETRY_DELAY,
  val enableWAL: Boolean = true,
  val enableForeignKeys: Boolean = true,
  val enableCaseSensitive: Boolean = false
) {
  
  /**
   * 설정 유효성 검증
   */
  fun validate(): Boolean {
    return databaseName.isNotBlank() &&
           version > 0 &&
           connectionPoolSize > 0 &&
           queryTimeout > 0 &&
           transactionTimeout > 0 &&
           maxRetryAttempts >= 0 &&
           retryDelay >= 0
  }
  
  /**
   * 개발용 설정
   */
  companion object {
    fun development(): DatabaseConfig = DatabaseConfig(
      databaseName = "flower_diary_dev.db",
      enableWAL = false,
      queryTimeout = 60000L,
      transactionTimeout = 30000L
    )
    
    /**
     * 테스트용 설정
     */
    fun test(): DatabaseConfig = DatabaseConfig(
      databaseName = ":memory:",
      connectionPoolSize = 1,
      queryTimeout = 5000L,
      transactionTimeout = 5000L,
      maxRetryAttempts = 1
    )
    
    /**
     * 프로덕션용 설정
     */
    fun production(): DatabaseConfig = DatabaseConfig(
      enableWAL = true,
      enableForeignKeys = true,
      connectionPoolSize = 5,
      queryTimeout = 30000L,
      transactionTimeout = 10000L,
      maxRetryAttempts = 3
    )
  }
}

/**
 * 데이터베이스 연결 상태
 */
enum class DatabaseState {
  DISCONNECTED,
  CONNECTING,
  CONNECTED,
  ERROR
}

/**
 * 트랜잭션 격리 수준
 */
enum class IsolationLevel {
  READ_UNCOMMITTED,
  READ_COMMITTED,
  REPEATABLE_READ,
  SERIALIZABLE
}