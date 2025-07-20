package com.flowerdiary.core.types

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

/**
 * 날짜 관련 성능 최적화 타입들
 * 빈번한 생성이 발생하므로 typealias 사용
 */

/**
 * 일기 작성 날짜
 * UI에서 빈번하게 사용되는 타입
 */
typealias DiaryDate = LocalDate

/**
 * 일기 작성 시간 (날짜 + 시간)
 * 정확한 작성 시점 기록
 */
typealias DiaryDateTime = LocalDateTime

/**
 * 탄생화 날짜 (월-일)
 * 연도는 제외하고 월과 일만 사용
 */
typealias FlowerDate = String

/**
 * 미디어 파일 생성 시간
 * 파일 메타데이터에서 사용
 */
typealias MediaTimestamp = LocalDateTime

/**
 * 설정 변경 시간
 * 설정 이력 추적용
 */
typealias SettingsTimestamp = LocalDateTime

/**
 * 날짜 형식 상수들
 */
object DateFormats {
  const val DIARY_DATE_FORMAT = "yyyy-MM-dd"
  const val DIARY_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
  const val FLOWER_DATE_FORMAT = "MM-dd"
  const val DISPLAY_DATE_FORMAT = "yyyy년 MM월 dd일"
  const val DISPLAY_TIME_FORMAT = "HH:mm"
}

/**
 * 날짜 유틸리티 상수들
 */
object DateConstants {
  const val DAYS_IN_YEAR = 365
  const val MONTHS_IN_YEAR = 12
  const val MIN_MONTH = 1
  const val MAX_MONTH = 12
  const val MIN_DAY = 1
  const val MAX_DAY = 31
}