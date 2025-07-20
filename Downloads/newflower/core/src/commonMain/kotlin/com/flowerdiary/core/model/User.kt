package com.flowerdiary.core.model

import com.flowerdiary.core.types.MediaTimestamp
import com.flowerdiary.core.types.UserId
import kotlinx.serialization.Serializable

/**
 * 사용자 도메인 모델
 * 단일 사용자 앱에서 사용자 정보
 */
@Serializable
data class User(
  val id: UserId,
  val nickname: String,
  val profileImagePath: String? = null,
  val createdAt: MediaTimestamp,
  val lastActiveAt: MediaTimestamp,
  val totalDiariesCount: Int = 0,
  val totalFlowersUnlocked: Int = 0
) {
  init {
    require(nickname.isNotBlank()) { "Nickname cannot be blank" }
    require(nickname.length <= MAX_NICKNAME_LENGTH) { 
      "Nickname too long: ${nickname.length} > $MAX_NICKNAME_LENGTH" 
    }
    require(totalDiariesCount >= 0) { 
      "Total diaries count cannot be negative: $totalDiariesCount" 
    }
    require(totalFlowersUnlocked >= 0) { 
      "Total flowers unlocked cannot be negative: $totalFlowersUnlocked" 
    }
    require(profileImagePath?.isNotBlank() ?: true) { 
      "Profile image path cannot be blank if provided" 
    }
  }

  /**
   * 프로필 이미지 유무 확인
   */
  fun hasProfileImage(): Boolean = !profileImagePath.isNullOrBlank()

  /**
   * 일기 작성 횟수 증가
   */
  fun incrementDiaryCount(): User = copy(totalDiariesCount = totalDiariesCount + 1)

  /**
   * 탄생화 잠금 해제 횟수 증가
   */
  fun incrementFlowerUnlockCount(): User = copy(
    totalFlowersUnlocked = totalFlowersUnlocked + 1
  )

  /**
   * 마지막 활동 시간 업데이트
   */
  fun updateLastActive(timestamp: MediaTimestamp): User = copy(lastActiveAt = timestamp)

  companion object {
    const val MAX_NICKNAME_LENGTH = 20
    const val DEFAULT_NICKNAME = "꽃일기 사용자"
  }
}