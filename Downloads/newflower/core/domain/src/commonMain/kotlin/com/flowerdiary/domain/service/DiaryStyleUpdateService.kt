package com.flowerdiary.domain.service

import com.flowerdiary.common.constants.DiaryConstants
import com.flowerdiary.common.platform.DateTimeUtil
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.Diary

/**
 * 일기 스타일 업데이트 서비스
 * SRP: 일기의 스타일 관련 업데이트만 담당
 * 폰트, 색상, 테마 등 외관 설정 업데이트
 */
object DiaryStyleUpdateService {
    
    /**
     * 스타일 전체 업데이트
     */
    fun update(
        diary: Diary,
        fontFamily: String? = null,
        fontSize: Float? = null,
        fontColor: Long? = null,
        backgroundTheme: String? = null,
        updateTime: Long = DateTimeUtil.now()
    ): Diary {
        val updatedDiary = diary.copy(
            fontFamily = fontFamily ?: diary.fontFamily,
            fontSize = fontSize ?: diary.fontSize,
            fontColor = fontColor ?: diary.fontColor,
            backgroundTheme = backgroundTheme ?: diary.backgroundTheme,
            updatedAt = updateTime
        )
        
        Logger.debug(
            DiaryConstants.LogTags.DIARY_UPDATE_SERVICE,
            "Updated diary style: ${updatedDiary.id}"
        )
        
        return updatedDiary
    }
    
    /**
     * 폰트 설정 업데이트
     */
    fun updateFont(
        diary: Diary,
        fontFamily: String? = null,
        fontSize: Float? = null,
        fontColor: Long? = null
    ): Diary {
        return update(
            diary = diary,
            fontFamily = fontFamily,
            fontSize = fontSize,
            fontColor = fontColor
        )
    }
    
    /**
     * 테마 업데이트
     */
    fun updateTheme(
        diary: Diary,
        backgroundTheme: String
    ): Diary {
        return update(
            diary = diary,
            backgroundTheme = backgroundTheme
        )
    }
    
    /**
     * 기본 스타일로 초기화
     */
    fun resetToDefault(diary: Diary): Diary {
        return update(
            diary = diary,
            fontFamily = DiaryConstants.Font.DEFAULT_FONT_FAMILY,
            fontSize = DiaryConstants.Font.DEFAULT_FONT_SIZE,
            fontColor = DiaryConstants.Color.DEFAULT_FONT_COLOR,
            backgroundTheme = DiaryConstants.Theme.DEFAULT_BACKGROUND_THEME
        )
    }
}