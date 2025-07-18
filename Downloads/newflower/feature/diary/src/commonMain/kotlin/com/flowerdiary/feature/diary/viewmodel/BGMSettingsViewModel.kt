package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.constants.Messages
import com.flowerdiary.common.platform.AudioManager
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.common.utils.ExceptionUtil
import com.flowerdiary.common.utils.logDebug
import com.flowerdiary.common.utils.logError
import com.flowerdiary.feature.diary.state.*
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * BGM 설정 전용 ViewModel
 * SRP: BGM 관련 설정만 담당
 */
class BGMSettingsViewModel(
    private val preferencesStore: PreferencesStore,
    private val audioManager: AudioManager
) : BaseViewModel<BGMSettingsState, BGMSettingsIntent, BGMSettingsEffect>(
    initialState = BGMSettingsState()
) {
    
    init {
        loadBGMSettings()
    }
    
    override fun handleIntent(intent: BGMSettingsIntent) {
        when (intent) {
            is BGMSettingsIntent.LoadSettings -> loadBGMSettings()
            is BGMSettingsIntent.ToggleBGM -> toggleBGM()
            is BGMSettingsIntent.ChangeVolume -> changeVolume(intent.volume)
            is BGMSettingsIntent.ChangeTrack -> changeTrack(intent.trackIndex)
        }
    }
    
    private fun loadBGMSettings() {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                val enabled = preferencesStore.getBoolean(Config.PREF_KEY_BGM_ENABLED, true)
                val volume = preferencesStore.getFloat(Config.PREF_KEY_BGM_VOLUME, Config.BGM_VOLUME_DEFAULT)
                val trackIndex = preferencesStore.getInt(Config.PREF_KEY_BGM_TRACK, 0)
                
                _state.update {
                    it.copy(
                        isLoading = false,
                        enabled = enabled,
                        volume = volume,
                        trackIndex = trackIndex
                    )
                }
                
                if (enabled) {
                    sendEffect(BGMSettingsEffect.PlayBGM(trackIndex))
                    sendEffect(BGMSettingsEffect.SetVolume(volume))
                }
            }.onFailure { e ->
                logError("Failed to load BGM settings", e)
                _state.update { it.copy(isLoading = false, error = Messages.ERROR_SETTINGS_LOAD_FAILED) }
            }
        }
    }
    
    private fun toggleBGM() {
        viewModelScope.launch {
            val newEnabled = !_state.value.enabled
            
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.putBoolean(Config.PREF_KEY_BGM_ENABLED, newEnabled)
                _state.update { it.copy(enabled = newEnabled) }
                
                if (newEnabled) {
                    sendEffect(BGMSettingsEffect.PlayBGM(_state.value.trackIndex))
                } else {
                    sendEffect(BGMSettingsEffect.StopBGM)
                }
            }.onFailure { e ->
                logError("Failed to toggle BGM", e)
                _state.update { it.copy(error = Messages.ERROR_BGM_CHANGE_FAILED) }
            }
        }
    }
    
    private fun changeVolume(volume: Float) {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.putFloat(Config.PREF_KEY_BGM_VOLUME, volume)
                _state.update { it.copy(volume = volume) }
                sendEffect(BGMSettingsEffect.SetVolume(volume))
            }.onFailure { e ->
                logError("Failed to change volume", e)
                _state.update { it.copy(error = Messages.ERROR_BGM_CHANGE_FAILED) }
            }
        }
    }
    
    private fun changeTrack(trackIndex: Int) {
        viewModelScope.launch {
            ExceptionUtil.runCatchingSuspend {
                preferencesStore.putInt(Config.PREF_KEY_BGM_TRACK, trackIndex)
                _state.update { it.copy(trackIndex = trackIndex) }
                if (_state.value.enabled) {
                    sendEffect(BGMSettingsEffect.PlayBGM(trackIndex))
                }
            }.onFailure { e ->
                logError("Failed to change track", e)
                _state.update { it.copy(error = Messages.ERROR_BGM_CHANGE_FAILED) }
            }
        }
    }
}
