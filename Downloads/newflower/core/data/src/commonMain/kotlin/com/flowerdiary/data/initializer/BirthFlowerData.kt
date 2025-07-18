package com.flowerdiary.data.initializer

import kotlinx.serialization.Serializable

/**
 * 탄생화 데이터 모델
 * JSON 파싱 결과를 담는 데이터 클래스
 * SRP: 데이터 구조만 정의
 */
@Serializable
data class BirthFlowerData(
    val nameKr: String,
    val nameEn: String,
    val meaning: String,
    val description: String,
    val imageFileName: String,
    val backgroundColor: String
)

/**
 * 날짜별 탄생화 엔트리
 * SRP: 날짜와 데이터 매핑만 담당
 */
@Serializable
data class BirthFlowerEntry(
    val date: String, // "MM-DD" format
    val nameKr: String,
    val nameEn: String,
    val meaning: String,
    val description: String,
    val imageFileName: String,
    val backgroundColor: String
) {
    val month: Int by lazy { 
        date.substring(0, 2).toIntOrNull() ?: throw IllegalArgumentException("Invalid month in date: $date")
    }
    
    val day: Int by lazy {
        date.substring(3, 5).toIntOrNull() ?: throw IllegalArgumentException("Invalid day in date: $date")
    }
}

/**
 * 탄생화 JSON 루트 객체
 * SRP: JSON 최상위 구조 정의
 */
@Serializable
data class BirthFlowerJson(
    val flowers: List<BirthFlowerEntry>
)
