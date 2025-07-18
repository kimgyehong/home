package com.flowerdiary.common.constants

/**
 * 데이터베이스 관련 설정 상수
 * SRP: 데이터베이스 설정만 담당
 */
object DatabaseConfig {
    const val DB_NAME = "flower_diary.db"
    const val DB_VERSION = 1
    
    // 캐시 설정
    const val CACHE_EXPIRY_MINUTES = 15L
    const val MAX_CACHE_SIZE = 50
    
    // 페이징 설정
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_PAGE_SIZE = 100
}