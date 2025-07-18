package com.flowerdiary.common.constants

/**
 * 앱 전체 설정 상수 - 하드코딩 방지를 위한 중앙집중식 관리
 * SOLID 원칙 중 DIP(의존성 역전 원칙) 적용
 * SRP 준수를 위해 카테고리별로 분리된 Config 파일들을 참조
 * 
 * @Deprecated("Use specific config objects instead")
 * 이 파일은 하위 호환성을 위해 유지되며, 새로운 코드에서는
 * 카테고리별 Config 객체를 직접 사용하세요:
 * - DatabaseConfig: 데이터베이스 관련 설정
 * - TextConfig: 텍스트 관련 설정
 * - UIConfig: UI 관련 설정
 * - ResourceConfig: 리소스 관련 설정
 * - TimeConfig: 시간 관련 설정
 * - PreferencesConfig: Preferences 키 설정
 */
object Config {
    // 데이터베이스 설정 - DatabaseConfig로 이동됨
    const val DB_NAME = DatabaseConfig.DB_NAME
    const val DB_VERSION = DatabaseConfig.DB_VERSION
    const val CACHE_EXPIRY_MINUTES = DatabaseConfig.CACHE_EXPIRY_MINUTES
    const val MAX_CACHE_SIZE = DatabaseConfig.MAX_CACHE_SIZE
    const val DEFAULT_PAGE_SIZE = DatabaseConfig.DEFAULT_PAGE_SIZE
    const val MAX_PAGE_SIZE = DatabaseConfig.MAX_PAGE_SIZE
    
    // 텍스트 설정 - TextConfig로 이동됨
    const val MAX_TITLE_LENGTH = TextConfig.MAX_TITLE_LENGTH
    const val MAX_CONTENT_LENGTH = TextConfig.MAX_CONTENT_LENGTH
    const val DEFAULT_TITLE_PLACEHOLDER = TextConfig.DEFAULT_TITLE_PLACEHOLDER
    
    // UI 설정 - UIConfig로 이동됨
    const val DEFAULT_BACKGROUND_THEME = UIConfig.DEFAULT_BACKGROUND_THEME
    
    // 리소스 설정 - ResourceConfig로 이동됨
    const val TOTAL_BIRTH_FLOWERS = ResourceConfig.TOTAL_BIRTH_FLOWERS
}
