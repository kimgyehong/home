package com.flowerdiary.domain.service

import com.flowerdiary.common.constants.DiaryConstants
import com.flowerdiary.domain.model.Diary

/**
 * 일기 상태 확인 서비스
 * SRP: 일기의 다양한 상태 확인만 담당
 * 순수 함수로 구성되어 테스트하기 쉬움
 */
object DiaryStateChecker {
    
    /**
     * 일기가 비어있는지 확인
     * 제목과 내용이 모두 공백인 경우 비어있다고 판단
     */
    fun isEmpty(diary: Diary): Boolean {
        return diary.title.isBlank() && diary.content.isBlank()
    }
    
    /**
     * 일기가 수정되었는지 확인
     * 업데이트 시간이 생성 시간보다 늦은 경우 수정됨
     */
    fun isEdited(diary: Diary): Boolean {
        return diary.updatedAt > diary.createdAt
    }
    
    /**
     * 기본 설정인지 확인
     * 폰트, 색상, 테마가 모두 기본값인 경우
     */
    fun isDefaultSettings(diary: Diary): Boolean {
        return diary.fontFamily == DiaryConstants.Font.DEFAULT_FONT_FAMILY &&
               diary.fontColor == DiaryConstants.Color.DEFAULT_FONT_COLOR &&
               diary.backgroundTheme == DiaryConstants.Theme.DEFAULT_BACKGROUND_THEME
    }
    
    /**
     * 일기 작성 완료 여부 확인
     * 제목 또는 내용 중 하나라도 있으면 완료로 판단
     */
    fun isComplete(diary: Diary): Boolean {
        return !isEmpty(diary)
    }
    
    /**
     * 긴 일기인지 확인
     * 내용이 미리보기 길이보다 긴 경우
     */
    fun isLongContent(diary: Diary): Boolean {
        return diary.content.length > DiaryConstants.Length.PREVIEW_LENGTH
    }
    
    /**
     * 폰트 크기가 기본값인지 확인
     */
    fun hasDefaultFontSize(diary: Diary): Boolean {
        return diary.fontSize == DiaryConstants.Font.DEFAULT_FONT_SIZE
    }
    
    /**
     * 커스텀 스타일 적용 여부 확인
     */
    fun hasCustomStyle(diary: Diary): Boolean {
        return !isDefaultSettings(diary) || !hasDefaultFontSize(diary)
    }
}