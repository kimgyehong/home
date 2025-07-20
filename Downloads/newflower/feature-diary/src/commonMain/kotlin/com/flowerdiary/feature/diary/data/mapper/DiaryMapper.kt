package com.flowerdiary.feature.diary.data.mapper

import com.flowerdiary.core.types.FlowerId
import com.flowerdiary.feature.diary.data.entity.DiaryEntity
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryEntry
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import kotlinx.datetime.LocalDate

object DiaryMapper {

  fun toDomain(entity: DiaryEntity): Diary {
    return Diary(
      id = DiaryId.fromString(entity.id),
      entry = DiaryEntry(
        title = entity.title,
        content = entity.content,
        date = LocalDate.parse(entity.date),
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt
      ),
      birthFlowerId = entity.birthFlowerId?.let { FlowerId.fromString(it) },
      status = DiaryStatus.fromString(entity.status)
    )
  }

  fun toEntity(domain: Diary): DiaryEntity {
    return DiaryEntity(
      id = domain.id.value,
      title = domain.entry.title,
      content = domain.entry.content,
      date = domain.entry.date.toString(),
      birthFlowerId = domain.birthFlowerId?.value,
      status = domain.status.toString().lowercase(),
      createdAt = domain.entry.createdAt,
      updatedAt = domain.entry.updatedAt
    )
  }

  fun toDomainList(entities: List<DiaryEntity>): List<Diary> {
    return entities.map { toDomain(it) }
  }

  fun toEntityList(domains: List<Diary>): List<DiaryEntity> {
    return domains.map { toEntity(it) }
  }

  fun updateEntityFromDomain(entity: DiaryEntity, domain: Diary): DiaryEntity {
    return entity.copy(
      title = domain.entry.title,
      content = domain.entry.content,
      date = domain.entry.date.toString(),
      birthFlowerId = domain.birthFlowerId?.value,
      status = domain.status.toString().lowercase(),
      updatedAt = domain.entry.updatedAt
    )
  }

  fun mergeDomainChanges(original: Diary, updated: Diary): Diary {
    return original.copy(
      entry = updated.entry,
      birthFlowerId = updated.birthFlowerId,
      status = updated.status
    )
  }
}