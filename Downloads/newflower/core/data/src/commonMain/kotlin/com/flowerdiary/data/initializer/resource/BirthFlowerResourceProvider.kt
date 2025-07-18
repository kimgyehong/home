package com.flowerdiary.data.initializer.resource

/**
 * 탄생화 리소스 제공자 인터페이스
 * SRP: 리소스 접근만 담당
 * 순수 Kotlin 인터페이스 - platform 모듈에서 구현
 */
interface BirthFlowerResourceProvider {
    /**
     * 탄생화 JSON 데이터 읽기
     */
    suspend fun readBirthFlowerJson(): Result<String>
}
