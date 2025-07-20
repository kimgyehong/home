package com.flowerdiary.core.util

/**
 * 데이터 유효성 검증 유틸리티
 * 도메인 모델의 검증 로직 통합
 */
object Validator {
  
  /**
   * 문자열이 공백이 아닌지 검증
   */
  fun isNotBlank(value: String?): Boolean = !value.isNullOrBlank()
  
  /**
   * 문자열 길이 검증
   */
  fun isValidLength(value: String?, minLength: Int, maxLength: Int): Boolean {
    if (value == null) return false
    return value.length in minLength..maxLength
  }
  
  /**
   * 이메일 형식 검증 (향후 확장용)
   */
  fun isValidEmail(email: String?): Boolean {
    if (email.isNullOrBlank()) return false
    return email.contains("@") && email.contains(".")
  }
  
  /**
   * 색상 Hex 코드 검증
   */
  fun isValidColorHex(color: String?): Boolean {
    if (color.isNullOrBlank()) return false
    return color.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"))
  }
  
  /**
   * 파일 확장자 검증
   */
  fun hasValidExtension(fileName: String?, validExtensions: List<String>): Boolean {
    if (fileName.isNullOrBlank()) return false
    return validExtensions.any { ext -> fileName.endsWith(ext, ignoreCase = true) }
  }
  
  /**
   * 숫자 범위 검증
   */
  fun isInRange(value: Int, min: Int, max: Int): Boolean = value in min..max
  
  /**
   * 숫자 범위 검증 (Float)
   */
  fun isInRange(value: Float, min: Float, max: Float): Boolean = value in min..max
  
  /**
   * 숫자 범위 검증 (Long)
   */
  fun isInRange(value: Long, min: Long, max: Long): Boolean = value in min..max
  
  /**
   * 월 유효성 검증 (1-12)
   */
  fun isValidMonth(month: Int): Boolean = month in 1..12
  
  /**
   * 일 유효성 검증 (1-31)
   */
  fun isValidDay(day: Int): Boolean = day in 1..31
  
  /**
   * 파일 크기 검증
   */
  fun isValidFileSize(fileSize: Long, maxSize: Long): Boolean = 
    fileSize > 0 && fileSize <= maxSize
  
  /**
   * 리스트 크기 검증
   */
  fun isValidListSize(list: List<*>?, maxSize: Int): Boolean {
    if (list == null) return true
    return list.size <= maxSize
  }
  
  /**
   * ID 형식 검증
   */
  fun isValidIdFormat(id: String?, prefix: String): Boolean {
    if (id.isNullOrBlank()) return false
    return id.startsWith(prefix) && 
           id.length > prefix.length && 
           id.matches(Regex("^$prefix[a-zA-Z0-9_-]+$"))
  }
}