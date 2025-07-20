package com.flowerdiary.core.types

import kotlinx.serialization.Serializable

/**
 * 모든 엔티티 ID의 기본 인터페이스
 * 타입 안전성과 직렬화를 보장합니다
 */
@Serializable
sealed interface EntityId {
  val value: String

  /**
   * ID 유효성 검증
   * @return 유효하면 true, 아니면 false
   */
  fun isValid(): Boolean = value.isNotBlank() && value.length >= MIN_ID_LENGTH

  companion object {
    const val MIN_ID_LENGTH = 3
    const val MAX_ID_LENGTH = 50
    
    /**
     * ID 생성 규칙 검증
     * @param value 검증할 ID 값
     * @param prefix 필수 접두사
     * @return 유효하면 true
     */
    fun validateFormat(value: String, prefix: String): Boolean {
      return value.startsWith(prefix) && 
             value.length in MIN_ID_LENGTH..MAX_ID_LENGTH &&
             value.matches(Regex("^$prefix[a-zA-Z0-9_-]+$"))
    }
  }
}