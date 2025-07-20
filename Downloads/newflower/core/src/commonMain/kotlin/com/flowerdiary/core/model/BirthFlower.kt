package com.flowerdiary.core.model

import com.flowerdiary.core.types.FlowerDate
import com.flowerdiary.core.types.FlowerId
import com.flowerdiary.core.types.MediaId
import kotlinx.serialization.Serializable

/**
 * 탄생화 도메인 모델
 * 각 날짜별 탄생화 정보
 */
@Serializable
data class BirthFlower(
  val id: FlowerId,
  val name: String,
  val date: FlowerDate,
  val meaning: String,
  val description: String,
  val imageId: MediaId,
  val colorHex: String,
  val isUnlocked: Boolean = false,
  val unlockCount: Int = 0
) {
  init {
    require(name.isNotBlank()) { "Flower name cannot be blank" }
    require(meaning.isNotBlank()) { "Flower meaning cannot be blank" }
    require(description.isNotBlank()) { "Flower description cannot be blank" }
    require(name.length <= MAX_NAME_LENGTH) { 
      "Name too long: ${name.length} > $MAX_NAME_LENGTH" 
    }
    require(meaning.length <= MAX_MEANING_LENGTH) { 
      "Meaning too long: ${meaning.length} > $MAX_MEANING_LENGTH" 
    }
    require(description.length <= MAX_DESCRIPTION_LENGTH) { 
      "Description too long: ${description.length} > $MAX_DESCRIPTION_LENGTH" 
    }
    require(isValidColorHex()) { "Invalid color hex format: $colorHex" }
    require(unlockCount >= 0) { "Unlock count cannot be negative: $unlockCount" }
  }

  private fun isValidColorHex(): Boolean {
    return colorHex.matches(Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$"))
  }

  /**
   * 탄생화 잠금 해제
   * 잠금 해제 횟수 증가
   */
  fun unlock(): BirthFlower = copy(
    isUnlocked = true,
    unlockCount = unlockCount + 1
  )

  /**
   * 잠금 해제 여부 확인
   */
  fun canBeUnlocked(): Boolean = !isUnlocked

  companion object {
    const val MAX_NAME_LENGTH = 50
    const val MAX_MEANING_LENGTH = 200
    const val MAX_DESCRIPTION_LENGTH = 1000
  }
}