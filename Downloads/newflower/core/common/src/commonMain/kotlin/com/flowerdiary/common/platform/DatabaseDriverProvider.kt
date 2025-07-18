package com.flowerdiary.common.platform

/**
 * 플랫폼별 데이터베이스 드라이버 제공자 인터페이스
 * Android와 iOS에서 각각 구현
 * SQLDelight 타입은 data 모듈에서 사용
 * 순수 Kotlin 인터페이스 - platform 모듈에서 구현
 */
interface DatabaseDriverProvider {
    // Generic 타입을 사용하여 SQLDelight 의존성 제거
    fun <T> createDriver(schema: T, name: String): T
}
