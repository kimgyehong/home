package com.flowerdiary.domain.service

import com.flowerdiary.common.constants.DiaryConstants
import com.flowerdiary.common.utils.Logger

/**
 * 일기 포맷팅 서비스
 * SRP: 일기 데이터 포맷팅 및 표시용 변환만 담당
 * 순수 함수로 구성되어 사이드 이펙트 없음
 */
object DiaryFormatter {
    
    /**
     * 일기 내용 미리보기 생성
     * 지정된 길이로 자르고 생략 표시 추가
     */
    fun createPreview(
        content: String,
        maxLength: Int = DiaryConstants.Length.PREVIEW_LENGTH
    ): String {
        return when {
            content.isBlank() -> DiaryConstants.Default.EMPTY_CONTENT
            content.length <= maxLength -> content
            else -> content.take(maxLength) + DiaryConstants.Default.PREVIEW_SUFFIX
        }
    }
    
    /**
     * 제목이 없을 때 기본 제목 반환
     */
    fun getTitleOrDefault(title: String): String {
        return title.ifBlank { DiaryConstants.Default.TITLE_PLACEHOLDER }
    }
    
    /**
     * 일기 요약 정보 생성
     * 제목과 미리보기를 조합한 요약 문자열 반환
     */
    fun createSummary(
        title: String,
        content: String,
        previewLength: Int = DiaryConstants.Length.PREVIEW_LENGTH
    ): String {
        val formattedTitle = getTitleOrDefault(title)
        val preview = createPreview(content, previewLength)
        
        return when {
            preview.isBlank() -> formattedTitle
            else -> "$formattedTitle - $preview"
        }
    }
    
    /**
     * 글자 수 표시용 텍스트 생성
     */
    fun createCharacterCountText(
        currentLength: Int,
        maxLength: Int
    ): String {
        return "$currentLength / $maxLength"
    }
    
    /**
     * 읽기 시간 추정 (분 단위)
     * 일반적으로 분당 200자 기준
     */
    fun estimateReadingTime(content: String): Int {
        val charactersPerMinute = 200
        val contentLength = content.length
        return if (contentLength == 0) 0 else maxOf(1, contentLength / charactersPerMinute)
    }
}