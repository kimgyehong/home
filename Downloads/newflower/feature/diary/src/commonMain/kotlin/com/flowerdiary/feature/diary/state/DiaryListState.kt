package com.flowerdiary.feature.diary.state

import com.flowerdiary.domain.model.Diary

/**
 * 일기 목록 화면 상태
 * SRP: UI 상태 표현만 담당
 * 불변성 보장으로 상태 예측 가능성 확보
 */
sealed class DiaryListState {
    /**
     * 초기 상태
     */
    object Initial : DiaryListState()
    
    /**
     * 로딩 중
     */
    object Loading : DiaryListState()
    
    /**
     * 일기 목록 로드 성공
     */
    data class Success(
        val diaries: List<Diary>,
        val selectedMonth: Int? = null,
        val selectedYear: Int? = null,
        val isRefreshing: Boolean = false
    ) : DiaryListState() {
        val isEmpty: Boolean = diaries.isEmpty()
        val filteredDiaries: List<Diary> = if (selectedMonth != null && selectedYear != null) {
            diaries.filter { diary ->
                val date = com.flowerdiary.common.platform.DateTimeUtil.toDateInfo(diary.createdAt)
                date.month == selectedMonth && date.year == selectedYear
            }
        } else {
            diaries
        }
    }
    
    /**
     * 에러 상태
     */
    data class Error(
        val message: String,
        val throwable: Throwable? = null,
        val canRetry: Boolean = true
    ) : DiaryListState()
}

/**
 * 일기 목록 UI 이벤트
 * SRP: 사용자 의도만 표현
 */
sealed class DiaryListIntent {
    /**
     * 일기 목록 로드
     */
    object LoadDiaries : DiaryListIntent()
    
    /**
     * 일기 목록 새로고침
     */
    object RefreshDiaries : DiaryListIntent()
    
    /**
     * 특정 월 필터링
     */
    data class FilterByMonth(val year: Int, val month: Int) : DiaryListIntent()
    
    /**
     * 필터 초기화
     */
    object ClearFilter : DiaryListIntent()
    
    /**
     * 일기 선택
     */
    data class SelectDiary(val diary: Diary) : DiaryListIntent()
    
    /**
     * 일기 삭제
     */
    data class DeleteDiary(val diary: Diary) : DiaryListIntent()
    
    /**
     * 새 일기 작성
     */
    object CreateNewDiary : DiaryListIntent()
}

/**
 * 일기 목록 부수효과
 * SRP: 단발성 이벤트만 표현
 */
sealed class DiaryListEffect {
    /**
     * 일기 상세 화면으로 이동
     */
    data class NavigateToDiaryDetail(val diaryId: String) : DiaryListEffect()
    
    /**
     * 새 일기 작성 화면으로 이동
     */
    object NavigateToCreateDiary : DiaryListEffect()
    
    /**
     * 토스트 메시지 표시
     */
    data class ShowToast(val message: String) : DiaryListEffect()
    
    /**
     * 일기 삭제 확인 다이얼로그 표시
     */
    data class ShowDeleteConfirmation(val diary: Diary) : DiaryListEffect()
}
