package com.flowerdiary.domain.specification

import com.flowerdiary.domain.model.Diary

/**
 * 복합 조건 - AND
 * Specification 패턴을 활용한 복합 조건 구성
 */
data class AndSpecification(
    val left: DiaryQuerySpecification,
    val right: DiaryQuerySpecification
) : DiaryQuerySpecification {
    
    override fun toSqlWhereClause(): String =
        "(${left.toSqlWhereClause()}) AND (${right.toSqlWhereClause()})"
    
    override fun toParameters(): Map<String, Any> =
        left.toParameters() + right.toParameters()
    
    override fun isSatisfiedBy(diary: Diary): Boolean =
        left.isSatisfiedBy(diary) && right.isSatisfiedBy(diary)
}

/**
 * 복합 조건 - OR
 */
data class OrSpecification(
    val left: DiaryQuerySpecification,
    val right: DiaryQuerySpecification
) : DiaryQuerySpecification {
    
    override fun toSqlWhereClause(): String =
        "(${left.toSqlWhereClause()}) OR (${right.toSqlWhereClause()})"
    
    override fun toParameters(): Map<String, Any> =
        left.toParameters() + right.toParameters()
    
    override fun isSatisfiedBy(diary: Diary): Boolean =
        left.isSatisfiedBy(diary) || right.isSatisfiedBy(diary)
}

/**
 * 부정 조건 - NOT
 */
data class NotSpecification(
    val specification: DiaryQuerySpecification
) : DiaryQuerySpecification {
    
    override fun toSqlWhereClause(): String =
        "NOT (${specification.toSqlWhereClause()})"
    
    override fun toParameters(): Map<String, Any> =
        specification.toParameters()
    
    override fun isSatisfiedBy(diary: Diary): Boolean =
        !specification.isSatisfiedBy(diary)
}