package com.flowerdiary.feature.diary.store.action

import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import com.flowerdiary.feature.diary.store.state.SortOrder
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
sealed class DiaryListAction {

  @Serializable
  data object Initialize : DiaryListAction()

  @Serializable
  data object LoadDiaries : DiaryListAction()

  @Serializable
  data object RefreshDiaries : DiaryListAction()

  @Serializable
  data object LoadMoreDiaries : DiaryListAction()

  @Serializable
  data class LoadDiariesByMonth(val year: Int, val month: Int) : DiaryListAction()

  @Serializable
  data class LoadDiariesByDateRange(
    val startDate: LocalDate,
    val endDate: LocalDate
  ) : DiaryListAction()

  @Serializable
  data class SearchDiaries(val query: String) : DiaryListAction()

  @Serializable
  data class FilterByStatus(val status: DiaryStatus?) : DiaryListAction()

  @Serializable
  data class SortDiaries(val sortOrder: SortOrder) : DiaryListAction()

  @Serializable
  data class SelectDiary(val diaryId: DiaryId) : DiaryListAction()

  @Serializable
  data class DeselectDiary(val diaryId: DiaryId) : DiaryListAction()

  @Serializable
  data class DeleteDiary(val diaryId: DiaryId) : DiaryListAction()

  @Serializable
  data class DeleteMultipleDiaries(val diaryIds: List<DiaryId>) : DiaryListAction()

  @Serializable
  data class ArchiveDiary(val diaryId: DiaryId) : DiaryListAction()

  @Serializable
  data class PublishDiary(val diaryId: DiaryId) : DiaryListAction()

  @Serializable
  data object ClearSearch : DiaryListAction()

  @Serializable
  data object ClearFilter : DiaryListAction()

  @Serializable
  data object ClearSelection : DiaryListAction()

  @Serializable
  data object ClearError : DiaryListAction()

  @Serializable
  data object RetryLoad : DiaryListAction()

  @Serializable
  data class ShowDeleteConfirmation(val diaryId: DiaryId) : DiaryListAction()

  @Serializable
  data object HideDeleteConfirmation : DiaryListAction()

  @Serializable
  data object ConfirmDelete : DiaryListAction()
}