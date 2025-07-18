package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.constants.Messages
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.feature.diary.state.*
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 알림 설정 전용 ViewModel
 * SRP: 알림 및 자동 잠금 해제 관련 설정만 담당
 */
class NotificationSettingsViewModel(
    private val preferencesStore: PreferencesStore
) : BaseViewModel<NotificationSettingsState, NotificationSettingsIntent, NotificationSettingsEffect>(
    initialState = NotificationSettingsState()
) {
    
    init {
        loadNotificationSettings()
    }
    
    override fun handleIntent(intent: NotificationSettingsIntent) {
        when (intent) {
            is NotificationSettingsIntent.LoadSettings -> loadNotificationSettings()
            is NotificationSettingsIntent.ToggleNotifications -> toggleNotifications()
            is NotificationSettingsIntent.ToggleAutoUnlock -> toggleAutoUnlock()
        }
    }
    
    private fun loadNotificationSettings() {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                val notificationsEnabled = preferencesStore.getBoolean(Config.PREF_KEY_NOTIFICATIONS, true)
                val autoUnlockEnabled = preferencesStore.getBoolean(Config.PREF_KEY_AUTO_UNLOCK, true)
                
                _state.update {
                    it.copy(
                        isLoading = false,
                        notificationsEnabled = notificationsEnabled,
                        autoUnlockEnabled = autoUnlockEnabled
                    )
                }
            }.onFailure { e ->
                Logger.error(TAG, "Failed to load notification settings", e)
                _state.update { it.copy(isLoading = false, error = Messages.ERROR_SETTINGS_LOAD_FAILED) }
            }
        }
    }
    
    private fun toggleNotifications() {
        viewModelScope.launch {
            val newEnabled = !_state.value.notificationsEnabled
            
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.putBoolean(Config.PREF_KEY_NOTIFICATIONS, newEnabled)
                _state.update { it.copy(notificationsEnabled = newEnabled) }
                
                val message = if (newEnabled) {
                    Messages.SUCCESS_NOTIFICATION_ENABLED
                } else {
                    Messages.SUCCESS_NOTIFICATION_DISABLED
                }
                sendEffect(NotificationSettingsEffect.ShowToast(message))
            }.onFailure { e ->
                Logger.error(TAG, "Failed to toggle notifications", e)
            }
        }
    }
    
    private fun toggleAutoUnlock() {
        viewModelScope.launch {
            val newEnabled = !_state.value.autoUnlockEnabled
            
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.putBoolean(Config.PREF_KEY_AUTO_UNLOCK, newEnabled)
                _state.update { it.copy(autoUnlockEnabled = newEnabled) }
            }.onFailure { e ->
                Logger.error(TAG, "Failed to toggle auto unlock", e)
            }
        }
    }
    
    companion object {
        private const val TAG = "NotificationSettingsViewModel"
    }
}
