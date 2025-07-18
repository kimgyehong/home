package com.flowerdiary.domain.service

import com.flowerdiary.domain.model.BirthFlower

/**
 * 탄생화 업데이트 서비스
 * SRP: 탄생화 데이터 업데이트 로직만 담당
 * 불변성 유지를 위해 새로운 인스턴스 반환
 */
object BirthFlowerUpdateService {
    
    /**
     * 탄생화 해금
     */
    fun unlock(birthFlower: BirthFlower): BirthFlower {
        return birthFlower.copy(isUnlocked = true)
    }
    
    /**
     * 탄생화 잠금
     */
    fun lock(birthFlower: BirthFlower): BirthFlower {
        return birthFlower.copy(isUnlocked = false)
    }
    
    /**
     * 탄생화 해금 상태 토글
     */
    fun toggleUnlocked(birthFlower: BirthFlower): BirthFlower {
        return birthFlower.copy(isUnlocked = !birthFlower.isUnlocked)
    }
    
    /**
     * 배경색 변경
     */
    fun updateBackgroundColor(birthFlower: BirthFlower, newColor: Long): BirthFlower {
        return birthFlower.copy(backgroundColor = newColor)
    }
    
    /**
     * 탄생화 정보 업데이트
     */
    fun updateInfo(
        birthFlower: BirthFlower,
        nameKr: String? = null,
        nameEn: String? = null,
        meaning: String? = null,
        description: String? = null,
        imageUrl: String? = null
    ): BirthFlower {
        return birthFlower.copy(
            nameKr = nameKr ?: birthFlower.nameKr,
            nameEn = nameEn ?: birthFlower.nameEn,
            meaning = meaning ?: birthFlower.meaning,
            description = description ?: birthFlower.description,
            imageUrl = imageUrl ?: birthFlower.imageUrl
        )
    }
    
    /**
     * 탄생화 이름 업데이트
     */
    fun updateName(
        birthFlower: BirthFlower,
        nameKr: String? = null,
        nameEn: String? = null
    ): BirthFlower {
        return birthFlower.copy(
            nameKr = nameKr ?: birthFlower.nameKr,
            nameEn = nameEn ?: birthFlower.nameEn
        )
    }
    
    /**
     * 탄생화 의미 업데이트
     */
    fun updateMeaning(
        birthFlower: BirthFlower,
        meaning: String
    ): BirthFlower {
        return birthFlower.copy(meaning = meaning)
    }
    
    /**
     * 탄생화 설명 업데이트
     */
    fun updateDescription(
        birthFlower: BirthFlower,
        description: String
    ): BirthFlower {
        return birthFlower.copy(description = description)
    }
}