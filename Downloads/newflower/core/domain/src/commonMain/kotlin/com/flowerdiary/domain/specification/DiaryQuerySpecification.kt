package com.flowerdiary.domain.specification

import com.flowerdiary.domain.model.Diary

/**
 * 일기 쿼리 명세 인터페이스
 * OCP 준수: 새로운 쿼리 조건 추가 시 기존 코드 수정 없이 확장 가능
 */
sealed interface DiaryQuerySpecification {
    
    /**
     * 명세를 SQL WHERE 절로 변환
     */
    fun toSqlWhereClause(): String
    
    /**
     * 명세를 파라미터로 변환
     */
    fun toParameters(): Map<String, Any>
    
    /**
     * 메모리에서 필터링할 때 사용
     */
    fun isSatisfiedBy(diary: Diary): Boolean
}