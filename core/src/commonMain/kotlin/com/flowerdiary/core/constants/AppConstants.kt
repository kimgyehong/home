package com.flowerdiary.core.constants

/**
 * 앱 전반에서 사용되는 상수들
 */
object AppConstants {
  
  /**
   * 앱 정보
   */
  const val APP_NAME = "꽃일기"
  const val APP_VERSION = "1.0.0"
  const val DATABASE_NAME = "flower_diary.db"
  const val DATABASE_VERSION = 1
  
  /**
   * 화면 전환 시간
   */
  const val DEFAULT_TRANSITION_DURATION = 300L
  const val LOADING_MIN_DURATION = 1500L
  const val LOADING_MAX_DURATION = 3000L
  const val OPENING_VIDEO_SKIP_DELAY = 1000L
  
  /**
   * 파일 확장자
   */
  const val VIDEO_EXTENSION = ".mp4"
  const val AUDIO_EXTENSION = ".mp3"
  const val IMAGE_EXTENSION_JPG = ".jpg"
  const val IMAGE_EXTENSION_PNG = ".png"
  
  /**
   * 기본 설정값
   */
  const val DEFAULT_BGM_VOLUME = 0.7f
  const val DEFAULT_BGM_TRACK = 1
  const val DEFAULT_LOADING_DURATION = 2000L
  
  /**
   * UI 관련 상수
   */
  const val ANIMATION_DURATION_SHORT = 200L
  const val ANIMATION_DURATION_MEDIUM = 400L
  const val ANIMATION_DURATION_LONG = 600L
  
  /**
   * 터치 영역 최소 크기
   */
  const val MIN_TOUCH_TARGET_SIZE = 48
  
  /**
   * 시스템 상수
   */
  const val DAYS_IN_YEAR = 365
  const val MONTHS_IN_YEAR = 12
  const val MAX_RETRY_COUNT = 3
  const val NETWORK_TIMEOUT = 30000L
}