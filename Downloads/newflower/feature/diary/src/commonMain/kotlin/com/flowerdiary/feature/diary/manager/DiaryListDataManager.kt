package com.flowerdiary.feature.diary.manager

import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.model.DiaryId
import com.flowerdiary.domain.usecase.diary.DeleteDiaryUseCase
import com.flowerdiary.domain.usecase.diary.GetDiariesUseCase

/**
 * 일기 목록 데이터 관리자
 * SRP: 일기 목록의 데이터 로드/삭제 로직만 담당
 */
class DiaryListDataManager(
    private val getDiariesUseCase: GetDiariesUseCase,
    private val deleteDiaryUseCase: DeleteDiaryUseCase
) {
    
    /**
     * 모든 일기 로드
     */
    suspend fun loadAllDiaries(): Result<List<Diary>> {
        return getDiariesUseCase.getAll()
    }
    
    /**
     * 일기 삭제
     */
    suspend fun deleteDiary(diaryId: DiaryId): Result<Unit> {
        return deleteDiaryUseCase(diaryId)
    }
    
    /**
     * 정렬된 일기 목록 가져오기
     */
    fun getSortedDiaries(diaries: List<Diary>): List<Diary> {
        return diaries.sortedByDescending { it.createdAt }
    }
}