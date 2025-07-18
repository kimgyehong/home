package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.repository.BirthFlowerRepository
import com.flowerdiary.domain.usecase.flower.GetBirthFlowerUseCase
import com.flowerdiary.domain.usecase.flower.UnlockBirthFlowerUseCase
import com.flowerdiary.feature.diary.state.BaseViewModel
import com.flowerdiary.feature.diary.state.CollectionEffect
import com.flowerdiary.feature.diary.state.CollectionIntent
import com.flowerdiary.feature.diary.state.CollectionState

/**
 * 도감 ViewModel
 * SRP: 도감 화면의 상태 관리와 Intent 처리만 담당
 */
class CollectionViewModel(
    getBirthFlowerUseCase: GetBirthFlowerUseCase,
    unlockBirthFlowerUseCase: UnlockBirthFlowerUseCase,
    birthFlowerRepository: BirthFlowerRepository
) : BaseViewModel<CollectionState, CollectionIntent, CollectionEffect>() {
    
    private val dataLoader = CollectionDataLoader(getBirthFlowerUseCase)
    private val todayFlowerUnlocker = TodayFlowerUnlocker(
        getBirthFlowerUseCase,
        unlockBirthFlowerUseCase
    )
    private val collectionObserver = CollectionObserver(
        birthFlowerRepository,
        viewModelScope
    )
    
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
            is CollectionIntent.RefreshCollection -> refreshCollection()
        }
    }
    
    override fun handleError(throwable: Throwable) {
        updateState {
            copy(
                error = throwable.message ?: "오류가 발생했습니다",
                isLoading = false
            )
        }
    }
    
    private fun loadCollection() {
        updateState { copy(isLoading = true) }
        
        launch {
            dataLoader.loadCollection()
                .onSuccess { result ->
                    updateState {
                        copy(
                            isLoading = false,
                            flowers = result.flowers,
                            unlockedCount = result.unlockedCount,
                            error = null
                        )
                    }
                    checkCompletion(result.unlockedCount)
                }
                .onFailure { error ->
                    handleError(error)
                }
        }
    }
    
    private fun unlockTodayFlower() {
        launch {
            todayFlowerUnlocker.unlockTodayFlower()
                .onSuccess { result ->
                    when (result) {
                        is TodayFlowerUnlocker.TodayFlowerUnlockResult.NewlyUnlocked -> {
                            updateState { copy(todayFlower = result.flower) }
                            sendEffect(CollectionEffect.ShowUnlockAnimation(result.flower))
                            sendEffect(CollectionEffect.ShowToast("오늘의 꽃 '${result.flower.nameKr}'이(가) 해금되었습니다!"))
                            loadCollection()
                        }
                        is TodayFlowerUnlocker.TodayFlowerUnlockResult.AlreadyUnlocked -> {
                            updateState { copy(todayFlower = result.flower) }
                        }
                        is TodayFlowerUnlocker.TodayFlowerUnlockResult.NoFlowerFound -> {
                            // 오늘의 꽃이 없음
                        }
                    }
                }
        }
    }
    
    private fun filterByMonth(month: Int?) {
        updateState { copy(selectedMonth = month) }
    }
    
    private fun viewFlowerDetail(flower: BirthFlower) {
        if (!flower.isUnlocked) {
            sendEffect(CollectionEffect.ShowToast("아직 해금되지 않은 꽃입니다"))
            return
        }
        
        updateState { copy(selectedFlower = flower) }
        sendEffect(CollectionEffect.ShowFlowerDetail(flower))
    }
    
    private fun closeFlowerDetail() {
        updateState { copy(selectedFlower = null) }
    }
    
    private fun refreshCollection() {
        loadCollection()
    }
    
    private fun observeCollectionChanges() {
        collectionObserver.observeUnlockStatus { unlockedCount ->
            updateState { copy(unlockedCount = unlockedCount) }
            checkCompletion(unlockedCount)
        }
    }
    
    private fun checkCompletion(unlockedCount: Int) {
        if (dataLoader.checkCompletion(unlockedCount, currentState.totalCount) && 
            !currentState.isCompleted) {
            sendEffect(CollectionEffect.ShowCompletionCelebration)
            sendEffect(CollectionEffect.ShowToast("축하합니다! 모든 탄생화를 수집하셨습니다!"))
        }
    }
    
    fun shareFlower(flower: BirthFlower) {
        if (!flower.isUnlocked) return
        sendEffect(CollectionEffect.ShareFlower(flower))
    }
}
