package com.flowerdiary.core.constants

/**
 * 미디어 파일 관련 상수들
 */
object MediaConstants {
  
  /**
   * 파일 크기 제한
   */
  const val MAX_IMAGE_SIZE = 10L * 1024 * 1024 // 10MB
  const val MAX_VIDEO_SIZE = 100L * 1024 * 1024 // 100MB
  const val MAX_AUDIO_SIZE = 20L * 1024 * 1024 // 20MB
  
  /**
   * 이미지 해상도
   */
  const val MIN_IMAGE_WIDTH = 100
  const val MIN_IMAGE_HEIGHT = 100
  const val MAX_IMAGE_WIDTH = 4096
  const val MAX_IMAGE_HEIGHT = 4096
  
  /**
   * 비디오 설정
   */
  const val MIN_VIDEO_DURATION = 1000L // 1초
  const val MAX_VIDEO_DURATION = 300000L // 5분
  const val VIDEO_BITRATE = 2000000 // 2Mbps
  
  /**
   * 오디오 설정
   */
  const val AUDIO_SAMPLE_RATE = 44100
  const val AUDIO_CHANNELS = 2
  const val AUDIO_BITRATE = 192000 // 192kbps
  const val MIN_AUDIO_DURATION = 5000L // 5초
  const val MAX_AUDIO_DURATION = 600000L // 10분
  
  /**
   * BGM 관련
   */
  const val BGM_TRACK_COUNT = 4
  const val BGM_FADE_DURATION = 1000L
  const val BGM_DEFAULT_VOLUME = 0.7f
  const val BGM_MIN_VOLUME = 0.0f
  const val BGM_MAX_VOLUME = 1.0f
  
  /**
   * 미디어 타입
   */
  const val MIME_TYPE_MP4 = "video/mp4"
  const val MIME_TYPE_MP3 = "audio/mp3"
  const val MIME_TYPE_JPEG = "image/jpeg"
  const val MIME_TYPE_PNG = "image/png"
  const val MIME_TYPE_WEBP = "image/webp"
  
  /**
   * 파일명 패턴
   */
  const val OPENING_VIDEO_NAME = "opening.mp4"
  const val BGM_FILE_PREFIX = "bgm"
  const val LOADING_IMAGE_PREFIX = "loading"
  const val TITLE_BACKGROUND = "title2.jpg"
  const val START_BUTTON = "start.png"
  const val GARDEN_WINDOW = "gardenwindow.jpg"
  const val REPO_BACKGROUND = "repo.jpg"
  const val NOTEBOOK_OVERLAY = "노트북.png"
  const val GEAR_ICON = "gear.png"
}