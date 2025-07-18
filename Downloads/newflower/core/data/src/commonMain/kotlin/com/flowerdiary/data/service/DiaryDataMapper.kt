package com.flowerdiary.data.service

import com.flowerdiary.common.constants.RepositoryConstants
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.data.Diary as DiaryDb
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.model.DiaryId
import com.flowerdiary.domain.model.FlowerId
import com.flowerdiary.domain.model.Mood
import com.flowerdiary.domain.model.Weather

/**
 * 일기 데이터 매핑 서비스
 * SRP: 데이터베이스 모델과 도메인 모델 간의 변환만 담당
 * 순수 함수로 구성되어 사이드 이펙트 없음
 */
object DiaryDataMapper {
    
    /**
     * 데이터베이스 모델을 도메인 모델로 변환
     */
    fun toDomain(dbDiary: DiaryDb): Diary {
        return Diary(
            id = DiaryId(dbDiary.id),
            title = dbDiary.title,
            content = dbDiary.content,
            mood = Mood.valueOf(dbDiary.mood),
            weather = Weather.valueOf(dbDiary.weather),
            flowerId = FlowerId(dbDiary.flower_id.toInt()),
            fontFamily = dbDiary.font_family,
            fontSize = dbDiary.font_size,
            fontColor = dbDiary.font_color,
            backgroundTheme = dbDiary.background_theme,
            createdAt = dbDiary.created_at,
            updatedAt = dbDiary.updated_at
        )
    }
    
    /**
     * 도메인 모델을 데이터베이스 삽입 파라미터로 변환
     */
    fun toDbParams(diary: Diary): DiaryInsertParams {
        return DiaryInsertParams(
            id = diary.id.value,
            title = diary.title,
            content = diary.content,
            mood = diary.mood.name,
            weather = diary.weather.name,
            flowerId = diary.flowerId.value.toLong(),
            fontFamily = diary.fontFamily,
            fontSize = diary.fontSize,
            fontColor = diary.fontColor,
            backgroundTheme = diary.backgroundTheme,
            createdAt = diary.createdAt,
            updatedAt = diary.updatedAt
        )
    }
    
    /**
     * 리스트 변환 헬퍼
     */
    fun toDomainList(dbDiaries: List<DiaryDb>): List<Diary> {
        return dbDiaries.map { toDomain(it) }
    }
}

/**
 * 데이터베이스 삽입 파라미터 데이터 클래스
 */
data class DiaryInsertParams(
    val id: String,
    val title: String,
    val content: String,
    val mood: String,
    val weather: String,
    val flowerId: Long,
    val fontFamily: String,
    val fontSize: Float,
    val fontColor: Long,
    val backgroundTheme: String,
    val createdAt: Long,
    val updatedAt: Long
)