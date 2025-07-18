package com.flowerdiary.feature.diary.viewmodel

import com.flowerdiary.common.constants.Messages
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.model.DiaryId
import com.flowerdiary.feature.diary.manager.*
import com.flowerdiary.feature.diary.state.*

/**
 * 일기 목록 ViewModel
 * SRP: Intent 처리와 매니저 조정만 담당
 * 실제 비즈니스 로직은 매니저에게 위임
 */
class DiaryListViewModel(
    private val dataManager: DiaryListDataManager,
    private val filterManager: DiaryListFilterManager,
    private val stateManager: DiaryListStateManager
) : BaseViewModel<DiaryListState, DiaryListIntent, DiaryListEffect>() {
    
    init { processIntent(DiaryListIntent.LoadDiaries) }
    
    override fun createInitialState() = stateManager.createInitialState()
    
    override fun processIntent(intent: DiaryListIntent) = when (intent) {
        is DiaryListIntent.LoadDiaries -> loadDiaries()
        is DiaryListIntent.RefreshDiaries -> refreshDiaries()
        is DiaryListIntent.FilterByMonth -> filterByMonth(intent.year, intent.month)
        is DiaryListIntent.ClearFilter -> clearFilter()
        is DiaryListIntent.SelectDiary -> selectDiary(intent.diary)
        is DiaryListIntent.DeleteDiary -> confirmDeleteDiary(intent.diary)
        is DiaryListIntent.CreateNewDiary -> createNewDiary()
    }
    
    override fun handleError(throwable: Throwable) = updateState { stateManager.updateToError(throwable) }
    
    private fun loadDiaries() {
        if (currentState is DiaryListState.Loading) return
        updateState { stateManager.updateToLoading() }
        launch {
            dataManager.loadAllDiaries()
                .onSuccess { diaries ->
                    val sorted = dataManager.getSortedDiaries(diaries)
                    updateState { stateManager.updateToSuccess(sorted) }
                }
                .onFailure { 
                    Logger.error(TAG, "Failed to load diaries", it)
                    handleError(it)
                }
        }
    }
    
    private fun refreshDiaries() {
        val currentSuccess = currentState as? DiaryListState.Success ?: return
        updateState { stateManager.updateRefreshState(currentSuccess, true) }
        launch {
            dataManager.loadAllDiaries()
                .onSuccess { diaries ->
                    val sorted = dataManager.getSortedDiaries(diaries)
                    updateState { stateManager.updateDiaries(currentSuccess, sorted).let {
                        stateManager.updateRefreshState(it as DiaryListState.Success, false)
                    }}
                }
                .onFailure {
                    Logger.error(TAG, "Failed to refresh diaries", it)
                    updateState { stateManager.updateRefreshState(currentSuccess, false) }
                    sendEffect(DiaryListEffect.ShowToast(Messages.ERROR_REFRESH_FAILED))
                }
        }
    }
    
    private fun filterByMonth(year: Int, month: Int) {
        val currentSuccess = currentState as? DiaryListState.Success ?: return
        updateState { stateManager.updateFilterState(currentSuccess, year, month) }
    }
    
    private fun clearFilter() {
        val currentSuccess = currentState as? DiaryListState.Success ?: return
        updateState { stateManager.updateFilterState(currentSuccess, null, null) }
    }
    
    private fun selectDiary(diary: Diary) = sendEffect(DiaryListEffect.NavigateToDiaryDetail(diary.id.value))
    
    private fun confirmDeleteDiary(diary: Diary) = sendEffect(DiaryListEffect.ShowDeleteConfirmation(diary))
    
    fun deleteDiary(diaryId: DiaryId) = launch {
        dataManager.deleteDiary(diaryId)
            .onSuccess {
                sendEffect(DiaryListEffect.ShowToast(Messages.SUCCESS_DIARY_DELETED))
                loadDiaries()
            }
            .onFailure {
                Logger.error(TAG, "Failed to delete diary", it)
                sendEffect(DiaryListEffect.ShowToast(Messages.ERROR_DELETE_FAILED))
            }
    }
    
    private fun createNewDiary() = sendEffect(DiaryListEffect.NavigateToCreateDiary)
    
    companion object { private const val TAG = "DiaryListViewModel" }
}