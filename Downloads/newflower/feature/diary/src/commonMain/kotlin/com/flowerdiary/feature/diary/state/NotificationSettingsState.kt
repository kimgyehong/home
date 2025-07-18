package com.flowerdiary.feature.diary.state

/**
 * 알림 설정 상태
 */
data class NotificationSettingsState(
    val isLoading: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val autoUnlockEnabled: Boolean = true,
    val error: String? = null
)

/**
 * 알림 설정 의도
 */
sealed interface NotificationSettingsIntent {
    data object LoadSettings : NotificationSettingsIntent
    data object ToggleNotifications : NotificationSettingsIntent
    data object ToggleAutoUnlock : NotificationSettingsIntent
}

/**
 * 알림 설정 효과
 */
sealed interface NotificationSettingsEffect {
    data class ShowToast(val message: String) : NotificationSettingsEffect
}
