package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.constants.Messages
import com.flowerdiary.domain.model.DiarySettings
import com.flowerdiary.feature.diary.state.DiaryEditorState

/**
 * 일기 스타일 관리자
 * SRP: 일기의 스타일(폰트, 색상, 배경) 관련 로직만 담당
 */
class DiaryStyleManager {
    
    /**
     * 폰트 패밀리 변경
     */
    fun changeFontFamily(
        currentState: DiaryEditorState,
        fontFamily: String
    ): DiaryEditorState {
        val updatedSettings = currentState.customization.diarySettings.copy(
            fontFamily = fontFamily
        )
        return currentState.copy(
            customization = currentState.customization.copy(
                diarySettings = updatedSettings
            )
        )
    }
    
    /**
     * 폰트 색상 변경
     */
    fun changeFontColor(
        currentState: DiaryEditorState,
        color: Long
    ): DiaryEditorState {
        val updatedSettings = currentState.customization.diarySettings.copy(
            fontColor = color
        )
        return currentState.copy(
            customization = currentState.customization.copy(
                diarySettings = updatedSettings
            )
        )
    }
    
    /**
     * 배경 테마 변경
     */
    fun changeBackgroundTheme(
        currentState: DiaryEditorState,
        theme: String
    ): DiaryEditorState {
        val updatedSettings = currentState.customization.diarySettings.copy(
            backgroundTheme = theme
        )
        return currentState.copy(
            customization = currentState.customization.copy(
                diarySettings = updatedSettings
            )
        )
    }
    
    /**
     * 스타일 설정 초기화
     */
    fun initializeStyleSettings(
        fontFamily: String = "default",
        fontColor: Long = 0xFF000000L,
        backgroundTheme: String = "default"
    ): DiarySettings {
        return DiarySettings(
            fontFamily = fontFamily,
            fontColor = fontColor,
            backgroundTheme = backgroundTheme
        )
    }
    
    /**
     * 스타일 관련 메시지 가져오기
     */
    fun getStyleChangedMessage(styleName: String): String {
        return when (styleName) {
            "font" -> Messages.SUCCESS_FONT_CHANGED
            "color" -> Messages.SUCCESS_COLOR_CHANGED
            "background" -> Messages.SUCCESS_BACKGROUND_CHANGED
            else -> Messages.SUCCESS_STYLE_CHANGED
        }
    }
}