package com.flowerdiary.feature.diary.store.store

import com.flowerdiary.feature.diary.domain.usecase.DeleteDiaryUseCase
import com.flowerdiary.feature.diary.domain.usecase.GetDiariesUseCase
import com.flowerdiary.feature.diary.domain.usecase.UpdateDiaryUseCase
import com.flowerdiary.feature.diary.store.action.DiaryListAction
import com.flowerdiary.feature.diary.store.state.DiaryListState
import com.flowerdiary.feature.diary.store.state.SortOrder
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiaryListStore(
  private val getDiariesUseCase: GetDiariesUseCase,
  private val updateDiaryUseCase: UpdateDiaryUseCase,
  private val deleteDiaryUseCase: DeleteDiaryUseCase,
  private val coroutineScope: CoroutineScope
) {

  private val _state = MutableStateFlow(DiaryListState())
  val state: StateFlow<DiaryListState> = _state.asStateFlow()

  fun dispatch(action: DiaryListAction) {
    coroutineScope.launch {
      when (action) {
        is DiaryListAction.Initialize -> initialize()
        is DiaryListAction.LoadDiaries -> loadDiaries()
        is DiaryListAction.RefreshDiaries -> refreshDiaries()
        is DiaryListAction.LoadMoreDiaries -> loadMoreDiaries()
        is DiaryListAction.LoadDiariesByMonth -> loadDiariesByMonth(action.year, action.month)
        is DiaryListAction.LoadDiariesByDateRange -> loadDiariesByDateRange(action.startDate, action.endDate)
        is DiaryListAction.SearchDiaries -> searchDiaries(action.query)
        is DiaryListAction.FilterByStatus -> filterByStatus(action.status)
        is DiaryListAction.SortDiaries -> sortDiaries(action.sortOrder)
        is DiaryListAction.DeleteDiary -> deleteDiary(action.diaryId)
        is DiaryListAction.DeleteMultipleDiaries -> deleteMultipleDiaries(action.diaryIds)
        is DiaryListAction.ArchiveDiary -> archiveDiary(action.diaryId)
        is DiaryListAction.PublishDiary -> publishDiary(action.diaryId)
        is DiaryListAction.ClearSearch -> clearSearch()
        is DiaryListAction.ClearFilter -> clearFilter()
        is DiaryListAction.ClearError -> clearError()
        is DiaryListAction.RetryLoad -> retryLoad()
        else -> {
          // Handle other actions
        }
      }
    }
  }

  private suspend fun initialize() {
    _state.value = DiaryListState.loading()
    loadDiaries()
  }

  private suspend fun loadDiaries() {
    _state.value = _state.value.copy(isLoading = true, errorMessage = null)
    
    val result = getDiariesUseCase.executeAll()
    
    result.fold(
      onSuccess = { diaries ->
        _state.value = _state.value.copy(
          diaries = diaries,
          filteredDiaries = diaries,
          isLoading = false
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(
          isLoading = false,
          errorMessage = error.message
        )
      }
    )
  }

  private suspend fun refreshDiaries() {
    _state.value = _state.value.copy(isRefreshing = true, errorMessage = null)
    
    val result = getDiariesUseCase.executeAll()
    
    result.fold(
      onSuccess = { diaries ->
        _state.value = _state.value.copy(
          diaries = diaries,
          filteredDiaries = applyCurrentFilters(diaries),
          isRefreshing = false
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(
          isRefreshing = false,
          errorMessage = error.message
        )
      }
    )
  }

  private suspend fun loadMoreDiaries() {
    if (_state.value.isLoadingMore || !_state.value.hasNextPage) return
    
    _state.value = _state.value.copy(isLoadingMore = true)
    
    val currentCount = _state.value.diaries.size
    val result = getDiariesUseCase.executeRecent(currentCount + PAGE_SIZE)
    
    result.fold(
      onSuccess = { diaries ->
        _state.value = _state.value.copy(
          diaries = diaries,
          filteredDiaries = applyCurrentFilters(diaries),
          isLoadingMore = false,
          hasNextPage = diaries.size > currentCount
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(
          isLoadingMore = false,
          errorMessage = error.message
        )
      }
    )
  }

  private suspend fun loadDiariesByMonth(year: Int, month: Int) {
    _state.value = _state.value.copy(isLoading = true, errorMessage = null)
    
    val result = getDiariesUseCase.executeByMonth(year, month)
    
    result.fold(
      onSuccess = { diaries ->
        _state.value = _state.value.copy(
          diaries = diaries,
          filteredDiaries = diaries,
          isLoading = false
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(
          isLoading = false,
          errorMessage = error.message
        )
      }
    )
  }

  private suspend fun loadDiariesByDateRange(startDate: kotlinx.datetime.LocalDate, endDate: kotlinx.datetime.LocalDate) {
    _state.value = _state.value.copy(isLoading = true, errorMessage = null)
    
    val result = getDiariesUseCase.executeByDateRange(startDate, endDate)
    
    result.fold(
      onSuccess = { diaries ->
        _state.value = _state.value.copy(
          diaries = diaries,
          filteredDiaries = diaries,
          isLoading = false
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(
          isLoading = false,
          errorMessage = error.message
        )
      }
    )
  }

  private fun searchDiaries(query: String) {
    val currentState = _state.value
    val searchResults = if (query.isBlank()) {
      currentState.diaries
    } else {
      currentState.diaries.filter { diary ->
        diary.entry.title.contains(query, ignoreCase = true) ||
        diary.entry.content.contains(query, ignoreCase = true)
      }
    }
    
    _state.value = currentState.copy(
      searchQuery = query,
      filteredDiaries = searchResults.toImmutableList()
    )
  }

  private fun filterByStatus(status: com.flowerdiary.feature.diary.domain.model.DiaryStatus?) {
    val currentState = _state.value
    val filteredResults = if (status == null) {
      currentState.diaries
    } else {
      currentState.diaries.filter { diary -> diary.status == status }
    }
    
    _state.value = currentState.copy(
      selectedStatus = status,
      filteredDiaries = filteredResults.toImmutableList()
    )
  }

  private fun sortDiaries(sortOrder: SortOrder) {
    val currentState = _state.value
    val sortedDiaries = when (sortOrder) {
      SortOrder.DATE_ASC -> currentState.diaries.sortedBy { it.entry.date }
      SortOrder.DATE_DESC -> currentState.diaries.sortedByDescending { it.entry.date }
      SortOrder.TITLE_ASC -> currentState.diaries.sortedBy { it.entry.title }
      SortOrder.TITLE_DESC -> currentState.diaries.sortedByDescending { it.entry.title }
      SortOrder.UPDATED_ASC -> currentState.diaries.sortedBy { it.entry.updatedAt }
      SortOrder.UPDATED_DESC -> currentState.diaries.sortedByDescending { it.entry.updatedAt }
    }
    
    _state.value = currentState.copy(
      sortOrder = sortOrder,
      diaries = sortedDiaries.toImmutableList(),
      filteredDiaries = applyCurrentFilters(sortedDiaries.toImmutableList())
    )
  }

  private suspend fun deleteDiary(diaryId: com.flowerdiary.feature.diary.domain.model.DiaryId) {
    val result = deleteDiaryUseCase.execute(diaryId)
    
    result.fold(
      onSuccess = {
        val currentState = _state.value
        val updatedDiaries = currentState.diaries.filterNot { it.id == diaryId }
        
        _state.value = currentState.copy(
          diaries = updatedDiaries.toImmutableList(),
          filteredDiaries = applyCurrentFilters(updatedDiaries.toImmutableList())
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(errorMessage = error.message)
      }
    )
  }

  private suspend fun deleteMultipleDiaries(diaryIds: List<com.flowerdiary.feature.diary.domain.model.DiaryId>) {
    val result = deleteDiaryUseCase.executeMultiple(diaryIds)
    
    result.fold(
      onSuccess = {
        val currentState = _state.value
        val updatedDiaries = currentState.diaries.filterNot { diary ->
          diaryIds.contains(diary.id)
        }
        
        _state.value = currentState.copy(
          diaries = updatedDiaries.toImmutableList(),
          filteredDiaries = applyCurrentFilters(updatedDiaries.toImmutableList())
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(errorMessage = error.message)
      }
    )
  }

  private suspend fun archiveDiary(diaryId: com.flowerdiary.feature.diary.domain.model.DiaryId) {
    val result = updateDiaryUseCase.archiveDiary(diaryId)
    
    result.fold(
      onSuccess = { updatedDiary ->
        val currentState = _state.value
        val updatedDiaries = currentState.diaries.map { diary ->
          if (diary.id == diaryId) updatedDiary else diary
        }
        
        _state.value = currentState.copy(
          diaries = updatedDiaries.toImmutableList(),
          filteredDiaries = applyCurrentFilters(updatedDiaries.toImmutableList())
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(errorMessage = error.message)
      }
    )
  }

  private suspend fun publishDiary(diaryId: com.flowerdiary.feature.diary.domain.model.DiaryId) {
    val result = updateDiaryUseCase.publishDiary(diaryId)
    
    result.fold(
      onSuccess = { updatedDiary ->
        val currentState = _state.value
        val updatedDiaries = currentState.diaries.map { diary ->
          if (diary.id == diaryId) updatedDiary else diary
        }
        
        _state.value = currentState.copy(
          diaries = updatedDiaries.toImmutableList(),
          filteredDiaries = applyCurrentFilters(updatedDiaries.toImmutableList())
        )
      },
      onFailure = { error ->
        _state.value = _state.value.copy(errorMessage = error.message)
      }
    )
  }

  private fun clearSearch() {
    val currentState = _state.value
    _state.value = currentState.copy(
      searchQuery = "",
      filteredDiaries = applyCurrentFilters(currentState.diaries)
    )
  }

  private fun clearFilter() {
    val currentState = _state.value
    _state.value = currentState.copy(
      selectedStatus = null,
      filteredDiaries = applyCurrentFilters(currentState.diaries)
    )
  }

  private fun clearError() {
    _state.value = _state.value.copy(errorMessage = null)
  }

  private suspend fun retryLoad() {
    loadDiaries()
  }

  private fun applyCurrentFilters(diaries: kotlinx.collections.immutable.ImmutableList<com.flowerdiary.feature.diary.domain.model.Diary>): kotlinx.collections.immutable.ImmutableList<com.flowerdiary.feature.diary.domain.model.Diary> {
    var filtered = diaries
    
    // Apply status filter
    _state.value.selectedStatus?.let { status ->
      filtered = filtered.filter { it.status == status }.toImmutableList()
    }
    
    // Apply search filter
    if (_state.value.searchQuery.isNotBlank()) {
      filtered = filtered.filter { diary ->
        diary.entry.title.contains(_state.value.searchQuery, ignoreCase = true) ||
        diary.entry.content.contains(_state.value.searchQuery, ignoreCase = true)
      }.toImmutableList()
    }
    
    return filtered
  }

  companion object {
    private const val PAGE_SIZE = 20
  }
}