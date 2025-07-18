package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.domain.repository.BirthFlowerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * 도감 변경사항 관찰자
 * SRP: 도감 상태 변경사항 관찰만 담당
 */
internal class CollectionObserver(
    private val birthFlowerRepository: BirthFlowerRepository,
    private val coroutineScope: CoroutineScope
) {
    
    /**
     * 도감 해금 상태 변경 관찰
     */
    fun observeUnlockStatus(
        onUnlockCountChanged: (Int) -> Unit
    ) {
        birthFlowerRepository.observeUnlockStatus()
            .onEach { unlockedCount ->
                onUnlockCountChanged(unlockedCount)
            }
            .launchIn(coroutineScope)
    }
    
    /**
     * 도감 변경사항 관찰 (일반적인 Flow 반환)
     */
    fun getUnlockStatusFlow(): Flow<Int> {
        return birthFlowerRepository.observeUnlockStatus()
    }
}
