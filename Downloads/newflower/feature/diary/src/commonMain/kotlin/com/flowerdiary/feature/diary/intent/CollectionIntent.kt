package com.flowerdiary.feature.diary.intent

import com.flowerdiary.domain.model.BirthFlower

/**
 * 도감 UI 이벤트
 * SRP: 사용자 의도만 표현
 */
sealed class CollectionIntent {
    /**
     * 도감 데이터 로드
     */
    data object LoadCollection : CollectionIntent()
    
    /**
     * 오늘의 꽃 해금
     */
    data object UnlockTodayFlower : CollectionIntent()
    
    /**
     * 월 필터 적용
     */
    data class FilterByMonth(val month: Int?) : CollectionIntent()
    
    /**
     * 꽃 상세 보기
     */
    data class ViewFlowerDetail(val flower: BirthFlower) : CollectionIntent()
    
    /**
     * 꽃 상세 닫기
     */
    data object CloseFlowerDetail : CollectionIntent()
    
    /**
     * 도감 새로고침
     */
    data object RefreshCollection : CollectionIntent()
}