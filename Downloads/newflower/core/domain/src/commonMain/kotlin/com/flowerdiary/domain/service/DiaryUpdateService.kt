package com.flowerdiary.domain.service

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather

/**
 * 일기 업데이트 서비스 (통합 파사드)
 * SRP: 두 개의 업데이트 서비스를 조합하여 편리한 인터페이스 제공
 * Facade Pattern 적용
 */
object DiaryUpdateService {
    
    /**
     * 일기 전체 업데이트
     * 내용과 스타일을 모두 업데이트할 수 있는 통합 인터페이스
     */
    fun update(
        diary: Diary,
        title: String? = null,
        content: String? = null,
        mood: Mood? = null,
        weather: Weather? = null,
        flowerId: FlowerId? = null,
        fontFamily: String? = null,
        fontSize: Float? = null,
        fontColor: Long? = null,
        backgroundTheme: String? = null,
        updateTime: Long = DateTimeUtil.now()
    ): Diary {
        // 먼저 내용 업데이트
        val contentUpdated = DiaryContentUpdateService.update(
            diary = diary,
            title = title,
            content = content,
            mood = mood,
            weather = weather,
            flowerId = flowerId,
            updateTime = updateTime
        )
        
        // 스타일 업데이트가 필요한 경우
        return if (fontFamily != null || fontSize != null || fontColor != null || backgroundTheme != null) {
            DiaryStyleUpdateService.update(
                diary = contentUpdated,
                fontFamily = fontFamily,
                fontSize = fontSize,
                fontColor = fontColor,
                backgroundTheme = backgroundTheme,
                updateTime = updateTime
            )
        } else {
            contentUpdated
        }
    }
    
    /**
     * 내용만 업데이트 (편의 메서드)
     */
    fun updateContent(
        diary: Diary,
        title: String? = null,
        content: String? = null
    ): Diary = DiaryContentUpdateService.updateText(diary, title, content)
    
    /**
     * 스타일만 업데이트 (편의 메서드)
     */
    fun updateStyle(
        diary: Diary,
        fontFamily: String? = null,
        fontSize: Float? = null,
        fontColor: Long? = null,
        backgroundTheme: String? = null
    ): Diary = DiaryStyleUpdateService.update(diary, fontFamily, fontSize, fontColor, backgroundTheme)
    
    /**
     * 기분과 날씨 업데이트 (편의 메서드)
     */
    fun updateMoodAndWeather(
        diary: Diary,
        mood: Mood? = null,
        weather: Weather? = null
    ): Diary = DiaryContentUpdateService.updateMoodAndWeather(diary, mood, weather)
}