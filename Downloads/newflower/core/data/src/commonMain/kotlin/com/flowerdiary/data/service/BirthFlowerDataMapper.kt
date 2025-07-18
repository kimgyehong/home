package com.flowerdiary.data.service

import com.flowerdiary.data.BirthFlower as BirthFlowerDb
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId

/**
 * 탄생화 데이터 매핑 서비스
 * SRP: 데이터베이스 모델과 도메인 모델 간의 변환만 담당
 * 순수 함수로 구성되어 사이드 이펙트 없음
 */
object BirthFlowerDataMapper {
    
    /**
     * 데이터베이스 모델을 도메인 모델로 변환
     */
    fun toDomain(dbBirthFlower: BirthFlowerDb): BirthFlower {
        return BirthFlower(
            id = FlowerId(dbBirthFlower.id.toInt()),
            month = dbBirthFlower.month.toInt(),
            day = dbBirthFlower.day.toInt(),
            nameKr = dbBirthFlower.name_kr,
            nameEn = dbBirthFlower.name_en,
            meaning = dbBirthFlower.meaning,
            description = dbBirthFlower.description,
            imageUrl = dbBirthFlower.image_url,
            backgroundColor = dbBirthFlower.background_color,
            isUnlocked = dbBirthFlower.is_unlocked
        )
    }
    
    /**
     * 도메인 모델을 데이터베이스 삽입 파라미터로 변환
     */
    fun toDbParams(birthFlower: BirthFlower): BirthFlowerInsertParams {
        return BirthFlowerInsertParams(
            id = birthFlower.id.value.toLong(),
            month = birthFlower.month.toLong(),
            day = birthFlower.day.toLong(),
            nameKr = birthFlower.nameKr,
            nameEn = birthFlower.nameEn,
            meaning = birthFlower.meaning,
            description = birthFlower.description,
            imageUrl = birthFlower.imageUrl,
            backgroundColor = birthFlower.backgroundColor,
            isUnlocked = birthFlower.isUnlocked
        )
    }
    
    /**
     * 리스트 변환 헬퍼
     */
    fun toDomainList(dbBirthFlowers: List<BirthFlowerDb>): List<BirthFlower> {
        return dbBirthFlowers.map { toDomain(it) }
    }
}

/**
 * 탄생화 데이터베이스 삽입 파라미터 데이터 클래스
 */
data class BirthFlowerInsertParams(
    val id: Long,
    val month: Long,
    val day: Long,
    val nameKr: String,
    val nameEn: String,
    val meaning: String,
    val description: String,
    val imageUrl: String,
    val backgroundColor: Long,
    val isUnlocked: Boolean
)