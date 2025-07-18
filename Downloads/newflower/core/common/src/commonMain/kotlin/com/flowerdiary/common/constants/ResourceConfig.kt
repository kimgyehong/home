package com.flowerdiary.common.constants

/**
 * 리소스 관련 설정 상수
 * SRP: 리소스 경로 및 파일 설정만 담당
 */
object ResourceConfig {
    // 리소스 설정
    const val LOADING_IMAGE_COUNT = 7
    const val TOTAL_BIRTH_FLOWERS = 365
    
    // 리소스 경로 상수
    const val RESOURCE_PATH_BIRTHFLOWER = "birthflower"
    const val RESOURCE_PATH_LOADING = "loading"
    const val RESOURCE_PATH_BGM = "bgm"
    const val RESOURCE_PATH_UI = "ui"
    const val RESOURCE_PATH_APP = "app"
    const val RESOURCE_PATH_VIDEOS = "videos"
    const val RESOURCE_PATH_ANIMATIONS = "animations"
    const val RESOURCE_PATH_COLLECTION = "collection"
    
    // 파일 확장자 상수
    const val FILE_EXTENSION_JPG = ".jpg"
    const val FILE_EXTENSION_PNG = ".png"
    const val FILE_EXTENSION_MP3 = ".mp3"
    const val FILE_EXTENSION_MP4 = ".mp4"
    
    // 파일명 패턴 상수
    const val BGM_FILE_BASE_NAME = "bgm"
    const val LOADING_FILE_BASE_NAME = "loading"
    const val DATE_FORMAT_PADDING_CHAR = '0'
    const val DATE_FORMAT_PADDING_LENGTH = 2
}