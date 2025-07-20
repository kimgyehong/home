package com.flowerdiary.feature.diary.domain.usecase

import com.flowerdiary.core.util.ResultWrapper
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import com.flowerdiary.feature.diary.domain.repository.DiaryRepository

class DeleteDiaryUseCase(
  private val diaryRepository: DiaryRepository
) {

  suspend fun execute(diaryId: DiaryId): ResultWrapper<Unit> {
    return try {
      val existingDiary = diaryRepository.getById(diaryId)
        ?: return ResultWrapper.error("Diary not found")

      val success = diaryRepository.delete(diaryId)
      if (success) {
        ResultWrapper.success(Unit)
      } else {
        ResultWrapper.error("Failed to delete diary")
      }
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to delete diary: ${exception.message}")
    }
  }

  suspend fun executeMultiple(diaryIds: List<DiaryId>): ResultWrapper<Unit> {
    return try {
      if (diaryIds.isEmpty()) {
        return ResultWrapper.error("No diaries to delete")
      }

      val results = diaryIds.map { diaryId ->
        diaryRepository.getById(diaryId) != null
      }

      val notFoundCount = results.count { !it }
      if (notFoundCount > 0) {
        return ResultWrapper.error("$notFoundCount diaries not found")
      }

      val success = diaryRepository.deleteMultiple(diaryIds)
      if (success) {
        ResultWrapper.success(Unit)
      } else {
        ResultWrapper.error("Failed to delete some diaries")
      }
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to delete diaries: ${exception.message}")
    }
  }

  suspend fun executeByStatus(status: DiaryStatus): ResultWrapper<Int> {
    return try {
      val deletedCount = diaryRepository.deleteByStatus(status)
      ResultWrapper.success(deletedCount)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to delete diaries by status: ${exception.message}")
    }
  }
}