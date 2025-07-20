package com.flowerdiary.core.types

import kotlinx.serialization.Serializable

/**
 * 사용자의 고유 식별자
 * 단일 사용자 앱이지만 확장성을 위한 타입
 */
@Serializable
data class UserId(override val value: String) : EntityId {
  init {
    require(value.startsWith(PREFIX)) { 
      "UserId must start with '$PREFIX': $value" 
    }
    require(EntityId.validateFormat(value, PREFIX)) { 
      "Invalid UserId format: $value" 
    }
    require(value.length >= MIN_LENGTH) { 
      "UserId too short: $value" 
    }
  }

  companion object {
    private const val PREFIX = "user-"
    private const val MIN_LENGTH = 8
    private const val DEFAULT_USER_SUFFIX = "default"

    /**
     * 기본 사용자 ID 생성
     * 단일 사용자 앱에서 사용
     */
    fun createDefault(): UserId = UserId("$PREFIX$DEFAULT_USER_SUFFIX")

    /**
     * 커스텀 사용자 ID 생성
     * @param suffix 사용자 구분을 위한 접미사
     */
    fun create(suffix: String): UserId {
      require(suffix.isNotBlank()) { "User suffix cannot be blank" }
      return UserId("$PREFIX$suffix")
    }
  }
}