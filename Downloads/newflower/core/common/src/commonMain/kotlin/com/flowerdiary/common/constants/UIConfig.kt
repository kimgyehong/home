package com.flowerdiary.common.constants

/**
 * UI 관련 설정 상수
 * SRP: UI 애니메이션 및 디스플레이 설정만 담당
 */
object UIConfig {
    // 기본 상태값
    const val DEFAULT_MOOD = "PEACEFUL"
    const val DEFAULT_WEATHER = "SUNNY"
    const val DEFAULT_BACKGROUND_THEME = "default"
    
    // 애니메이션 설정
    const val ANIMATION_DURATION_SHORT = 300
    const val ANIMATION_DURATION_MEDIUM = 500
    const val ANIMATION_DURATION_LONG = 1000
    
    // 로딩 설정
    const val LOADING_DELAY_MS = 2000L
    const val SKIP_BUTTON_DELAY_MS = 2000L
    
    // BGM 설정
    const val BGM_VOLUME_DEFAULT = 0.7f
    const val BGM_FADE_DURATION_MS = 500L
    const val BGM_TRACK_COUNT = 4
    const val BGM_FADE_STEPS = 20
    
    // 이미지 설정
    const val IMAGE_QUALITY = 80
    const val MAX_IMAGE_SIZE_KB = 1024
    const val THUMBNAIL_SIZE = 150
    
    // 색상 관련 상수
    const val COLOR_MAX_VALUE = 255
    const val HUE_MAX_DEGREES = 360
    const val COLOR_CHANNEL_BITS = 8
    const val ALPHA_CHANNEL_SHIFT = 24
    const val RED_CHANNEL_SHIFT = 16
    const val GREEN_CHANNEL_SHIFT = 8
    const val FULL_ALPHA = 0xFF
    
    // 색상 생성 관련 상수
    const val COLOR_HUE_MAX = 360
    const val COLOR_HASH_MAX = 255
    const val COLOR_SATURATION_DEFAULT = 0.7f
    const val COLOR_LIGHTNESS_DEFAULT = 0.8f
    const val COLOR_HUE_SEGMENT = 60
    const val COLOR_MIN = 0.0f
    const val COLOR_RGB_MAX = 255.0f
    const val COLOR_ALPHA_OPAQUE = 0xFF
}