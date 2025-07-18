package com.flowerdiary.feature.diary.manager

import com.flowerdiary.common.constants.Messages
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.feature.diary.state.DiaryListState

/**
 * 일기 목록 상태 관리자
 * SRP: 일기 목록의 상태 관리 로직만 담당
 */
class DiaryListStateManager {
    
    /**
     * 초기 상태 생성
     */
    fun createInitialState(): DiaryListState {
        return DiaryListState.Initial
    }
    
    /**
     * 로딩 상태로 변경
     */
    fun updateToLoading(): DiaryListState {
        return DiaryListState.Loading
    }
    
    /**
     * 성공 상태로 변경
     */
    fun updateToSuccess(
        diaries: List<Diary>,
        selectedYear: Int? = null,
        selectedMonth: Int? = null
    ): DiaryListState {
        return DiaryListState.Success(
            diaries = diaries,
            selectedYear = selectedYear,
            selectedMonth = selectedMonth
        )
    }
    
    /**
     * 에러 상태로 변경
     */
    fun updateToError(
        throwable: Throwable,
        canRetry: Boolean = true
    ): DiaryListState {
        return DiaryListState.Error(
            message = throwable.message ?: Messages.ERROR_UNKNOWN,
            throwable = throwable,
            canRetry = canRetry
        )
    }
    
    /**
     * 새로고침 상태 업데이트
     */
    fun updateRefreshState(
        currentState: DiaryListState.Success,
        isRefreshing: Boolean
    ): DiaryListState {
        return currentState.copy(isRefreshing = isRefreshing)
    }
    
    /**
     * 필터 상태 업데이트
     */
    fun updateFilterState(
        currentState: DiaryListState.Success,
        year: Int?,
        month: Int?
    ): DiaryListState {
        return currentState.copy(
            selectedYear = year,
            selectedMonth = month
        )
    }
    
    /**
     * 일기 목록 업데이트
     */
    fun updateDiaries(
        currentState: DiaryListState.Success,
        diaries: List<Diary>
    ): DiaryListState {
        return currentState.copy(diaries = diaries)
    }
}