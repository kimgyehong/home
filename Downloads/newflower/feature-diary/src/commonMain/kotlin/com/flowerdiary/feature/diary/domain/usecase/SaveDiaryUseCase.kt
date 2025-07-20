package com.flowerdiary.feature.diary.domain.usecase

import com.flowerdiary.core.util.ResultWrapper
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.repository.DiaryRepository
import com.flowerdiary.feature.diary.domain.validator.DiaryValidator

class SaveDiaryUseCase(
  private val diaryRepository: DiaryRepository,
  private val diaryValidator: DiaryValidator
) {

  suspend fun execute(diary: Diary): ResultWrapper<Diary> {
    return try {
      val validationResult = diaryValidator.validate(diary)
      if (!validationResult.isValid) {
        return ResultWrapper.error("Validation failed: ${validationResult.errorMessage}")
      }

      val savedDiary = diaryRepository.save(diary)
      ResultWrapper.success(savedDiary)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to save diary: ${exception.message}")
    }
  }

  suspend fun autoSave(diary: Diary): ResultWrapper<Diary> {
    return try {
      if (diary.isEmpty()) {
        return ResultWrapper.error("Cannot auto-save empty diary")
      }

      val savedDiary = diaryRepository.save(diary)
      ResultWrapper.success(savedDiary)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to auto-save diary: ${exception.message}")
    }
  }

  suspend fun executeWithBackup(diary: Diary): ResultWrapper<Diary> {
    return try {
      val validationResult = diaryValidator.validate(diary)
      if (!validationResult.isValid) {
        return ResultWrapper.error("Validation failed: ${validationResult.errorMessage}")
      }

      val existingDiary = diaryRepository.getById(diary.id)
      if (existingDiary != null) {
        diaryRepository.backup(existingDiary)
      }

      val savedDiary = diaryRepository.save(diary)
      ResultWrapper.success(savedDiary)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to save diary with backup: ${exception.message}")
    }
  }

  suspend fun saveMultiple(diaries: List<Diary>): ResultWrapper<List<Diary>> {
    return try {
      if (diaries.isEmpty()) {
        return ResultWrapper.error("No diaries to save")
      }

      val validationResults = diaries.map { diary ->
        diaryValidator.validate(diary)
      }

      val invalidDiaries = validationResults.filterNot { it.isValid }
      if (invalidDiaries.isNotEmpty()) {
        val errors = invalidDiaries.joinToString(", ") { it.errorMessage }
        return ResultWrapper.error("Validation failed for some diaries: $errors")
      }

      val savedDiaries = diaryRepository.saveMultiple(diaries)
      ResultWrapper.success(savedDiaries)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to save multiple diaries: ${exception.message}")
    }
  }
}