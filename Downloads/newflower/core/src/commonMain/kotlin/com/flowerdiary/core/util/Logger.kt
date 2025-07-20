package com.flowerdiary.core.util

/**
 * 로깅 유틸리티
 * 플랫폼별 로깅 구현을 위한 인터페이스
 */
interface Logger {
  
  /**
   * 디버그 로그
   */
  fun debug(tag: String, message: String)
  
  /**
   * 정보 로그
   */
  fun info(tag: String, message: String)
  
  /**
   * 경고 로그
   */
  fun warn(tag: String, message: String)
  
  /**
   * 에러 로그
   */
  fun error(tag: String, message: String, throwable: Throwable? = null)
}

/**
 * 기본 로거 구현
 * 콘솔 출력용
 */
class ConsoleLogger : Logger {
  
  override fun debug(tag: String, message: String) {
    println("DEBUG [$tag]: $message")
  }
  
  override fun info(tag: String, message: String) {
    println("INFO [$tag]: $message")
  }
  
  override fun warn(tag: String, message: String) {
    println("WARN [$tag]: $message")
  }
  
  override fun error(tag: String, message: String, throwable: Throwable?) {
    println("ERROR [$tag]: $message")
    throwable?.let { 
      println("ERROR [$tag]: ${it.stackTraceToString()}")
    }
  }
}

/**
 * 로거 팩토리
 */
object LoggerFactory {
  private var logger: Logger = ConsoleLogger()
  
  /**
   * 로거 설정
   */
  fun setLogger(newLogger: Logger) {
    logger = newLogger
  }
  
  /**
   * 로거 조회
   */
  fun getLogger(): Logger = logger
}

/**
 * 로거 확장 함수들
 */
fun Logger.d(tag: String, message: String) = debug(tag, message)
fun Logger.i(tag: String, message: String) = info(tag, message)
fun Logger.w(tag: String, message: String) = warn(tag, message)
fun Logger.e(tag: String, message: String, throwable: Throwable? = null) = 
  error(tag, message, throwable)

/**
 * 전역 로거 함수들
 */
fun logDebug(tag: String, message: String) = LoggerFactory.getLogger().debug(tag, message)
fun logInfo(tag: String, message: String) = LoggerFactory.getLogger().info(tag, message)
fun logWarn(tag: String, message: String) = LoggerFactory.getLogger().warn(tag, message)
fun logError(tag: String, message: String, throwable: Throwable? = null) = 
  LoggerFactory.getLogger().error(tag, message, throwable)