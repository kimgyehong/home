package com.flowerdiary.common.utils

/**
 * 플랫폼 중립적 로깅 인터페이스
 * 순수 Kotlin 인터페이스 - platform 모듈에서 구현
 */
interface Logger {
    fun debug(tag: String, message: String)
    fun info(tag: String, message: String)
    fun warning(tag: String, message: String)
    fun error(tag: String, message: String, throwable: Throwable? = null)
}

/**
 * 로그 레벨 정의
 */
enum class LogLevel {
    DEBUG,
    INFO,
    WARNING,
    ERROR
}
