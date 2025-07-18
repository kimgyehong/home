package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.constants.Messages
import com.flowerdiary.feature.diary.manager.*
import com.flowerdiary.feature.diary.state.*

/** 간소화된 설정 ViewModel - Intent 처리와 매니저 조정만 담당 */
class SettingsViewModelSimplified(
    private val bgmManager: BGMSettingsManager,
    private val displayManager: DisplaySettingsManager,
    private val dataManager: DataSettingsManager,
    private val stateManager: SettingsStateManager
) : BaseViewModel<SettingsState, SettingsIntent, SettingsEffect>() {
    
    init { loadSettings() }
    
    override fun createInitialState() = stateManager.createInitialState()
    
    override fun processIntent(intent: SettingsIntent) = when (intent) {
        is SettingsIntent.LoadSettings -> loadSettings()
        is SettingsIntent.ToggleBGM -> toggleBGM()
        is SettingsIntent.ChangeBGMVolume -> changeBGMVolume(intent.volume)
        is SettingsIntent.ChangeBGMTrack -> changeBGMTrack(intent.trackIndex)
        is SettingsIntent.ChangeFontSize -> changeFontSize(intent.level)
        is SettingsIntent.ToggleNotifications -> toggleNotifications()
        is SettingsIntent.ToggleAutoUnlock -> toggleAutoUnlock()
        is SettingsIntent.ChangeTheme -> changeTheme(intent.mode)
        is SettingsIntent.ClearCache -> clearCache()
        is SettingsIntent.ResetData -> showResetConfirmation()
    }
    
    private fun loadSettings() = launch {
        updateState { stateManager.updateLoadingState(it, true) }
        val bgm = bgmManager.loadBGMSettings(); val display = displayManager.loadDisplaySettings(); val data = dataManager.loadDataSettings()
        updateState { stateManager.updateAllSettings(it, bgm.enabled, bgm.volume, bgm.currentTrack,
            display.fontSizeScale, data.notificationsEnabled, data.autoUnlockEnabled, display.themeMode) }
        if (bgm.enabled) sendEffect(SettingsEffect.PlayBGM(bgm.currentTrack))
    }
    
    private fun toggleBGM() = launch {
        bgmManager.toggleBGM(currentState.bgmEnabled).onSuccess { newState ->
            updateState { stateManager.updateBGMEnabled(it, newState) }
            sendEffect(if (newState) SettingsEffect.PlayBGM(currentState.bgmTrackIndex) else SettingsEffect.StopBGM)
        }.onFailure { handleError(it) }
    }
    
    private fun changeBGMVolume(volume: Float) = launch {
        bgmManager.changeBGMVolume(volume).onSuccess { updateState { stateManager.updateBGMVolume(it, volume) } }.onFailure { handleError(it) }
    }
    
    private fun changeBGMTrack(trackIndex: Int) = launch {
        bgmManager.changeBGMTrack(trackIndex).onSuccess {
            updateState { stateManager.updateBGMTrack(it, trackIndex) }
            if (currentState.bgmEnabled) sendEffect(SettingsEffect.PlayBGM(trackIndex))
        }.onFailure { handleError(it) }
    }
    
    private fun changeFontSize(level: FontSizeLevel) = launch {
        displayManager.changeFontSize(level).onSuccess {
            updateState { stateManager.updateDisplaySettings(it, level.scale, currentState.themeMode) }
            sendEffect(SettingsEffect.ShowToast(Messages.SUCCESS_FONT_SIZE_CHANGED))
        }.onFailure { handleError(it) }
    }
    
    private fun toggleNotifications() = launch {
        val newState = !currentState.notificationsEnabled
        dataManager.setNotificationsEnabled(newState).onSuccess {
            updateState { stateManager.updateNotificationSettings(it, newState, currentState.autoUnlockEnabled) }
            sendEffect(SettingsEffect.ShowToast(if (newState) Messages.SUCCESS_NOTIFICATION_ENABLED else Messages.SUCCESS_NOTIFICATION_DISABLED))
        }.onFailure { handleError(it) }
    }
    
    private fun toggleAutoUnlock() = launch {
        dataManager.setAutoUnlockEnabled(!currentState.autoUnlockEnabled).onSuccess {
            updateState { stateManager.updateNotificationSettings(it, currentState.notificationsEnabled, !currentState.autoUnlockEnabled) }
        }.onFailure { handleError(it) }
    }
    
    private fun changeTheme(mode: ThemeMode) = launch {
        displayManager.changeTheme(mode).onSuccess {
            updateState { stateManager.updateDisplaySettings(it, currentState.fontSizeScale, mode) }
            sendEffect(SettingsEffect.ApplyTheme(mode))
        }.onFailure { handleError(it) }
    }
    
    private fun clearCache() = launch {
        updateState { stateManager.updateLoadingState(it, true) }
        dataManager.clearCache()
            .onSuccess { updateState { stateManager.updateLoadingState(it, false) }; sendEffect(SettingsEffect.ShowToast(Messages.SUCCESS_CACHE_DELETED)) }
            .onFailure { updateState { stateManager.updateErrorState(it, Messages.ERROR_CACHE_DELETE_FAILED) } }
    }
    
    private fun showResetConfirmation() = sendEffect(SettingsEffect.ShowResetConfirmation)
    override fun handleError(throwable: Throwable) = updateState { stateManager.updateErrorState(it, throwable.message ?: Messages.ERROR_GENERAL) }
    fun executeReset() = launch {
        updateState { stateManager.updateLoadingState(it, true) }
        dataManager.resetAllData()
            .onSuccess { sendEffect(SettingsEffect.RequireRestart) }
            .onFailure { updateState { stateManager.updateErrorState(it, Messages.ERROR_DATA_RESET_FAILED) } }
    }
}