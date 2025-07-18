package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.utils.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * 플랫폼 중립적 ViewModel 기본 클래스
 * SRP: 상태 관리와 코루틴 스코프 관리만 담당
 * Android의 ViewModel에 의존하지 않음
 */
abstract class BaseViewModel<State, Intent, Effect> {
    
    private val viewModelScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )
    
    /**
     * UI 상태 (StateFlow로 hot stream 제공)
     */
    private val _state = MutableStateFlow(createInitialState())
    val state: StateFlow<State> = _state.asStateFlow()
    
    /**
     * 현재 상태값
     */
    protected val currentState: State
        get() = _state.value
    
    /**
     * 부수효과 (SharedFlow로 일회성 이벤트 처리)
     */
    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()
    
    /**
     * 초기 상태 생성
     */
    protected abstract fun createInitialState(): State
    
    /**
     * Intent 처리
     */
    abstract fun processIntent(intent: Intent)
    
    /**
     * 상태 업데이트
     */
    protected fun updateState(reducer: State.() -> State) {
        val newState = currentState.reducer()
        _state.value = newState
        Logger.debug(TAG, "State updated: ${newState::class.simpleName}")
    }
    
    /**
     * 부수효과 발생
     */
    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
            Logger.debug(TAG, "Effect sent: ${effect::class.simpleName}")
        }
    }
    
    /**
     * 코루틴 실행
     */
    protected fun launch(
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(
        context = CoroutineExceptionHandler { _, throwable ->
            Logger.error(TAG, "Coroutine exception", throwable)
            handleError(throwable)
        },
        block = block
    )
    
    /**
     * 에러 처리 (하위 클래스에서 구현)
     */
    protected abstract fun handleError(throwable: Throwable)
    
    /**
     * ViewModel 정리
     */
    fun onCleared() {
        viewModelScope.cancel()
        Logger.debug(TAG, "ViewModel cleared")
    }
    
    companion object {
        private const val TAG = "BaseViewModel"
    }
}
