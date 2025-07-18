package com.flowerdiary.domain.specification

/**
 * DiaryQuerySpecification을 위한 DSL 확장 함수
 * 쿼리 조건을 더 읽기 쉽게 조합할 수 있도록 지원
 * 
 * 사용 예시:
 * val query = YearMonthDiaries(2024, 1) and MoodDiaries("HAPPY")
 * val query2 = WeatherDiaries("SUNNY") or WeatherDiaries("CLOUDY")
 * val query3 = AllDiaries.not()
 */

/**
 * AND 연산자
 */
infix fun DiaryQuerySpecification.and(
    other: DiaryQuerySpecification
): DiaryQuerySpecification = AndSpecification(this, other)

/**
 * OR 연산자
 */
infix fun DiaryQuerySpecification.or(
    other: DiaryQuerySpecification
): DiaryQuerySpecification = OrSpecification(this, other)

/**
 * NOT 연산자
 */
fun DiaryQuerySpecification.not(): DiaryQuerySpecification = 
    NotSpecification(this)