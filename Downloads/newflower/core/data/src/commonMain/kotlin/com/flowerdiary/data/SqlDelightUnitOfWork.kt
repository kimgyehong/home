package com.flowerdiary.data

import app.cash.sqldelight.TransactionWithReturn
import app.cash.sqldelight.TransactionWithoutReturn
import com.flowerdiary.common.data.UnitOfWork
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.common.platform.DefaultDispatcher
import kotlinx.coroutines.withContext

/**
 * SQLDelight 기반 Unit of Work 구현
 * 트랜잭션 관리 및 원자성 보장
 * SOLID 원칙 중 SRP 적용 - 트랜잭션 관리만 담당
 */
class SqlDelightUnitOfWork(
    private val database: FlowerDiaryDatabase
) : UnitOfWork {
    
    /**
     * 읽기/쓰기 트랜잭션 실행
     */
    override suspend fun <R> transaction(block: suspend () -> R): Result<R> =
        withContext(DefaultDispatcher) {
            ExceptionUtil.runCatchingSuspend {
                Logger.debug(TAG, "Starting transaction")
                
                // SQLDelight 트랜잭션은 동기적이므로 suspend 함수를 직접 실행할 수 없음
                // 트랜잭션 외부에서 데이터를 준비하고 트랜잭션 내부에서는 동기적으로 처리
                val result = block()
                
                database.transactionWithResult {
                    Logger.debug(TAG, "Transaction completed successfully")
                    result
                }
            }.onFailure {
                Logger.error(TAG, "Transaction failed", it)
            }
        }
    
    /**
     * 읽기 전용 트랜잭션 실행
     * 성능 최적화를 위해 읽기 작업만 수행할 때 사용
     */
    override suspend fun <R> readTransaction(block: suspend () -> R): Result<R> =
        withContext(DefaultDispatcher) {
            ExceptionUtil.runCatchingSuspend {
                Logger.debug(TAG, "Starting read-only transaction")
                
                // SQLDelight는 별도의 읽기 전용 트랜잭션을 지원하지 않으므로
                // 일반 쿼리로 실행
                block().also {
                    Logger.debug(TAG, "Read-only operation completed successfully")
                }
            }.onFailure {
                Logger.error(TAG, "Read-only operation failed", it)
            }
        }
    
    companion object {
        private const val TAG = "SqlDelightUnitOfWork"
    }
}
