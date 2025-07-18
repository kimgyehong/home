package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.feature.diary.state.*
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 일기 스타일 설정 전용 ViewModel
 * SRP: 폰트, 색상, 배경 테마 등 일기 스타일 관련 기능만 담당
 */
class DiaryStyleViewModel(
    private val preferencesStore: PreferencesStore
) : BaseViewModel<DiaryStyleState, DiaryStyleIntent, DiaryStyleEffect>(
    initialState = DiaryStyleState()
) {
    
    init {
        loadSavedStyles()
    }
    
    override fun handleIntent(intent: DiaryStyleIntent) {
        when (intent) {
            is DiaryStyleIntent.LoadStyles -> loadSavedStyles()
            is DiaryStyleIntent.ChangeFontFamily -> changeFontFamily(intent.fontFamily)
            is DiaryStyleIntent.ChangeFontColor -> changeFontColor(intent.color)
            is DiaryStyleIntent.ChangeBackgroundTheme -> changeBackgroundTheme(intent.theme)
            is DiaryStyleIntent.ResetToDefault -> resetToDefault()
        }
    }
    
    private fun loadSavedStyles() {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                val fontFamily = preferencesStore.getString(Config.PREF_KEY_FONT_FAMILY) 
                    ?: Config.DEFAULT_FONT_FAMILY
                val fontColor = preferencesStore.getInt(Config.PREF_KEY_FONT_COLOR, 0xFF000000.toInt())
                val backgroundTheme = preferencesStore.getString(Config.PREF_KEY_BACKGROUND_THEME) 
                    ?: Config.DEFAULT_BACKGROUND_THEME
                
                _state.update {
                    it.copy(
                        fontFamily = fontFamily,
                        fontColor = fontColor,
                        backgroundTheme = backgroundTheme
                    )
                }
            }.onFailure { e ->
                Logger.error(TAG, "Failed to load saved styles", e)
            }
        }
    }
    
    private fun changeFontFamily(fontFamily: String) {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.putString(Config.PREF_KEY_FONT_FAMILY, fontFamily)
                _state.update { it.copy(fontFamily = fontFamily) }
                sendEffect(DiaryStyleEffect.StyleChanged)
            }.onFailure { e ->
                Logger.error(TAG, "Failed to change font family", e)
            }
        }
    }
    
    private fun changeFontColor(color: Int) {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.putInt(Config.PREF_KEY_FONT_COLOR, color)
                _state.update { it.copy(fontColor = color) }
                sendEffect(DiaryStyleEffect.StyleChanged)
            }.onFailure { e ->
                Logger.error(TAG, "Failed to change font color", e)
            }
        }
    }
    
    private fun changeBackgroundTheme(theme: String) {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.putString(Config.PREF_KEY_BACKGROUND_THEME, theme)
                _state.update { it.copy(backgroundTheme = theme) }
                sendEffect(DiaryStyleEffect.StyleChanged)
            }.onFailure { e ->
                Logger.error(TAG, "Failed to change background theme", e)
            }
        }
    }
    
    private fun resetToDefault() {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.remove(Config.PREF_KEY_FONT_FAMILY)
                preferencesStore.remove(Config.PREF_KEY_FONT_COLOR)
                preferencesStore.remove(Config.PREF_KEY_BACKGROUND_THEME)
                
                _state.update {
                    it.copy(
                        fontFamily = Config.DEFAULT_FONT_FAMILY,
                        fontColor = 0xFF000000.toInt(),
                        backgroundTheme = Config.DEFAULT_BACKGROUND_THEME
                    )
                }
                sendEffect(DiaryStyleEffect.StyleChanged)
            }.onFailure { e ->
                Logger.error(TAG, "Failed to reset styles", e)
            }
        }
    }
    
    companion object {
        private const val TAG = "DiaryStyleViewModel"
    }
}
