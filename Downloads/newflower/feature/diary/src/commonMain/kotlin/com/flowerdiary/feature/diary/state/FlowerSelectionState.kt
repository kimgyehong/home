package com.flowerdiary.feature.diary.state

import com.flowerdiary.domain.model.BirthFlower

/**
 * 꽃 선택 상태
 */
data class FlowerSelectionState(
    val todayFlower: BirthFlower? = null,
    val selectedFlower: BirthFlower? = null,
    val recommendedFlower: BirthFlower? = null,
    val isRecommending: Boolean = false
)

/**
 * 꽃 선택 의도
 */
sealed interface FlowerSelectionIntent {
    data object LoadTodayFlower : FlowerSelectionIntent
    data class SelectFlower(val flower: BirthFlower) : FlowerSelectionIntent
    data object UseTodayFlower : FlowerSelectionIntent
    data class RecommendFlower(val content: String, val mood: String?) : FlowerSelectionIntent
}

/**
 * 꽃 선택 효과
 */
sealed interface FlowerSelectionEffect {
    data class FlowerSelected(val flower: BirthFlower) : FlowerSelectionEffect
    data class FlowerRecommended(val flower: BirthFlower) : FlowerSelectionEffect
    data class ShowError(val message: String) : FlowerSelectionEffect
}
