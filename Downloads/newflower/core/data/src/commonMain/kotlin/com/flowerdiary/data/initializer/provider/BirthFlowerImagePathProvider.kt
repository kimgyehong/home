package com.flowerdiary.data.initializer.provider

import com.flowerdiary.common.constants.BirthFlowerConstants

/**
 * 탄생화 이미지 경로 제공자
 * SRP: 이미지 파일 경로 생성만 담당
 * 하드코딩 금지: 상수 사용
 */
class BirthFlowerImagePathProvider {
    
    /**
     * 월/일 기반 이미지 파일명 생성
     */
    fun getImageFileName(month: Int, day: Int): String {
        val monthStr = month.toString()
            .padStart(BirthFlowerConstants.DATE_PAD_LENGTH, BirthFlowerConstants.DATE_PAD_CHAR)
        val dayStr = day.toString()
            .padStart(BirthFlowerConstants.DATE_PAD_LENGTH, BirthFlowerConstants.DATE_PAD_CHAR)
        
        return "$monthStr$dayStr${BirthFlowerConstants.IMAGE_FILE_EXTENSION}"
    }
    
    /**
     * 이미지 URL 생성 (플랫폼 중립적 경로)
     */
    fun getImageUrl(month: Int, day: Int): String {
        val fileName = getImageFileName(month, day)
        // Android drawable 리소스 접근을 위한 경로
        return "flower_$fileName"
    }
    
    companion object {
        // 이미지는 각 플랫폼의 리소스 시스템에서 처리
    }
}
