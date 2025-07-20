package com.flowerdiary.feature.diary.data.mapper

import com.flowerdiary.feature.diary.data.entity.DiaryStatusEntity
import com.flowerdiary.feature.diary.domain.model.DiaryStatus

object DiaryStatusMapper {

  fun toDomain(entity: DiaryStatusEntity): DiaryStatus {
    return when (entity.status) {
      DiaryStatusEntity.STATUS_DRAFT -> DiaryStatus.Draft
      DiaryStatusEntity.STATUS_WRITTEN -> DiaryStatus.Written
      DiaryStatusEntity.STATUS_PUBLISHED -> DiaryStatus.Published
      DiaryStatusEntity.STATUS_ARCHIVED -> DiaryStatus.Archived
      else -> DiaryStatus.Draft
    }
  }

  fun toEntity(domain: DiaryStatus, diaryId: String): DiaryStatusEntity {
    val statusString = mapToEntityStatus(domain)
    return DiaryStatusEntity.create(diaryId, statusString)
  }

  fun updateEntityFromDomain(entity: DiaryStatusEntity, domain: DiaryStatus): DiaryStatusEntity {
    val newStatus = mapToEntityStatus(domain)
    return if (entity.canTransitionTo(newStatus)) {
      entity.updateStatus(newStatus)
    } else {
      entity
    }
  }

  fun canTransition(from: DiaryStatus, to: DiaryStatus): Boolean {
    val fromEntity = mapToEntityStatus(from)
    val toEntity = mapToEntityStatus(to)
    val tempEntity = DiaryStatusEntity.create("temp", fromEntity)
    return tempEntity.canTransitionTo(toEntity)
  }

  fun getDisplayName(status: DiaryStatus): String {
    return when (status) {
      DiaryStatus.Draft -> "임시저장"
      DiaryStatus.Written -> "작성완료"
      DiaryStatus.Published -> "게시됨"
      DiaryStatus.Archived -> "보관됨"
    }
  }

  fun getStatusPriority(status: DiaryStatus): Int {
    return when (status) {
      DiaryStatus.Draft -> 1
      DiaryStatus.Written -> 2
      DiaryStatus.Published -> 3
      DiaryStatus.Archived -> 4
    }
  }

  fun isValidTransition(entity: DiaryStatusEntity, newStatus: DiaryStatus): Boolean {
    val newStatusString = mapToEntityStatus(newStatus)
    return entity.canTransitionTo(newStatusString)
  }

  private fun mapToEntityStatus(domain: DiaryStatus): String {
    return when (domain) {
      DiaryStatus.Draft -> DiaryStatusEntity.STATUS_DRAFT
      DiaryStatus.Written -> DiaryStatusEntity.STATUS_WRITTEN
      DiaryStatus.Published -> DiaryStatusEntity.STATUS_PUBLISHED
      DiaryStatus.Archived -> DiaryStatusEntity.STATUS_ARCHIVED
    }
  }

  fun createInitialEntity(diaryId: String): DiaryStatusEntity {
    return DiaryStatusEntity.create(diaryId, DiaryStatusEntity.STATUS_DRAFT)
  }

  fun getAllValidStatuses(): List<DiaryStatus> {
    return listOf(
      DiaryStatus.Draft,
      DiaryStatus.Written,
      DiaryStatus.Published,
      DiaryStatus.Archived
    )
  }
}