package com.flowerdiary.feature.diary.state

import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.feature.diary.state.collection.CollectionFilterState
import com.flowerdiary.feature.diary.state.collection.FlowerCollectionState

/**
 * 도감 화면 전체 상태
 * Composition 패턴으로 관련 상태들을 조합
 * SRP: 각 하위 상태는 독립적인 책임을 가짐
 */
data class CollectionState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val collection: FlowerCollectionState = FlowerCollectionState(),
    val filter: CollectionFilterState = CollectionFilterState()
) {
    /**
     * 필터링된 꽃 목록
     */
    val filteredFlowers: List<BirthFlower> get() = 
        collection.flowers
            .filter { flower ->
                // 월 필터 적용
                if (filter.selectedMonth != null && flower.month != filter.selectedMonth) {
                    return@filter false
                }
                // 해금 상태 필터 적용
                if (filter.showOnlyUnlocked && !flower.isUnlocked) {
                    return@filter false
                }
                true
            }
            .sortedBy { flower ->
                // 정렬 옵션 적용
                when (filter.sortBy) {
                    com.flowerdiary.feature.diary.state.collection.SortOption.DATE -> 
                        flower.month * 100 + flower.day
                    com.flowerdiary.feature.diary.state.collection.SortOption.NAME -> 
                        flower.name
                    com.flowerdiary.feature.diary.state.collection.SortOption.UNLOCK_STATUS -> 
                        if (flower.isUnlocked) 0 else 1
                }
            }
    
    /**
     * 월별로 그룹화된 꽃 목록
     */
    val flowersByMonth: Map<Int, List<BirthFlower>> get() = 
        filteredFlowers.groupBy { it.month }
    
    /**
     * 수집 진행률 (위임)
     */
    val collectionProgress: Float get() = collection.collectionProgress
    
    /**
     * 모든 꽃 수집 완료 여부 (위임)
     */
    val isCompleted: Boolean get() = collection.isCompleted
}