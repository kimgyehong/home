package com.flowerdiary.feature.diary.state.content

import com.flowerdiary.domain.model.BirthFlower

/**
 * 일기 꽃 관련 상태 - Context7 KMP 극한 압축
 * SRP: 꽃 선택과 추천 관련 상태만 관리
 */
data class DiaryFlowerState(
    val selectedFlower: BirthFlower? = null,
    val todayFlower: BirthFlower? = null,
    val recommendedFlowers: List<BirthFlower> = emptyList()
) {
    val hasSelectedFlower: Boolean get() = selectedFlower != null
    val canUseTodayFlower: Boolean get() = todayFlower != null
    val hasRecommendations: Boolean get() = recommendedFlowers.isNotEmpty()
}