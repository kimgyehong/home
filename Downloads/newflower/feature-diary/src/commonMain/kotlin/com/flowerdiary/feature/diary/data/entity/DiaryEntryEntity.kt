package com.flowerdiary.feature.diary.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class DiaryEntryEntity(
  val id: String,
  val diaryId: String,
  val title: String,
  val content: String,
  val date: String,
  val wordCount: Int = 0,
  val characterCount: Int = 0,
  val createdAt: Long = System.currentTimeMillis(),
  val updatedAt: Long = System.currentTimeMillis(),
  val version: Int = CURRENT_VERSION
) {

  fun isValid(): Boolean {
    return id.isNotBlank() &&
           diaryId.isNotBlank() &&
           date.isNotBlank() &&
           createdAt > 0 &&
           updatedAt >= createdAt &&
           version > 0 &&
           wordCount >= 0 &&
           characterCount >= 0
  }

  fun isEmpty(): Boolean {
    return title.isBlank() && content.isBlank()
  }

  fun updateCounts(): DiaryEntryEntity {
    val newWordCount = countWords(content)
    val newCharacterCount = content.length
    
    return copy(
      wordCount = newWordCount,
      characterCount = newCharacterCount,
      updatedAt = System.currentTimeMillis()
    )
  }

  fun updateContent(newTitle: String, newContent: String): DiaryEntryEntity {
    return copy(
      title = newTitle,
      content = newContent,
      updatedAt = System.currentTimeMillis()
    ).updateCounts()
  }

  fun getContentPreview(maxLength: Int = DEFAULT_PREVIEW_LENGTH): String {
    return if (content.length <= maxLength) {
      content
    } else {
      content.take(maxLength - ELLIPSIS_LENGTH) + "..."
    }
  }

  private fun countWords(text: String): Int {
    if (text.isBlank()) return 0
    return text.trim().split(Regex("\\s+")).size
  }

  companion object {
    private const val CURRENT_VERSION = 1
    private const val DEFAULT_PREVIEW_LENGTH = 100
    private const val ELLIPSIS_LENGTH = 3
    
    fun empty(diaryId: String, date: String): DiaryEntryEntity {
      val now = System.currentTimeMillis()
      return DiaryEntryEntity(
        id = "${diaryId}_entry",
        diaryId = diaryId,
        title = "",
        content = "",
        date = date,
        createdAt = now,
        updatedAt = now
      )
    }
  }
}