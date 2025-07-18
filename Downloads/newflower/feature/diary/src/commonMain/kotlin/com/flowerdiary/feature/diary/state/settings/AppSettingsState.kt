package com.flowerdiary.feature.diary.state.settings

/**
 * 앱 설정 관련 상태
 * SRP: 알림, 자동 해금 등 앱 기능 설정만 관리
 */
data class AppSettingsState(
    val notificationsEnabled: Boolean = true,
    val autoUnlockEnabled: Boolean = true,
    val language: String = "ko",
    val dataBackupEnabled: Boolean = false,
    val analyticsEnabled: Boolean = false
) {
    /**
     * 모든 알림이 비활성화되었는지
     */
    val allNotificationsDisabled: Boolean get() = !notificationsEnabled
    
    /**
     * 백업 설정이 활성화되었는지
     */
    val isBackupActive: Boolean get() = dataBackupEnabled
    
    /**
     * 현재 언어 표시 이름
     */
    val languageDisplayName: String get() = when (language) {
        "ko" -> "한국어"
        "en" -> "English"
        else -> language
    }
    
    companion object {
        val SUPPORTED_LANGUAGES = listOf("ko", "en")
    }
}