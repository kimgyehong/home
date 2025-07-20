package com.flowerdiary.feature.diary.store.state

import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class DiaryListState(
  val diaries: ImmutableList<Diary> = persistentListOf(),
  val filteredDiaries: ImmutableList<Diary> = persistentListOf(),
  val isLoading: Boolean = false,
  val isRefreshing: Boolean = false,
  val errorMessage: String? = null,
  val selectedStatus: DiaryStatus? = null,
  val searchQuery: String = "",
  val sortOrder: SortOrder = SortOrder.DATE_DESC,
  val hasNextPage: Boolean = false,
  val isLoadingMore: Boolean = false
) {

  fun isEmpty(): Boolean {
    return diaries.isEmpty()
  }

  fun hasSearchResults(): Boolean {
    return searchQuery.isNotBlank() && filteredDiaries.isNotEmpty()
  }

  fun isSearching(): Boolean {
    return searchQuery.isNotBlank()
  }

  fun hasError(): Boolean {
    return errorMessage != null
  }

  fun getDisplayedDiaries(): ImmutableList<Diary> {
    return if (isSearching() || selectedStatus != null) {
      filteredDiaries
    } else {
      diaries
    }
  }

  fun getDiaryCount(): Int {
    return getDisplayedDiaries().size
  }

  companion object {
    
    fun loading(): DiaryListState {
      return DiaryListState(isLoading = true)
    }
    
    fun error(message: String): DiaryListState {
      return DiaryListState(errorMessage = message)
    }
    
    fun empty(): DiaryListState {
      return DiaryListState()
    }
  }
}

@Serializable
enum class SortOrder(val displayName: String) {
  DATE_ASC("날짜 오름차순"),
  DATE_DESC("날짜 내림차순"),
  TITLE_ASC("제목 오름차순"),
  TITLE_DESC("제목 내림차순"),
  UPDATED_ASC("수정일 오름차순"),
  UPDATED_DESC("수정일 내림차순");

  fun isAscending(): Boolean {
    return this in listOf(DATE_ASC, TITLE_ASC, UPDATED_ASC)
  }
}