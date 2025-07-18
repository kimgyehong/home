package com.flowerdiary.common.resources

import com.flowerdiary.common.constants.Config

/**
 * 리소스 경로 제공자
 * SRP: 리소스 경로 생성만 담당
 * 플랫폼 중립적이며 하드코딩 금지 원칙 준수
 */
object ResourcePathProvider {
    
    /**
     * 탄생화 이미지 경로 생성
     */
    fun getBirthFlowerImagePath(month: Int, day: Int): String {
        require(month in 1..Config.MONTHS_PER_YEAR) { "Invalid month: $month" }
        require(day in 1..31) { "Invalid day: $day" }
        
        val monthStr = month.toString().padStart(Config.DATE_FORMAT_PADDING_LENGTH, Config.DATE_FORMAT_PADDING_CHAR)
        val dayStr = day.toString().padStart(Config.DATE_FORMAT_PADDING_LENGTH, Config.DATE_FORMAT_PADDING_CHAR)
        return "${Config.RESOURCE_PATH_BIRTHFLOWER}/$monthStr$dayStr${Config.FILE_EXTENSION_JPG}"
    }
    
    /**
     * 로딩 배경 이미지 경로 생성
     */
    fun getRandomLoadingImagePath(): String {
        val randomIndex = (1..Config.LOADING_IMAGE_COUNT).random()
        return "${Config.RESOURCE_PATH_LOADING}/${Config.LOADING_FILE_BASE_NAME}$randomIndex${Config.FILE_EXTENSION_PNG}"
    }
    
    /**
     * BGM 파일 경로 생성
     */
    fun getBGMPath(trackIndex: Int): String {
        require(trackIndex in 0 until Config.BGM_TRACK_COUNT) {
            "BGM track index must be between 0 and ${Config.BGM_TRACK_COUNT - 1}"
        }
        return when (trackIndex) {
            0 -> "${Config.RESOURCE_PATH_BGM}/${Config.BGM_FILE_BASE_NAME}${Config.FILE_EXTENSION_MP3}"
            else -> "${Config.RESOURCE_PATH_BGM}/${Config.BGM_FILE_BASE_NAME}${trackIndex + 1}${Config.FILE_EXTENSION_MP3}"
        }
    }
    
    /**
     * UI 리소스 경로 생성
     */
    fun getUIResourcePath(resourceName: String): String {
        return "${Config.RESOURCE_PATH_UI}/$resourceName${Config.FILE_EXTENSION_PNG}"
    }
    
    /**
     * 앱 리소스 경로 생성
     */
    fun getAppResourcePath(resourceName: String): String {
        return "${Config.RESOURCE_PATH_APP}/$resourceName${Config.FILE_EXTENSION_PNG}"
    }
    
    /**
     * 비디오 리소스 경로 생성
     */
    fun getVideoResourcePath(resourceName: String): String {
        return "${Config.RESOURCE_PATH_VIDEOS}/$resourceName${Config.FILE_EXTENSION_MP4}"
    }
    
    /**
     * 애니메이션 리소스 경로 생성
     */
    fun getAnimationResourcePath(resourceName: String): String {
        return "${Config.RESOURCE_PATH_ANIMATIONS}/$resourceName${Config.FILE_EXTENSION_PNG}"
    }
    
    /**
     * 도감 리소스 경로 생성
     */
    fun getCollectionResourcePath(resourceName: String): String {
        return "${Config.RESOURCE_PATH_COLLECTION}/$resourceName${Config.FILE_EXTENSION_PNG}"
    }
}
