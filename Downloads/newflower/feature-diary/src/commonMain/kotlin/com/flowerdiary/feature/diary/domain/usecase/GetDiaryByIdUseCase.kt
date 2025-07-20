package com.flowerdiary.feature.diary.domain.usecase

import com.flowerdiary.core.util.ResultWrapper
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.repository.DiaryRepository
import kotlinx.datetime.LocalDate

class GetDiaryByIdUseCase(
  private val diaryRepository: DiaryRepository
) {

  suspend fun execute(diaryId: DiaryId): ResultWrapper<Diary> {
    return try {
      val diary = diaryRepository.getById(diaryId)
      if (diary != null) {
        ResultWrapper.success(diary)
      } else {
        ResultWrapper.error("Diary not found")
      }
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to get diary: ${exception.message}")
    }
  }

  suspend fun executeByDate(date: LocalDate): ResultWrapper<Diary> {
    return try {
      val diary = diaryRepository.getByDate(date)
      if (diary != null) {
        ResultWrapper.success(diary)
      } else {
        ResultWrapper.error("No diary found for date: $date")
      }
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to get diary by date: ${exception.message}")
    }
  }

  suspend fun executeWithValidation(diaryId: DiaryId): ResultWrapper<Diary> {
    return try {
      val diary = diaryRepository.getById(diaryId)
        ?: return ResultWrapper.error("Diary not found")

      if (diary.isEmpty()) {
        ResultWrapper.error("Diary is empty")
      } else {
        ResultWrapper.success(diary)
      }
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to get diary: ${exception.message}")
    }
  }

  suspend fun checkExists(diaryId: DiaryId): ResultWrapper<Boolean> {
    return try {
      val exists = diaryRepository.exists(diaryId)
      ResultWrapper.success(exists)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to check diary existence: ${exception.message}")
    }
  }

  suspend fun checkExistsByDate(date: LocalDate): ResultWrapper<Boolean> {
    return try {
      val diary = diaryRepository.getByDate(date)
      ResultWrapper.success(diary != null)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to check diary existence by date: ${exception.message}")
    }
  }
}