package com.flowerdiary.domain.model

import com.flowerdiary.common.constants.DiaryConstants

/**
 * 순수한 일기 데이터 클래스
 * SRP: 일기 데이터 저장만 담당
 * 모든 비즈니스 로직은 별도 서비스로 분리
 * 불변성 보장을 위한 data class 사용
 */
data class Diary(
    val id: DiaryId,
    val title: String,
    val content: String,
    val mood: Mood,
    val weather: Weather,
    val flowerId: FlowerId,
    val fontFamily: String = DiaryConstants.Font.DEFAULT_FONT_FAMILY,
    val fontSize: Float = DiaryConstants.Font.DEFAULT_FONT_SIZE,
    val fontColor: Long = DiaryConstants.Color.DEFAULT_FONT_COLOR,
    val backgroundTheme: String = DiaryConstants.Theme.DEFAULT_BACKGROUND_THEME,
    val createdAt: Long,
    val updatedAt: Long
)