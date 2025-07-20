package com.flowerdiary.feature.diary.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DiaryEntry(
  val title: String,
  val content: String,
  val date: LocalDate,
  val createdAt: Long = System.currentTimeMillis(),
  val updatedAt: Long = System.currentTimeMillis()
) {

  init {
    require(title.isNotBlank()) { 
      "Title cannot be blank" 
    }
    require(title.length <= MAX_TITLE_LENGTH) { 
      "Title cannot exceed $MAX_TITLE_LENGTH characters" 
    }
    require(content.length <= MAX_CONTENT_LENGTH) { 
      "Content cannot exceed $MAX_CONTENT_LENGTH characters" 
    }
    require(createdAt > 0) { 
      "Created timestamp must be positive" 
    }
    require(updatedAt >= createdAt) { 
      "Updated timestamp cannot be before created timestamp" 
    }
  }

  fun updateContent(newTitle: String, newContent: String): DiaryEntry {
    return copy(
      title = newTitle,
      content = newContent,
      updatedAt = System.currentTimeMillis()
    )
  }

  fun isEmpty(): Boolean {
    return title.isBlank() && content.isBlank()
  }

  companion object {
    private const val MAX_TITLE_LENGTH = 100
    private const val MAX_CONTENT_LENGTH = 10000
    
    fun empty(date: LocalDate): DiaryEntry {
      return DiaryEntry(
        title = "",
        content = "",
        date = date
      )
    }
  }
}