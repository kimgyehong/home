package com.flowerdiary.domain.usecase.diary

import com.flowerdiary.common.utils.Logger
import com.flowerdiary.domain.model.Diary
import com.flowerdiary.domain.repository.DiaryRepository

/**
 * 일기 저장 유스케이스
 * 단일 책임: 일기 저장 비즈니스 로직만 담당
 */
class SaveDiaryUseCase(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(diary: Diary): Result<Unit> {
        // 유효성 검증
        if (diary.isEmpty()) {
            return Result.failure(IllegalArgumentException("일기 내용이 비어있습니다"))
        }
        
        Logger.debug(TAG, "Saving diary: ${diary.id.value}")
        
        return diaryRepository.save(diary)
            .onSuccess { 
                Logger.info(TAG, "Diary saved successfully: ${diary.id.value}")
            }
            .onFailure { 
                Logger.error(TAG, "Failed to save diary: ${diary.id.value}", it)
            }
    }
    
    companion object {
        private const val TAG = "SaveDiaryUseCase"
    }
}
