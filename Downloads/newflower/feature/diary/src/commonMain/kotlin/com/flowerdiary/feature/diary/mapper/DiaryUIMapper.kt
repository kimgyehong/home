package com.flowerdiary.feature.diary.mapper

import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.constants.ColorPalette
import com.flowerdiary.common.constants.Messages
import com.flowerdiary.domain.model.Diary
import kotlinx.datetime.*

/**
 * 일기 UI 표시용 매퍼
 * SRP: 도메인 모델을 UI 표시용으로 변환만 담당
 * 날짜 포맷팅, 미리보기 생성 등 UI 관련 로직 처리
 */
object DiaryUIMapper {
    
    /**
     * 일기를 UI 표시용 모델로 변환
     */
    fun Diary.toUIModel(): DiaryUIModel {
        val dateInfo = DateTimeUtil.toDateInfo(createdAt)
        
        return DiaryUIModel(
            id = id.value,
            title = getTitleOrDefault(),
            preview = getPreview(),
            formattedDate = formatDate(createdAt),
            relativeTime = getRelativeTime(createdAt),
            moodEmoji = mood.emoji,
            moodName = mood.displayName,
            weatherIcon = weather.icon,
            weatherName = weather.displayName,
            hasCustomSettings = !isDefaultSettings(),
            isEdited = isEdited()
        )
    }
    
    /**
     * 날짜 포맷팅
     */
    private fun formatDate(timestamp: Long): String {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        
        return "${localDateTime.year}년 ${localDateTime.monthNumber}월 ${localDateTime.dayOfMonth}일"
    }
    
    /**
     * 상대 시간 계산 (방금 전, 1시간 전 등)
     */
    private fun getRelativeTime(timestamp: Long): String {
        val now = Clock.System.now()
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val duration = now - instant
        
        return when {
            duration.inWholeMinutes < 1 -> Messages.TIME_JUST_NOW
            duration.inWholeMinutes < Config.MINUTES_PER_HOUR -> Messages.timeMinutesAgo(duration.inWholeMinutes)
            duration.inWholeHours < Config.HOURS_PER_DAY -> Messages.timeHoursAgo(duration.inWholeHours)
            duration.inWholeDays < Config.DAYS_PER_WEEK -> Messages.timeDaysAgo(duration.inWholeDays)
            duration.inWholeDays < Config.DAYS_PER_MONTH -> Messages.timeWeeksAgo(duration.inWholeDays / Config.DAYS_PER_WEEK)
            duration.inWholeDays < Config.DAYS_PER_YEAR -> Messages.timeMonthsAgo(duration.inWholeDays / Config.DAYS_PER_MONTH)
            else -> Messages.timeYearsAgo(duration.inWholeDays / Config.DAYS_PER_YEAR)
        }
    }
    
    /**
     * 기본 설정 여부 확인
     */
    private fun Diary.isDefaultSettings(): Boolean =
        fontFamily == Config.DEFAULT_FONT_FAMILY && 
        fontColor == ColorPalette.Text.Primary && 
        backgroundTheme == Config.DEFAULT_BACKGROUND_THEME
}

/**
 * UI 표시용 일기 모델
 * SRP: UI에 필요한 데이터만 포함
 */
data class DiaryUIModel(
    val id: String,
    val title: String,
    val preview: String,
    val formattedDate: String,
    val relativeTime: String,
    val moodEmoji: String,
    val moodName: String,
    val weatherIcon: String,
    val weatherName: String,
    val hasCustomSettings: Boolean,
    val isEdited: Boolean
)
