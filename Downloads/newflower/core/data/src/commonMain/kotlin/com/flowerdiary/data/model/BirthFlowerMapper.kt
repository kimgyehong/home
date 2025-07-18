package com.flowerdiary.data.model

import com.flowerdiary.data.BirthFlower as DbBirthFlower
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.model.FlowerId

/**
 * 탄생화 데이터 매퍼
 * DB 엔티티와 도메인 엔티티 간 변환
 * SRP: 데이터 변환 로직만 담당
 */
object BirthFlowerMapper {
    
    /**
     * DB 엔티티를 도메인 엔티티로 변환
     */
    fun DbBirthFlower.toDomain(): BirthFlower = BirthFlower(
        id = FlowerId(id.toInt()),
        month = month.toInt(),
        day = day.toInt(),
        nameKr = name_kr,
        nameEn = name_en,
        meaning = meaning,
        description = description,
        imageUrl = image_url,
        backgroundColor = background_color,
        isUnlocked = is_unlocked == 1L
    )
    
    /**
     * 도메인 엔티티를 DB 파라미터로 변환
     * insertFlower 쿼리에 사용
     */
    fun BirthFlower.toDbParams(): Array<Any?> = arrayOf(
        id.value.toLong(),
        month.toLong(),
        day.toLong(),
        nameKr,
        nameEn,
        meaning,
        description,
        imageUrl,
        backgroundColor,
        if (isUnlocked) 1L else 0L
    )
}
