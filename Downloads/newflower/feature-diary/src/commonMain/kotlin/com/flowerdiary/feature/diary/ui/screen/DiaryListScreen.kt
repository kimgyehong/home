package com.flowerdiary.feature.diary.ui.screen

import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import com.flowerdiary.feature.diary.store.action.DiaryListAction
import com.flowerdiary.feature.diary.store.state.DiaryListState
import com.flowerdiary.feature.diary.store.state.SortOrder
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDate

data class DiaryListScreen(
  val state: DiaryListState,
  val onAction: (DiaryListAction) -> Unit
) {

  fun initialize() {
    onAction(DiaryListAction.Initialize)
  }

  fun refreshDiaries() {
    onAction(DiaryListAction.RefreshDiaries)
  }

  fun loadMoreDiaries() {
    onAction(DiaryListAction.LoadMoreDiaries)
  }

  fun searchDiaries(query: String) {
    onAction(DiaryListAction.SearchDiaries(query))
  }

  fun filterByStatus(status: DiaryStatus?) {
    onAction(DiaryListAction.FilterByStatus(status))
  }

  fun sortDiaries(sortOrder: SortOrder) {
    onAction(DiaryListAction.SortDiaries(sortOrder))
  }

  fun selectDiary(diaryId: DiaryId) {
    onAction(DiaryListAction.SelectDiary(diaryId))
  }

  fun deleteDiary(diaryId: DiaryId) {
    onAction(DiaryListAction.DeleteDiary(diaryId))
  }

  fun archiveDiary(diaryId: DiaryId) {
    onAction(DiaryListAction.ArchiveDiary(diaryId))
  }

  fun publishDiary(diaryId: DiaryId) {
    onAction(DiaryListAction.PublishDiary(diaryId))
  }

  fun clearSearch() {
    onAction(DiaryListAction.ClearSearch)
  }

  fun clearFilter() {
    onAction(DiaryListAction.ClearFilter)
  }

  fun retryLoad() {
    onAction(DiaryListAction.RetryLoad)
  }

  fun loadDiariesByMonth(year: Int, month: Int) {
    onAction(DiaryListAction.LoadDiariesByMonth(year, month))
  }

  fun loadDiariesByDateRange(startDate: LocalDate, endDate: LocalDate) {
    onAction(DiaryListAction.LoadDiariesByDateRange(startDate, endDate))
  }

  val diaries: ImmutableList<Diary>
    get() = state.filteredDiaries

  val isLoading: Boolean
    get() = state.isLoading

  val isRefreshing: Boolean
    get() = state.isRefreshing

  val isLoadingMore: Boolean
    get() = state.isLoadingMore

  val hasError: Boolean
    get() = state.hasError()

  val errorMessage: String?
    get() = state.errorMessage

  val hasNextPage: Boolean
    get() = state.hasNextPage

  val searchQuery: String
    get() = state.searchQuery

  val selectedStatus: DiaryStatus?
    get() = state.selectedStatus

  val sortOrder: SortOrder
    get() = state.sortOrder

  val isEmpty: Boolean
    get() = state.filteredDiaries.isEmpty() && !state.isLoading

  val isSearching: Boolean
    get() = state.searchQuery.isNotBlank()

  val isFiltered: Boolean
    get() = state.selectedStatus != null

  fun canLoadMore(): Boolean {
    return !isLoading && !isLoadingMore && hasNextPage
  }

  fun getDisplayTitle(): String {
    return when {
      isSearching && isFiltered -> "Search: \"$searchQuery\" in ${selectedStatus?.name}"
      isSearching -> "Search: \"$searchQuery\""
      isFiltered -> "${selectedStatus?.name} Diaries"
      else -> "All Diaries"
    }
  }

  fun getDiaryCount(): Int {
    return diaries.size
  }

  fun getTotalDiaryCount(): Int {
    return state.diaries.size
  }
}