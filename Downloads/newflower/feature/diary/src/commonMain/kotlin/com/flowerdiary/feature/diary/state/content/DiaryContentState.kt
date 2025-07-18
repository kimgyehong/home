package com.flowerdiary.feature.diary.state.content

import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather
import com.flowerdiary.feature.diary.common.CharCount
import com.flowerdiary.feature.diary.common.hasNonBlankContent
import com.flowerdiary.feature.diary.common.toCharCount

/**
 * 일기 내용 관련 상태 - Context7 KMP 극한 압축
 * SRP: 일기의 텍스트 내용과 감정/날씨 정보만 관리
 */
data class DiaryContentState(
    val title: String = "",
    val content: String = "",
    val selectedMood: Mood = Mood.PEACEFUL,
    val selectedWeather: Weather = Weather.SUNNY
) {
    val titleCharCount: CharCount get() = title.toCharCount()
    val contentCharCount: CharCount get() = content.toCharCount()
    val canSave: Boolean get() = listOf(title, content).hasNonBlankContent()
    
    companion object {
        fun create(title: String = "", content: String = ""): DiaryContentState =
            DiaryContentState(title.trim(), content.trim())
    }
}