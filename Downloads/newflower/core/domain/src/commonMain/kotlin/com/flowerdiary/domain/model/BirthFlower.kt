package com.flowerdiary.domain.model

/**
 * 순수한 탄생화 데이터 클래스
 * SRP: 탄생화 데이터 저장만 담당
 * 모든 비즈니스 로직은 별도 서비스로 분리
 * 불변성 보장을 위한 data class 사용
 */
data class BirthFlower(
    val id: FlowerId,
    val month: Int,
    val day: Int,
    val nameKr: String,
    val nameEn: String,
    val meaning: String,
    val description: String,
    val imageUrl: String,
    val backgroundColor: Long,
    val isUnlocked: Boolean = false
)