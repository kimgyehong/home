package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.constants.Messages
import com.flowerdiary.common.platform.FileStore
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.feature.diary.state.*
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 데이터 관리 전용 ViewModel
 * SRP: 캐시 정리, 데이터 초기화 등 데이터 관리 기능만 담당
 */
class DataSettingsViewModel(
    private val preferencesStore: PreferencesStore,
    private val fileStore: FileStore
) : BaseViewModel<DataSettingsState, DataSettingsIntent, DataSettingsEffect>(
    initialState = DataSettingsState()
) {
    
    override fun handleIntent(intent: DataSettingsIntent) {
        when (intent) {
            is DataSettingsIntent.ClearCache -> clearCache()
            is DataSettingsIntent.ShowResetConfirmation -> showResetConfirmation()
            is DataSettingsIntent.ConfirmReset -> resetData()
            is DataSettingsIntent.CancelReset -> cancelReset()
        }
    }
    
    private fun clearCache() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            ExceptionUtil.runCatchingSuspend {
                // 캐시 정리 로직
                fileStore.clearCache()
                _state.update { it.copy(isLoading = false) }
                sendEffect(DataSettingsEffect.ShowToast(Messages.SUCCESS_CACHE_DELETED))
            }.onFailure { e ->
                Logger.error(TAG, "Failed to clear cache", e)
                _state.update { it.copy(isLoading = false, error = Messages.ERROR_CACHE_DELETE_FAILED) }
            }
        }
    }
    
    private fun showResetConfirmation() {
        _state.update { it.copy(showResetDialog = true) }
    }
    
    private fun cancelReset() {
        _state.update { it.copy(showResetDialog = false) }
    }
    
    private fun resetData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, showResetDialog = false) }
            
            ExceptionUtil.runCatchingSuspend {
                // 모든 데이터 초기화
                preferencesStore.clear()
                fileStore.clearAll()
                
                _state.update { it.copy(isLoading = false) }
                sendEffect(DataSettingsEffect.RequireRestart)
            }.onFailure { e ->
                Logger.error(TAG, "Failed to reset data", e)
                _state.update { it.copy(isLoading = false, error = Messages.ERROR_DATA_RESET_FAILED) }
            }
        }
    }
    
    companion object {
        private const val TAG = "DataSettingsViewModel"
    }
}
