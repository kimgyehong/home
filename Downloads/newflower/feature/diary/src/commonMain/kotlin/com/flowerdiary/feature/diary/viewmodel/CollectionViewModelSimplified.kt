package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.domain.repository.BirthFlowerRepository
import com.flowerdiary.feature.diary.manager.CollectionDataManager
import com.flowerdiary.feature.diary.manager.CollectionStateManager
import com.flowerdiary.feature.diary.manager.FlowerUnlockManager
import com.flowerdiary.feature.diary.state.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * 도감 ViewModel (간소화 버전)
 * SRP: Intent 처리 및 매니저들에게 작업 위임만 담당
 * 모든 실제 로직은 각 매니저에게 위임됨
 */
class CollectionViewModelSimplified(
    private val dataManager: CollectionDataManager,
    private val unlockManager: FlowerUnlockManager,
    private val stateManager: CollectionStateManager,
    private val birthFlowerRepository: BirthFlowerRepository
) : BaseViewModel<CollectionState, CollectionIntent, CollectionEffect>() {
    
    init {
        processIntent(CollectionIntent.LoadCollection)
        processIntent(CollectionIntent.UnlockTodayFlower)
        observeCollectionChanges()
    }
    
    override fun createInitialState(): CollectionState = CollectionState()
    
    override fun processIntent(intent: CollectionIntent) {
        when (intent) {
            is CollectionIntent.LoadCollection -> loadCollection()
            is CollectionIntent.UnlockTodayFlower -> unlockTodayFlower()
            is CollectionIntent.FilterByMonth -> filterByMonth(intent.month)
            is CollectionIntent.ViewFlowerDetail -> viewFlowerDetail(intent.flower)
            is CollectionIntent.CloseFlowerDetail -> closeFlowerDetail()
            is CollectionIntent.RefreshCollection -> loadCollection()
        }
    }
    
    override fun handleError(throwable: Throwable) {
        val errorState = stateManager.createErrorState(
            throwable.message ?: "오류가 발생했습니다"
        )
        updateState {
            copy(
                error = errorState["error"] as? String,
                isLoading = errorState["isLoading"] as Boolean
            )
        }
    }
    
    private fun loadCollection() {
        updateState { copy(isLoading = true) }
        
        launch {
            dataManager.loadAllFlowers()
                .onSuccess { flowers ->
                    val unlockedCount = dataManager.countUnlockedFlowers(flowers)
                    updateState {
                        copy(
                            isLoading = false,
                            flowers = flowers,
                            unlockedCount = unlockedCount,
                            error = null
                        )
                    }
                    checkCompletion(unlockedCount)
                }
                .onFailure { handleError(it) }
        }
    }
    
    private fun unlockTodayFlower() {
        launch {
            val today = DateTimeUtil.getCurrentDate()
            dataManager.getFlowerByDate(today.month, today.day)
                .onSuccess { flower ->
                    if (flower != null) {
                        updateState { copy(todayFlower = flower) }
                        if (unlockManager.canUnlockFlower(flower)) {
                            unlockManager.unlockTodayFlower()
                                .onSuccess {
                                    sendEffect(CollectionEffect.ShowUnlockAnimation(flower))
                                    sendEffect(CollectionEffect.ShowToast("오늘의 꽃 '${flower.nameKr}'이(가) 해금되었습니다!"))
                                    loadCollection()
                                }
                        }
                    }
                }
        }
    }
    
    private fun filterByMonth(month: Int?) {
        updateState { copy(selectedMonth = month) }
    }
    
    private fun viewFlowerDetail(flower: com.flowerdiary.domain.model.BirthFlower) {
        if (!stateManager.canViewFlowerDetail(flower)) {
            sendEffect(CollectionEffect.ShowToast("아직 해금되지 않은 꽃입니다"))
            return
        }
        updateState { copy(selectedFlower = flower) }
        sendEffect(CollectionEffect.ShowFlowerDetail(flower))
    }
    
    private fun closeFlowerDetail() {
        updateState { copy(selectedFlower = null) }
    }
    
    private fun observeCollectionChanges() {
        birthFlowerRepository.observeUnlockStatus()
            .onEach { unlockedCount ->
                updateState { copy(unlockedCount = unlockedCount) }
                checkCompletion(unlockedCount)
            }
            .launchIn(launch { })
    }
    
    private fun checkCompletion(unlockedCount: Int) {
        if (stateManager.shouldShowCompletion(unlockedCount, currentState.totalCount, currentState.isCompleted)) {
            sendEffect(CollectionEffect.ShowCompletionCelebration)
            sendEffect(CollectionEffect.ShowToast("축하합니다! 모든 탄생화를 수집하셨습니다!"))
        }
    }
}