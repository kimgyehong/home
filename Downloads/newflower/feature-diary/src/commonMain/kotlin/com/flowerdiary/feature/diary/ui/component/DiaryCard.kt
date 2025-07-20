package com.flowerdiary.feature.diary.ui.component

import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import kotlinx.datetime.LocalDate

data class DiaryCard(
  val diary: Diary,
  val isSelected: Boolean = false,
  val showPreview: Boolean = true,
  val maxPreviewLength: Int = 150,
  val onClick: (DiaryId) -> Unit = {},
  val onLongClick: (DiaryId) -> Unit = {},
  val onEditClick: (DiaryId) -> Unit = {},
  val onDeleteClick: (DiaryId) -> Unit = {},
  val onArchiveClick: (DiaryId) -> Unit = {},
  val onPublishClick: (DiaryId) -> Unit = {}
) {

  val title: String
    get() = diary.entry.title.ifBlank { "Untitled" }

  val content: String
    get() = diary.entry.content

  val previewContent: String
    get() = if (showPreview && content.length > maxPreviewLength) {
      content.take(maxPreviewLength) + "..."
    } else {
      content
    }

  val date: LocalDate
    get() = diary.entry.date

  val status: DiaryStatus
    get() = diary.status

  val statusText: String
    get() = when (status) {
      DiaryStatus.Draft -> "Draft"
      DiaryStatus.Published -> "Published"
      DiaryStatus.Archived -> "Archived"
    }

  val statusColor: String
    get() = when (status) {
      DiaryStatus.Draft -> "#FFA726"
      DiaryStatus.Published -> "#66BB6A"
      DiaryStatus.Archived -> "#BDBDBD"
    }

  val birthFlowerName: String?
    get() = diary.birthFlowerId?.value

  val wordCount: Int
    get() = content.split(Regex("\\s+")).filter { it.isNotBlank() }.size

  val characterCount: Int
    get() = content.length

  val isEmpty: Boolean
    get() = title.isBlank() && content.isBlank()

  val hasFlower: Boolean
    get() = diary.birthFlowerId != null

  val canEdit: Boolean
    get() = status != DiaryStatus.Archived

  val canPublish: Boolean
    get() = status == DiaryStatus.Draft

  val canArchive: Boolean
    get() = status != DiaryStatus.Archived

  val canDelete: Boolean
    get() = true

  fun handleClick() {
    onClick(diary.id)
  }

  fun handleLongClick() {
    onLongClick(diary.id)
  }

  fun handleEditClick() {
    if (canEdit) {
      onEditClick(diary.id)
    }
  }

  fun handleDeleteClick() {
    if (canDelete) {
      onDeleteClick(diary.id)
    }
  }

  fun handleArchiveClick() {
    if (canArchive) {
      onArchiveClick(diary.id)
    }
  }

  fun handlePublishClick() {
    if (canPublish) {
      onPublishClick(diary.id)
    }
  }

  fun getFormattedDate(): String {
    return "${date.year}.${date.monthNumber.toString().padStart(2, '0')}.${date.dayOfMonth.toString().padStart(2, '0')}"
  }

  fun getRelativeDate(): String {
    // Simplified relative date calculation
    return getFormattedDate()
  }

  fun getContentSummary(): String {
    return if (content.isBlank()) {
      "No content"
    } else {
      "$wordCount words, $characterCount characters"
    }
  }

  fun getLastModified(): String {
    return "Modified: ${getFormattedDate()}"
  }

  fun getTags(): List<String> {
    // Extract hashtags from content
    val hashtagRegex = Regex("#\\w+")
    return hashtagRegex.findAll(content).map { it.value }.toList()
  }

  fun hasImage(): Boolean {
    // Check if content contains image references
    return content.contains("![") || content.contains("<img")
  }

  fun getEstimatedReadingTime(): String {
    val minutes = (wordCount / WORDS_PER_MINUTE).coerceAtLeast(1)
    return "${minutes}min read"
  }

  companion object {
    private const val WORDS_PER_MINUTE = 200
    
    fun empty(): DiaryCard {
      return DiaryCard(
        diary = Diary(
          id = DiaryId("diary_empty"),
          entry = com.flowerdiary.feature.diary.domain.model.DiaryEntry(
            title = "",
            content = "",
            date = LocalDate(2024, 1, 1),
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
          ),
          birthFlowerId = null,
          status = DiaryStatus.Draft
        )
      )
    }
  }
}