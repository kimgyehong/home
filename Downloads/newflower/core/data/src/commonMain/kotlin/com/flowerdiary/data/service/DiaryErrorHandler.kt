package com.flowerdiary.data.service

import com.flowerdiary.common.constants.RepositoryConstants
import com.flowerdiary.common.utils.Logger

/**
 * 일기 에러 처리 서비스
 * SRP: Repository 계층의 에러 처리만 담당
 * 모든 데이터 액세스 에러를 Result 객체로 변환
 */
object DiaryErrorHandler {
    
    /**
     * 단일 객체 조회 에러 처리
     */
    suspend fun <T> handleSingleResult(
        operation: suspend () -> T,
        operationName: String
    ): Result<T> {
        return try {
            Result.success(operation())
        } catch (e: Exception) {
            Logger.error(
                RepositoryConstants.LogTags.DIARY_ERROR_HANDLER,
                "${RepositoryConstants.ErrorMessages.QUERY_EXECUTION_ERROR}: $operationName",
                e
            )
            Result.failure(e)
        }
    }
    
    /**
     * 리스트 조회 에러 처리
     */
    suspend fun <T> handleListResult(
        operation: suspend () -> List<T>,
        operationName: String
    ): Result<List<T>> {
        return try {
            Result.success(operation())
        } catch (e: Exception) {
            Logger.error(
                RepositoryConstants.LogTags.DIARY_ERROR_HANDLER,
                "${RepositoryConstants.ErrorMessages.QUERY_EXECUTION_ERROR}: $operationName",
                e
            )
            Result.failure(e)
        }
    }
    
    /**
     * Unit 작업 에러 처리
     */
    suspend fun handleUnitResult(
        operation: suspend () -> Unit,
        operationName: String
    ): Result<Unit> {
        return try {
            operation()
            Result.success(Unit)
        } catch (e: Exception) {
            Logger.error(
                RepositoryConstants.LogTags.DIARY_ERROR_HANDLER,
                "${RepositoryConstants.ErrorMessages.QUERY_EXECUTION_ERROR}: $operationName",
                e
            )
            Result.failure(e)
        }
    }
    
    /**
     * 개수 조회 에러 처리
     */
    suspend fun handleCountResult(
        operation: suspend () -> Long,
        operationName: String
    ): Result<Int> {
        return try {
            Result.success(operation().toInt())
        } catch (e: Exception) {
            Logger.error(
                RepositoryConstants.LogTags.DIARY_ERROR_HANDLER,
                "${RepositoryConstants.ErrorMessages.QUERY_EXECUTION_ERROR}: $operationName",
                e
            )
            Result.failure(e)
        }
    }
}