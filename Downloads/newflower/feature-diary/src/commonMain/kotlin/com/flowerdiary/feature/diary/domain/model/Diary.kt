package com.flowerdiary.feature.diary.domain.model

import com.flowerdiary.core.types.FlowerId
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Diary(
  val id: DiaryId,
  val entry: DiaryEntry,
  val birthFlowerId: FlowerId?,
  val status: DiaryStatus = DiaryStatus.Draft
) {

  init {
    require(entry.date.toString().isNotBlank()) {
      "Diary date cannot be blank"
    }
  }

  fun updateEntry(newTitle: String, newContent: String): Diary {
    return copy(
      entry = entry.updateContent(newTitle, newContent),
      status = if (newContent.isNotBlank()) DiaryStatus.Written else DiaryStatus.Draft
    )
  }

  fun publish(): Diary {
    require(entry.content.isNotBlank()) {
      "Cannot publish diary with empty content"
    }
    return copy(status = DiaryStatus.Published)
  }

  fun archive(): Diary {
    return copy(status = DiaryStatus.Archived)
  }

  fun isWritten(): Boolean {
    return entry.content.isNotBlank()
  }

  fun isEmpty(): Boolean {
    return entry.isEmpty()
  }

  companion object {
    
    fun create(date: LocalDate, birthFlowerId: FlowerId? = null): Diary {
      return Diary(
        id = DiaryId.generate(),
        entry = DiaryEntry.empty(date),
        birthFlowerId = birthFlowerId
      )
    }
    
    fun fromEntry(entry: DiaryEntry, birthFlowerId: FlowerId? = null): Diary {
      return Diary(
        id = DiaryId.generate(),
        entry = entry,
        birthFlowerId = birthFlowerId,
        status = if (entry.content.isNotBlank()) DiaryStatus.Written else DiaryStatus.Draft
      )
    }
  }
}