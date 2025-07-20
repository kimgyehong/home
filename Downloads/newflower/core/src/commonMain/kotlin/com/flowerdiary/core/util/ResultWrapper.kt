package com.flowerdiary.core.util

import kotlinx.serialization.Serializable

/**
 * 작업 결과를 안전하게 감싸는 래퍼 클래스
 * 성공과 실패를 명시적으로 처리
 */
@Serializable
sealed class ResultWrapper<out T> {
  
  /**
   * 성공 결과
   */
  @Serializable
  data class Success<T>(val data: T) : ResultWrapper<T>()
  
  /**
   * 실패 결과
   */
  @Serializable
  data class Error(val message: String, val code: String? = null) : ResultWrapper<Nothing>()
  
  /**
   * 성공 여부 확인
   */
  val isSuccess: Boolean get() = this is Success
  
  /**
   * 실패 여부 확인
   */
  val isError: Boolean get() = this is Error
  
  /**
   * 성공 데이터 조회 (실패 시 null)
   */
  fun getOrNull(): T? = when (this) {
    is Success -> data
    is Error -> null
  }
  
  /**
   * 성공 데이터 조회 (실패 시 기본값)
   */
  fun getOrDefault(default: T): T = when (this) {
    is Success -> data
    is Error -> default
  }
  
  /**
   * 결과 변환
   */
  inline fun <R> map(transform: (T) -> R): ResultWrapper<R> = when (this) {
    is Success -> Success(transform(data))
    is Error -> this
  }
  
  /**
   * 성공 시 작업 실행
   */
  inline fun onSuccess(action: (T) -> Unit): ResultWrapper<T> {
    if (this is Success) action(data)
    return this
  }
  
  /**
   * 실패 시 작업 실행
   */
  inline fun onError(action: (String) -> Unit): ResultWrapper<T> {
    if (this is Error) action(message)
    return this
  }
  
  companion object {
    /**
     * 성공 결과 생성
     */
    fun <T> success(data: T): ResultWrapper<T> = Success(data)
    
    /**
     * 실패 결과 생성
     */
    fun error(message: String, code: String? = null): ResultWrapper<Nothing> = Error(message, code)
    
    /**
     * 예외를 실패 결과로 변환
     */
    fun fromException(exception: Throwable): ResultWrapper<Nothing> = 
      Error(exception.message ?: "Unknown error", exception::class.simpleName)
  }
}