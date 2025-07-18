package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.constants.Config
import com.flowerdiary.domain.model.BirthFlower

/**
 * 도감 상태 관리 매니저
 * SRP: 도감 화면의 상태 변경 로직만 담당
 */
class CollectionStateManager {
    
    /**
     * 로딩 상태 생성
     */
    fun createLoadingState(): Map<String, Any> = mapOf(
        "isLoading" to true,
        "error" to null
    )
    
    /**
     * 성공 상태 생성
     */
    fun createSuccessState(
        flowers: List<BirthFlower>,
        unlockedCount: Int
    ): Map<String, Any> = mapOf(
        "isLoading" to false,
        "flowers" to flowers,
        "unlockedCount" to unlockedCount,
        "error" to null
    )
    
    /**
     * 에러 상태 생성
     */
    fun createErrorState(errorMessage: String): Map<String, Any> = mapOf(
        "isLoading" to false,
        "error" to errorMessage
    )
    
    /**
     * 완료 상태 확인
     */
    fun shouldShowCompletion(
        unlockedCount: Int,
        totalCount: Int,
        isAlreadyCompleted: Boolean
    ): Boolean {
        return unlockedCount >= totalCount && !isAlreadyCompleted
    }
    
    /**
     * 꽃 상세 보기 가능 여부
     */
    fun canViewFlowerDetail(flower: BirthFlower): Boolean {
        return flower.isUnlocked
    }
    
    /**
     * 진행률 계산
     */
    fun calculateProgress(unlockedCount: Int, totalCount: Int): Float {
        return if (totalCount > 0) {
            (unlockedCount.toFloat() / totalCount) * Config.PERCENTAGE_MULTIPLIER
        } else {
            Config.PROGRESS_MIN
        }
    }
}