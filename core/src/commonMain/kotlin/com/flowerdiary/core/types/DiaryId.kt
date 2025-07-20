package com.flowerdiary.core.types

import kotlinx.serialization.Serializable

/**
 * 일기 엔티티의 고유 식별자
 * 도메인 핵심 타입으로 안전성 우선
 */
@Serializable
data class DiaryId(override val value: String) : EntityId {
  init {
    require(value.startsWith(PREFIX)) { 
      "DiaryId must start with '$PREFIX': $value" 
    }
    require(EntityId.validateFormat(value, PREFIX)) { 
      "Invalid DiaryId format: $value" 
    }
    require(value.length >= MIN_LENGTH) { 
      "DiaryId too short: $value" 
    }
  }

  companion object {
    private const val PREFIX = "diary-"
    private const val MIN_LENGTH = 10

    /**
     * 새로운 DiaryId 생성
     * @param suffix 접미사 (기본값: 현재 시간 기반)
     * @return 생성된 DiaryId
     */
    fun create(suffix: String = generateSuffix()): DiaryId {
      return DiaryId("$PREFIX$suffix")
    }

    private fun generateSuffix(): String {
      return kotlin.random.Random.nextLong(1000000, 9999999).toString()
    }
  }
}