package com.flowerdiary.core.constants

/**
 * UI 관련 상수들
 */
object UIConstants {
  
  /**
   * 화면 레이어 구조
   */
  const val BOTTOM_LAYER_Z_INDEX = 0
  const val MIDDLE_LAYER_Z_INDEX = 1
  const val TOP_LAYER_Z_INDEX = 2
  const val OVERLAY_Z_INDEX = 3
  
  /**
   * 로딩 화면
   */
  const val LOADING_IMAGES_COUNT = 7
  const val LOADING_GEAR_ROTATION_DURATION = 2000L
  const val LOADING_TEXT_STYLE = "italic"
  const val LOADING_TEXT = "loading..."
  const val LOADING_GEAR_SIZE = 32
  
  /**
   * 타이틀 화면
   */
  const val TITLE_CLICK_AREA_FULL_SCREEN = true
  const val TITLE_TRANSITION_DELAY = 500L
  
  /**
   * 메인 화면
   */
  const val MAIN_SCREEN_BACKGROUND = "gardenwindow.jpg"
  const val MENU_BUTTON_SIZE = 80
  const val MENU_SPACING = 20
  
  /**
   * 일기장 화면
   */
  const val DIARY_BACKGROUND_OPACITY = 0.8f
  const val DIARY_TEXT_MAX_LINES = 20
  const val DIARY_TITLE_MAX_LENGTH = 50
  
  /**
   * 도감 화면
   */
  const val COLLECTION_GRID_COLUMNS = 3
  const val COLLECTION_ITEM_SPACING = 8
  const val COLLECTION_ITEM_ASPECT_RATIO = 1.0f
  
  /**
   * 일기 저장소
   */
  const val REPO_LIST_ITEM_HEIGHT = 80
  const val REPO_SCROLL_ANIMATION_DURATION = 300L
  
  /**
   * 공통 UI 요소
   */
  const val BUTTON_CORNER_RADIUS = 8
  const val CARD_ELEVATION = 4
  const val DIVIDER_THICKNESS = 1
  const val DEFAULT_PADDING = 16
  const val SMALL_PADDING = 8
  const val LARGE_PADDING = 24
  
  /**
   * 애니메이션
   */
  const val FADE_IN_DURATION = 300L
  const val FADE_OUT_DURATION = 200L
  const val SLIDE_DURATION = 250L
  const val SCALE_DURATION = 150L
  
  /**
   * 색상값 (Hex)
   */
  const val PRIMARY_COLOR = "#8BC34A"
  const val BACKGROUND_COLOR = "#F5F5F5"
  const val TEXT_COLOR = "#333333"
  const val ACCENT_COLOR = "#4CAF50"
  const val ERROR_COLOR = "#F44336"
}