package com.flowerdiary.feature.diary.effect

import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.feature.diary.state.settings.ThemeMode

/**
 * 앱 전체 부수효과 - Context7 KMP 극한 압축
 * SRP: 모든 부수효과를 카테고리별로 통합 관리
 */
sealed interface AppEffect {
    
    sealed interface Navigation : AppEffect {
        data object Back : Navigation
        data object ToFlowerPicker : Navigation
        data object ToMoodPicker : Navigation
        data object ToWeatherPicker : Navigation
        data object ToDecorationOptions : Navigation
    }
    
    sealed interface UI : AppEffect {
        data class ShowToast(val message: String) : UI
        data class ShowDialog(val title: String, val message: String = "") : UI
        data object ShowLoading : UI
        data object HideLoading : UI
        data object ShowUnsavedChangesDialog : UI
        data object ShowResetConfirmation : UI
        data object ShowCompletionCelebration : UI
    }
    
    sealed interface Audio : AppEffect {
        data class PlayBGM(val trackIndex: Int) : Audio
        data object StopBGM : Audio
        data class SetBGMVolume(val volume: Float) : Audio
    }
    
    sealed interface System : AppEffect {
        data class ApplyTheme(val mode: ThemeMode) : System
        data object RequireRestart : System
        data class ShareFlower(val flower: BirthFlower) : System
        data class ShowFlowerDetail(val flower: BirthFlower) : System
        data class ShowUnlockAnimation(val flower: BirthFlower) : System
    }
}