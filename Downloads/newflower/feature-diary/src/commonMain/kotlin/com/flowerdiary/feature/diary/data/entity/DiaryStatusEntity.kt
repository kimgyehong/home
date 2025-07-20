package com.flowerdiary.feature.diary.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class DiaryStatusEntity(
  val id: String,
  val diaryId: String,
  val status: String,
  val previousStatus: String?,
  val statusChangedAt: Long = System.currentTimeMillis(),
  val createdAt: Long = System.currentTimeMillis(),
  val version: Int = CURRENT_VERSION
) {

  fun isValid(): Boolean {
    return id.isNotBlank() &&
           diaryId.isNotBlank() &&
           status.isNotBlank() &&
           status in VALID_STATUSES &&
           statusChangedAt > 0 &&
           createdAt > 0 &&
           version > 0
  }

  fun canTransitionTo(newStatus: String): Boolean {
    if (newStatus !in VALID_STATUSES) return false
    
    return when (status) {
      STATUS_DRAFT -> newStatus in setOf(STATUS_WRITTEN, STATUS_ARCHIVED)
      STATUS_WRITTEN -> newStatus in setOf(STATUS_PUBLISHED, STATUS_ARCHIVED, STATUS_DRAFT)
      STATUS_PUBLISHED -> newStatus in setOf(STATUS_ARCHIVED)
      STATUS_ARCHIVED -> newStatus in setOf(STATUS_WRITTEN, STATUS_PUBLISHED)
      else -> false
    }
  }

  fun updateStatus(newStatus: String): DiaryStatusEntity {
    require(canTransitionTo(newStatus)) {
      "Cannot transition from $status to $newStatus"
    }
    
    return copy(
      status = newStatus,
      previousStatus = status,
      statusChangedAt = System.currentTimeMillis()
    )
  }

  fun getDisplayName(): String {
    return when (status) {
      STATUS_DRAFT -> "임시저장"
      STATUS_WRITTEN -> "작성완료"
      STATUS_PUBLISHED -> "게시됨"
      STATUS_ARCHIVED -> "보관됨"
      else -> "알 수 없음"
    }
  }

  fun isEditable(): Boolean {
    return status in setOf(STATUS_DRAFT, STATUS_WRITTEN)
  }

  companion object {
    private const val CURRENT_VERSION = 1
    
    const val STATUS_DRAFT = "draft"
    const val STATUS_WRITTEN = "written"
    const val STATUS_PUBLISHED = "published"
    const val STATUS_ARCHIVED = "archived"
    
    private val VALID_STATUSES = setOf(
      STATUS_DRAFT, STATUS_WRITTEN, STATUS_PUBLISHED, STATUS_ARCHIVED
    )
    
    fun create(diaryId: String, status: String = STATUS_DRAFT): DiaryStatusEntity {
      val now = System.currentTimeMillis()
      return DiaryStatusEntity(
        id = "${diaryId}_status",
        diaryId = diaryId,
        status = status,
        previousStatus = null,
        statusChangedAt = now,
        createdAt = now
      )
    }
  }
}