package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.domain.usecase.flower.GetBirthFlowerUseCase
import com.flowerdiary.domain.usecase.flower.RecommendFlowerUseCase
import com.flowerdiary.feature.diary.state.*
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 꽃 선택 전용 ViewModel
 * SRP: 꽃 선택, 추천, 오늘의 꽃 관련 기능만 담당
 */
class FlowerSelectionViewModel(
    private val getBirthFlowerUseCase: GetBirthFlowerUseCase,
    private val recommendFlowerUseCase: RecommendFlowerUseCase
) : BaseViewModel<FlowerSelectionState, FlowerSelectionIntent, FlowerSelectionEffect>(
    initialState = FlowerSelectionState()
) {
    
    init {
        loadTodayFlower()
    }
    
    override fun handleIntent(intent: FlowerSelectionIntent) {
        when (intent) {
            is FlowerSelectionIntent.LoadTodayFlower -> loadTodayFlower()
            is FlowerSelectionIntent.SelectFlower -> selectFlower(intent.flower)
            is FlowerSelectionIntent.UseTodayFlower -> useTodayFlower()
            is FlowerSelectionIntent.RecommendFlower -> recommendFlower(intent.content, intent.mood)
        }
    }
    
    private fun loadTodayFlower() {
        viewModelScope.launch {
            val today = DateTimeUtil.getToday()
            
            getBirthFlowerUseCase(today.monthNumber, today.dayOfMonth)
                .onSuccess { flower ->
                    _state.update { it.copy(todayFlower = flower) }
                }
                .onFailure { e ->
                    Logger.error(TAG, "Failed to load today's flower", e)
                }
        }
    }
    
    private fun selectFlower(flower: BirthFlower) {
        _state.update { it.copy(selectedFlower = flower) }
        sendEffect(FlowerSelectionEffect.FlowerSelected(flower))
    }
    
    private fun useTodayFlower() {
        _state.value.todayFlower?.let { flower ->
            selectFlower(flower)
        }
    }
    
    private fun recommendFlower(content: String, mood: String?) {
        viewModelScope.launch {
            _state.update { it.copy(isRecommending = true) }
            
            recommendFlowerUseCase(content, mood)
                .onSuccess { flower ->
                    _state.update { 
                        it.copy(
                            isRecommending = false,
                            recommendedFlower = flower,
                            selectedFlower = flower
                        )
                    }
                    sendEffect(FlowerSelectionEffect.FlowerRecommended(flower))
                }
                .onFailure { e ->
                    Logger.error(TAG, "Failed to recommend flower", e)
                    _state.update { it.copy(isRecommending = false) }
                    sendEffect(FlowerSelectionEffect.ShowError("꽃 추천에 실패했습니다"))
                }
        }
    }
    
    companion object {
        private const val TAG = "FlowerSelectionViewModel"
    }
}
