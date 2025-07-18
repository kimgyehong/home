package com.flowerdiary.domain.usecase.diary

import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.model.DiaryId
import com.flowerdiary.domain.repository.DiaryRepository

/**
 * 일기 조회 유스케이스
 * 단일 책임: 특정 일기 조회 로직만 담당
 */
class GetDiaryUseCase(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(id: DiaryId): Result<Diary?> {
        Logger.debug(TAG, "Getting diary: ${id.value}")
        
        return diaryRepository.findById(id)
            .onSuccess { diary ->
                if (diary != null) {
                    Logger.info(TAG, "Diary found: ${id.value}")
                } else {
                    Logger.info(TAG, "Diary not found: ${id.value}")
                }
            }
            .onFailure { 
                Logger.error(TAG, "Failed to get diary: ${id.value}", it)
            }
    }
    
    companion object {
        private const val TAG = "GetDiaryUseCase"
    }
}
