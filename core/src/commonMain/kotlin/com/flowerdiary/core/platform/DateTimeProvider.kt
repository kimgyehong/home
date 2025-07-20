package com.flowerdiary.core.platform

import com.flowerdiary.core.types.DiaryDate
import com.flowerdiary.core.types.DiaryDateTime

/**
 * 날짜/시간 제공 인터페이스
 * 플랫폼별 시간 처리 구현 필요
 */
interface DateTimeProvider {
  
  /**
   * 현재 날짜 조회
   * @return 오늘 날짜
   */
  fun getCurrentDate(): DiaryDate

  /**
   * 현재 날짜/시간 조회
   * @return 현재 날짜와 시간
   */
  fun getCurrentDateTime(): DiaryDateTime

  /**
   * 날짜를 문자열로 변환
   * @param date 변환할 날짜
   * @param format 날짜 형식
   * @return 형식화된 날짜 문자열
   */
  fun formatDate(date: DiaryDate, format: String): String

  /**
   * 날짜/시간을 문자열로 변환
   * @param dateTime 변환할 날짜/시간
   * @param format 날짜/시간 형식
   * @return 형식화된 날짜/시간 문자열
   */
  fun formatDateTime(dateTime: DiaryDateTime, format: String): String

  /**
   * 문자열을 날짜로 변환
   * @param dateString 날짜 문자열
   * @param format 날짜 형식
   * @return 파싱된 날짜
   */
  fun parseDate(dateString: String, format: String): DiaryDate?

  /**
   * 월과 일로 탄생화 날짜 생성
   * @param month 월 (1-12)
   * @param day 일 (1-31)
   * @return MM-dd 형식의 날짜 문자열
   */
  fun createFlowerDate(month: Int, day: Int): String

  /**
   * 두 날짜 사이의 일수 계산
   * @param from 시작 날짜
   * @param to 종료 날짜
   * @return 일수 차이
   */
  fun daysBetween(from: DiaryDate, to: DiaryDate): Long
}