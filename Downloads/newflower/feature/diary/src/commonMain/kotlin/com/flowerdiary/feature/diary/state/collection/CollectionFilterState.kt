package com.flowerdiary.feature.diary.state.collection

/**
 * 도감 필터 관련 상태
 * SRP: 도감 필터링과 정렬 관련 상태만 관리
 */
data class CollectionFilterState(
    val selectedMonth: Int? = null,
    val showOnlyUnlocked: Boolean = false,
    val sortBy: SortOption = SortOption.DATE
) {
    /**
     * 필터가 적용되었는지
     */
    val hasActiveFilter: Boolean get() = 
        selectedMonth != null || showOnlyUnlocked
    
    /**
     * 현재 선택된 월 이름
     */
    val selectedMonthName: String? get() = 
        selectedMonth?.let { month ->
            monthNames.getOrNull(month - 1)
        }
    
    companion object {
        private val monthNames = listOf(
            "1월", "2월", "3월", "4월", "5월", "6월",
            "7월", "8월", "9월", "10월", "11월", "12월"
        )
    }
}

/**
 * 정렬 옵션
 */
enum class SortOption(val displayName: String) {
    DATE("날짜순"),
    NAME("이름순"),
    UNLOCK_STATUS("해금 상태순")
}