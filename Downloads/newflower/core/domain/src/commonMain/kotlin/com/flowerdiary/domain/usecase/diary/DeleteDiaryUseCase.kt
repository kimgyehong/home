package com.flowerdiary.domain.usecase.diary

import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.DiaryId
import com.flowerdiary.domain.repository.DiaryRepository

/**
 * 일기 삭제 유스케이스
 * 단일 책임: 일기 삭제 로직만 담당
 */
class DeleteDiaryUseCase(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(id: DiaryId): Result<Unit> {
        Logger.debug(TAG, "Deleting diary: ${id.value}")
        
        // 존재 여부 확인
        val existingDiary = diaryRepository.findById(id).getOrNull()
        if (existingDiary == null) {
            Logger.warning(TAG, "Diary not found for deletion: ${id.value}")
            return Result.failure(NoSuchElementException("삭제할 일기를 찾을 수 없습니다"))
        }
        
        return diaryRepository.delete(id)
            .onSuccess { 
                Logger.info(TAG, "Diary deleted successfully: ${id.value}")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to delete diary: ${id.value}", it)
            }
    }
    
    companion object {
        private const val TAG = "DeleteDiaryUseCase"
    }
}
