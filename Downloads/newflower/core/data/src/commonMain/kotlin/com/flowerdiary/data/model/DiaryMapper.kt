package com.flowerdiary.data.model

import com.flowerdiary.common.constants.Config
import com.flowerdiary.data.Diary as DbDiary
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.model.DiaryId
import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather

/**
 * 일기 데이터 매퍼
 * DB 엔티티와 도메인 엔티티 간 변환
 * SRP: 데이터 변환 로직만 담당
 */
object DiaryMapper {
    
    /**
     * DB 엔티티를 도메인 엔티티로 변환
     */
    fun DbDiary.toDomain(): Diary = Diary(
        id = DiaryId(id),
        title = title,
        content = content,
        mood = Mood.fromName(mood) ?: Mood.fromName(Config.DEFAULT_MOOD) ?: Mood.PEACEFUL,
        weather = Weather.fromName(weather) ?: Weather.fromName(Config.DEFAULT_WEATHER) ?: Weather.SUNNY,
        flowerId = FlowerId(flower_id.toInt()),
        fontFamily = font_family,
        fontColor = font_color,
        backgroundTheme = background_theme,
        createdAt = created_at,
        updatedAt = updated_at
    )
    
    /**
     * 도메인 엔티티를 DB 파라미터로 변환
     * insertOrReplace 쿼리에 사용
     */
    fun Diary.toDbParams(): Array<Any?> = arrayOf(
        id.value,
        title,
        content,
        mood.name,
        weather.name,
        flowerId.value.toLong(),
        fontFamily,
        fontColor,
        backgroundTheme,
        createdAt,
        updatedAt
    )
}
