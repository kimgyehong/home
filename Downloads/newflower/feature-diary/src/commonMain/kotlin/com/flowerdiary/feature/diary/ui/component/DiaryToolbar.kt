package com.flowerdiary.feature.diary.ui.component

import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import com.flowerdiary.feature.diary.store.state.SortOrder

data class DiaryToolbar(
  val title: String = "",
  val showBackButton: Boolean = true,
  val showSearchButton: Boolean = false,
  val showSortButton: Boolean = false,
  val showFilterButton: Boolean = false,
  val showMenuButton: Boolean = true,
  val currentSortOrder: SortOrder = SortOrder.DATE_DESC,
  val currentFilter: DiaryStatus? = null,
  val selectedCount: Int = 0,
  val isInSelectionMode: Boolean = false,
  val onBackClick: () -> Unit = {},
  val onSearchClick: () -> Unit = {},
  val onSortClick: (SortOrder) -> Unit = {},
  val onFilterClick: (DiaryStatus?) -> Unit = {},
  val onMenuClick: () -> Unit = {},
  val onSelectAllClick: () -> Unit = {},
  val onClearSelectionClick: () -> Unit = {},
  val onDeleteSelectedClick: () -> Unit = {},
  val onArchiveSelectedClick: () -> Unit = {},
  val onShareSelectedClick: () -> Unit = {}
) {

  val displayTitle: String
    get() = if (isInSelectionMode) {
      "$selectedCount selected"
    } else {
      title
    }

  val showSelectionActions: Boolean
    get() = isInSelectionMode && selectedCount > 0

  val canDeleteSelected: Boolean
    get() = selectedCount > 0

  val canArchiveSelected: Boolean
    get() = selectedCount > 0

  val canShareSelected: Boolean
    get() = selectedCount > 0

  val sortButtonText: String
    get() = when (currentSortOrder) {
      SortOrder.DATE_ASC -> "Date ↑"
      SortOrder.DATE_DESC -> "Date ↓"
      SortOrder.TITLE_ASC -> "Title ↑"
      SortOrder.TITLE_DESC -> "Title ↓"
      SortOrder.UPDATED_ASC -> "Updated ↑"
      SortOrder.UPDATED_DESC -> "Updated ↓"
    }

  val filterButtonText: String
    get() = currentFilter?.let { 
      when (it) {
        DiaryStatus.Draft -> "Draft"
        DiaryStatus.Published -> "Published"
        DiaryStatus.Archived -> "Archived"
      }
    } ?: "All"

  val hasActiveFilter: Boolean
    get() = currentFilter != null

  fun handleBackClick() {
    if (isInSelectionMode) {
      onClearSelectionClick()
    } else {
      onBackClick()
    }
  }

  fun handleSearchClick() {
    onSearchClick()
  }

  fun handleSortClick() {
    val nextSortOrder = getNextSortOrder(currentSortOrder)
    onSortClick(nextSortOrder)
  }

  fun handleFilterClick() {
    val nextFilter = getNextFilter(currentFilter)
    onFilterClick(nextFilter)
  }

  fun handleMenuClick() {
    onMenuClick()
  }

  fun handleSelectAllClick() {
    onSelectAllClick()
  }

  fun handleClearSelectionClick() {
    onClearSelectionClick()
  }

  fun handleDeleteSelectedClick() {
    if (canDeleteSelected) {
      onDeleteSelectedClick()
    }
  }

  fun handleArchiveSelectedClick() {
    if (canArchiveSelected) {
      onArchiveSelectedClick()
    }
  }

  fun handleShareSelectedClick() {
    if (canShareSelected) {
      onShareSelectedClick()
    }
  }

  private fun getNextSortOrder(current: SortOrder): SortOrder {
    return when (current) {
      SortOrder.DATE_DESC -> SortOrder.DATE_ASC
      SortOrder.DATE_ASC -> SortOrder.TITLE_DESC
      SortOrder.TITLE_DESC -> SortOrder.TITLE_ASC
      SortOrder.TITLE_ASC -> SortOrder.UPDATED_DESC
      SortOrder.UPDATED_DESC -> SortOrder.UPDATED_ASC
      SortOrder.UPDATED_ASC -> SortOrder.DATE_DESC
    }
  }

  private fun getNextFilter(current: DiaryStatus?): DiaryStatus? {
    return when (current) {
      null -> DiaryStatus.Draft
      DiaryStatus.Draft -> DiaryStatus.Published
      DiaryStatus.Published -> DiaryStatus.Archived
      DiaryStatus.Archived -> null
    }
  }

  fun getActionButtonsCount(): Int {
    var count = 0
    if (showBackButton) count++
    if (showSearchButton) count++
    if (showSortButton) count++
    if (showFilterButton) count++
    if (showMenuButton) count++
    return count
  }

  fun getSelectionActionsCount(): Int {
    var count = 0
    if (canDeleteSelected) count++
    if (canArchiveSelected) count++
    if (canShareSelected) count++
    return count
  }

  companion object {
    fun list(): DiaryToolbar {
      return DiaryToolbar(
        title = "Diaries",
        showSearchButton = true,
        showSortButton = true,
        showFilterButton = true
      )
    }

    fun editor(title: String): DiaryToolbar {
      return DiaryToolbar(
        title = title,
        showBackButton = true,
        showMenuButton = true
      )
    }

    fun detail(title: String): DiaryToolbar {
      return DiaryToolbar(
        title = title,
        showBackButton = true,
        showMenuButton = true
      )
    }
  }
}