package com.flowerdiary.feature.diary.state.collection

import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.feature.diary.common.Percentage
import com.flowerdiary.feature.diary.common.allUnlocked
import com.flowerdiary.feature.diary.common.groupByLockStatus
import com.flowerdiary.feature.diary.common.progressOf
import com.flowerdiary.feature.diary.common.toPercentage

/**
 * 꽃 컬렉션 관련 상태 - Context7 KMP 극한 압축
 * SRP: 꽃 수집과 진행률 관련 상태만 관리
 */
data class FlowerCollectionState(
    val flowers: List<BirthFlower> = emptyList(),
    val todayFlower: BirthFlower? = null,
    val selectedFlower: BirthFlower? = null
) {
    val collectionProgress: Percentage get() = flowers.progressOf { it.isUnlocked }.toPercentage()
    val isCompleted: Boolean get() = flowers.allUnlocked()
    val unlockedCount: Int get() = flowers.count { it.isUnlocked }
    val remainingCount: Int get() = TOTAL_FLOWERS - unlockedCount
    
    val flowersGrouped: Pair<List<BirthFlower>, List<BirthFlower>> by lazy {
        flowers.groupByLockStatus()
    }
    
    val unlockedFlowers: List<BirthFlower> get() = flowersGrouped.first
    val lockedFlowers: List<BirthFlower> get() = flowersGrouped.second
    
    companion object {
        private const val TOTAL_FLOWERS = 365
    }
}