package com.flowerdiary.common.utils

import kotlinx.coroutines.CancellationException

/**
 * 예외 처리 유틸리티 - Generic Exception 방지 및 코루틴 취소 보존
 * SOLID 원칙과 Detekt 규칙 준수를 위한 중앙집중식 예외 처리
 */
object ExceptionUtil {
    
    /**
     * 안전한 suspend 함수 실행 - CancellationException은 재발생
     */
    suspend inline fun <T> runCatchingSuspend(
        crossinline block: suspend () -> T
    ): Result<T> = try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e // 코루틴 취소는 반드시 재발생
    } catch (e: Throwable) {
        Result.failure(e)
    }
    
    /**
     * 안전한 함수 실행
     */
    inline fun <T> runCatching(
        block: () -> T
    ): Result<T> = try {
        Result.success(block())
    } catch (e: Throwable) {
        Result.failure(e)
    }
    
    /**
     * 예외를 로깅하고 기본값 반환
     */
    inline fun <T> runCatchingOrDefault(
        default: T,
        block: () -> T
    ): T = try {
        block()
    } catch (e: Throwable) {
        Logger.error("ExceptionUtil", "Operation failed, returning default", e)
        default
    }
    
    /**
     * 예외를 로깅하고 null 반환
     */
    inline fun <T> runCatchingOrNull(
        block: () -> T
    ): T? = try {
        block()
    } catch (e: Throwable) {
        Logger.error("ExceptionUtil", "Operation failed, returning null", e)
        null
    }
    
    /**
     * 특정 예외 타입만 처리
     */
    inline fun <T, reified E : Throwable> runCatchingSpecific(
        crossinline onError: (E) -> T,
        block: () -> T
    ): T = try {
        block()
    } catch (e: Throwable) {
        when (e) {
            is E -> onError(e)
            else -> throw e
        }
    }
}

/**
 * Result 확장 함수들
 */
inline fun <T> Result<T>.onSuccessOrLog(
    tag: String = "Result",
    crossinline action: (value: T) -> Unit
): Result<T> {
    onSuccess(action)
    onFailure { Logger.error(tag, "Operation failed", it) }
    return this
}

inline fun <T> Result<T>.recoverOrLog(
    tag: String = "Result",
    crossinline recovery: (exception: Throwable) -> T
): T = fold(
    onSuccess = { it },
    onFailure = { 
        Logger.error(tag, "Recovering from failure", it)
        recovery(it)
    }
)
