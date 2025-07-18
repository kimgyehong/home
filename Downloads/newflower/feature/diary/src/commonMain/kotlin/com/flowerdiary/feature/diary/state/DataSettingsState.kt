package com.flowerdiary.feature.diary.state

/**
 * 데이터 관리 설정 상태
 */
data class DataSettingsState(
    val isLoading: Boolean = false,
    val showResetDialog: Boolean = false,
    val error: String? = null
)

/**
 * 데이터 관리 설정 의도
 */
sealed interface DataSettingsIntent {
    data object ClearCache : DataSettingsIntent
    data object ShowResetConfirmation : DataSettingsIntent
    data object ConfirmReset : DataSettingsIntent
    data object CancelReset : DataSettingsIntent
}

/**
 * 데이터 관리 설정 효과
 */
sealed interface DataSettingsEffect {
    data class ShowToast(val message: String) : DataSettingsEffect
    data object RequireRestart : DataSettingsEffect
}
