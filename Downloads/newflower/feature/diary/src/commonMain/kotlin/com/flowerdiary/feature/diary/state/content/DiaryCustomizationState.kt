package com.flowerdiary.feature.diary.state.content

import com.flowerdiary.domain.model.DiarySettings

/**
 * 일기 커스터마이징 관련 상태
 * SRP: 일기 꾸미기 설정과 관련된 상태만 관리
 */
data class DiaryCustomizationState(
    val diarySettings: DiarySettings = DiarySettings(),
    val isCustomizationEnabled: Boolean = true
) {
    /**
     * 일기 설정이 기본값에서 변경되었는지
     */
    val hasCustomSettings: Boolean get() = !diarySettings.isDefault()
    
    /**
     * 현재 폰트 패밀리
     */
    val currentFontFamily: String get() = diarySettings.fontFamily
    
    /**
     * 현재 폰트 색상
     */
    val currentFontColor: Long get() = diarySettings.fontColor
    
    /**
     * 현재 배경 테마
     */
    val currentBackgroundTheme: String get() = diarySettings.backgroundTheme
}