package com.flowerdiary.feature.diary.data.mapper

import com.flowerdiary.feature.diary.data.entity.DiaryEntryEntity
import com.flowerdiary.feature.diary.domain.model.DiaryEntry
import kotlinx.datetime.LocalDate

object DiaryEntryMapper {

  fun toDomain(entity: DiaryEntryEntity): DiaryEntry {
    return DiaryEntry(
      title = entity.title,
      content = entity.content,
      date = LocalDate.parse(entity.date),
      createdAt = entity.createdAt,
      updatedAt = entity.updatedAt
    )
  }

  fun toEntity(domain: DiaryEntry, diaryId: String): DiaryEntryEntity {
    return DiaryEntryEntity(
      id = "${diaryId}_entry",
      diaryId = diaryId,
      title = domain.title,
      content = domain.content,
      date = domain.date.toString(),
      wordCount = countWords(domain.content),
      characterCount = domain.content.length,
      createdAt = domain.createdAt,
      updatedAt = domain.updatedAt
    )
  }

  fun updateEntityFromDomain(entity: DiaryEntryEntity, domain: DiaryEntry): DiaryEntryEntity {
    return entity.copy(
      title = domain.title,
      content = domain.content,
      date = domain.date.toString(),
      wordCount = countWords(domain.content),
      characterCount = domain.content.length,
      updatedAt = domain.updatedAt
    )
  }

  fun toDomainList(entities: List<DiaryEntryEntity>): List<DiaryEntry> {
    return entities.map { toDomain(it) }
  }

  fun toEntityList(domains: List<DiaryEntry>, diaryIds: List<String>): List<DiaryEntryEntity> {
    require(domains.size == diaryIds.size) { "Domain and ID lists must have the same size" }
    return domains.mapIndexed { index, domain ->
      toEntity(domain, diaryIds[index])
    }
  }

  fun mergeDomainChanges(original: DiaryEntry, updated: DiaryEntry): DiaryEntry {
    return updated.copy(
      createdAt = original.createdAt,
      updatedAt = System.currentTimeMillis()
    )
  }

  fun createEmptyEntity(diaryId: String, date: LocalDate): DiaryEntryEntity {
    val emptyEntry = DiaryEntry.empty(date)
    return toEntity(emptyEntry, diaryId)
  }

  private fun countWords(content: String): Int {
    if (content.isBlank()) return 0
    return content.trim().split(Regex("\\s+")).size
  }

  fun getContentPreview(entity: DiaryEntryEntity, maxLength: Int = DEFAULT_PREVIEW_LENGTH): String {
    return entity.getContentPreview(maxLength)
  }

  private companion object {
    private const val DEFAULT_PREVIEW_LENGTH = 100
  }
}