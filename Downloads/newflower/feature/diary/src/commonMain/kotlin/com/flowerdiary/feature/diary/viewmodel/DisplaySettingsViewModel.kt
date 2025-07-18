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
 * 화면 설정 전용 ViewModel
 * SRP: 폰트 크기, 테마 등 화면 관련 설정만 담당
 */
class DisplaySettingsViewModel(
    private val preferencesStore: PreferencesStore
) : BaseViewModel<DisplaySettingsState, DisplaySettingsIntent, DisplaySettingsEffect>(
    initialState = DisplaySettingsState()
) {
    
    init {
        loadDisplaySettings()
    }
    
    override fun handleIntent(intent: DisplaySettingsIntent) {
        when (intent) {
            is DisplaySettingsIntent.LoadSettings -> loadDisplaySettings()
            is DisplaySettingsIntent.ChangeFontSize -> changeFontSize(intent.level)
            is DisplaySettingsIntent.ChangeTheme -> changeTheme(intent.mode)
        }
    }
    
    private fun loadDisplaySettings() {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                val fontScale = preferencesStore.getFloat(Config.PREF_KEY_FONT_SCALE, 1.0f)
                val themeMode = preferencesStore.getString(Config.PREF_KEY_THEME, ThemeMode.SYSTEM.name)
                
                _state.update {
                    it.copy(
                        isLoading = false,
                        fontSizeScale = fontScale,
                        themeMode = ThemeMode.valueOf(themeMode ?: ThemeMode.SYSTEM.name)
                    )
                }
            }.onFailure { e ->
                Logger.error(TAG, "Failed to load display settings", e)
                _state.update { it.copy(isLoading = false, error = Messages.ERROR_SETTINGS_LOAD_FAILED) }
            }
        }
    }
    
    private fun changeFontSize(level: FontSizeLevel) {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.putFloat(Config.PREF_KEY_FONT_SCALE, level.scale)
                _state.update { it.copy(fontSizeScale = level.scale) }
                sendEffect(DisplaySettingsEffect.ShowToast(Messages.SUCCESS_FONT_SIZE_CHANGED))
            }.onFailure { e ->
                Logger.error(TAG, "Failed to change font size", e)
            }
        }
    }
    
    private fun changeTheme(mode: ThemeMode) {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.putString(Config.PREF_KEY_THEME, mode.name)
                _state.update { it.copy(themeMode = mode) }
                sendEffect(DisplaySettingsEffect.ApplyTheme(mode))
            }.onFailure { e ->
                Logger.error(TAG, "Failed to change theme", e)
            }
        }
    }
    
    companion object {
        private const val TAG = "DisplaySettingsViewModel"
    }
}
