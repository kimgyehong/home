package com.flowerdiary.feature.diary.ui.screen

import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import com.flowerdiary.feature.diary.store.action.DiaryAction
import com.flowerdiary.feature.diary.store.state.DiaryDetailState
import kotlinx.datetime.LocalDate

data class DiaryDetailScreen(
  val state: DiaryDetailState,
  val onAction: (DiaryAction) -> Unit
) {

  fun loadDiary(diaryId: DiaryId) {
    // Load diary for detail view
  }

  fun refreshDiary() {
    // Refresh current diary
  }

  fun shareDiary() {
    // Share diary content
  }

  fun exportDiary() {
    // Export diary to file
  }

  fun printDiary() {
    // Print diary content
  }

  fun editDiary() {
    // Navigate to edit mode
  }

  fun deleteDiary() {
    // Delete current diary
  }

  fun archiveDiary() {
    // Archive current diary
  }

  fun publishDiary() {
    // Publish current diary
  }

  fun toggleFavorite() {
    // Toggle diary favorite status
  }

  fun addBookmark() {
    // Add bookmark to current position
  }

  fun navigateToDate(date: LocalDate) {
    // Navigate to specific date
  }

  fun searchInDiary(query: String) {
    // Search within diary content
  }

  fun clearSearch() {
    // Clear search results
  }

  fun retryLoad() {
    // Retry loading diary
  }

  fun clearError() {
    // Clear error state
  }

  val diary: Diary?
    get() = state.diary

  val title: String
    get() = diary?.entry?.title ?: ""

  val content: String
    get() = diary?.entry?.content ?: ""

  val date: LocalDate
    get() = diary?.entry?.date ?: LocalDate(2024, 1, 1)

  val status: DiaryStatus
    get() = diary?.status ?: DiaryStatus.Draft

  val isLoading: Boolean
    get() = state.isLoading

  val hasError: Boolean
    get() = state.hasError()

  val errorMessage: String?
    get() = state.errorMessage

  val isEmpty: Boolean
    get() = diary == null && !isLoading

  val canEdit: Boolean
    get() = diary != null && status != DiaryStatus.Archived

  val canDelete: Boolean
    get() = diary != null

  val canShare: Boolean
    get() = diary != null && status == DiaryStatus.Published

  val canArchive: Boolean
    get() = diary != null && status != DiaryStatus.Archived

  val canPublish: Boolean
    get() = diary != null && status == DiaryStatus.Draft

  val wordCount: Int
    get() = content.split(Regex("\\s+")).filter { it.isNotBlank() }.size

  val characterCount: Int
    get() = content.length

  val readingTimeMinutes: Int
    get() = (wordCount / WORDS_PER_MINUTE).coerceAtLeast(1)

  fun getCreatedDate(): LocalDate? {
    return diary?.entry?.createdAt?.let { 
      LocalDate(2024, 1, 1) // Placeholder conversion
    }
  }

  fun getUpdatedDate(): LocalDate? {
    return diary?.entry?.updatedAt?.let {
      LocalDate(2024, 1, 1) // Placeholder conversion
    }
  }

  fun getBirthFlowerName(): String? {
    return diary?.birthFlowerId?.value
  }

  fun getStatusDisplayName(): String {
    return when (status) {
      DiaryStatus.Draft -> "Draft"
      DiaryStatus.Published -> "Published"
      DiaryStatus.Archived -> "Archived"
    }
  }

  fun hasContent(): Boolean {
    return content.isNotBlank()
  }

  fun isLongContent(): Boolean {
    return wordCount > LONG_CONTENT_THRESHOLD
  }

  companion object {
    private const val WORDS_PER_MINUTE = 200
    private const val LONG_CONTENT_THRESHOLD = 500
  }
}