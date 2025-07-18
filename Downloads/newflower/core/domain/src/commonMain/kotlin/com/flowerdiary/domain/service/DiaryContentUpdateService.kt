package com.flowerdiary.domain.service

import com.flowerdiary.common.constants.DiaryConstants
import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather

/**
 * 일기 내용 업데이트 서비스
 * SRP: 일기의 내용 관련 업데이트만 담당
 * 제목, 내용, 기분, 날씨, 꽃 등 핵심 콘텐츠 업데이트
 */
object DiaryContentUpdateService {
    
    /**
     * 일기 내용 전체 업데이트
     */
    fun update(
        diary: Diary,
        title: String? = null,
        content: String? = null,
        mood: Mood? = null,
        weather: Weather? = null,
        flowerId: FlowerId? = null,
        updateTime: Long = DateTimeUtil.now()
    ): Diary {
        val updatedDiary = diary.copy(
            title = title ?: diary.title,
            content = content ?: diary.content,
            mood = mood ?: diary.mood,
            weather = weather ?: diary.weather,
            flowerId = flowerId ?: diary.flowerId,
            updatedAt = updateTime
        )
        
        Logger.debug(
            DiaryConstants.LogTags.DIARY_UPDATE_SERVICE,
            "Updated diary content: ${updatedDiary.id}"
        )
        
        return updatedDiary
    }
    
    /**
     * 텍스트 내용만 업데이트
     */
    fun updateText(
        diary: Diary,
        title: String? = null,
        content: String? = null
    ): Diary {
        return update(
            diary = diary,
            title = title,
            content = content
        )
    }
    
    /**
     * 기분과 날씨만 업데이트
     */
    fun updateMoodAndWeather(
        diary: Diary,
        mood: Mood? = null,
        weather: Weather? = null
    ): Diary {
        return update(
            diary = diary,
            mood = mood,
            weather = weather
        )
    }
    
    /**
     * 꽃 선택 업데이트
     */
    fun updateFlower(
        diary: Diary,
        flowerId: FlowerId
    ): Diary {
        return update(
            diary = diary,
            flowerId = flowerId
        )
    }
}