package com.flowerdiary.feature.diary.domain.usecase

import com.flowerdiary.core.util.ResultWrapper
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import com.flowerdiary.feature.diary.domain.repository.DiaryRepository

class UpdateDiaryUseCase(
  private val diaryRepository: DiaryRepository
) {

  suspend fun execute(
    diaryId: DiaryId,
    title: String,
    content: String
  ): ResultWrapper<Diary> {
    return try {
      val existingDiary = diaryRepository.getById(diaryId)
        ?: return ResultWrapper.error("Diary not found")

      if (!existingDiary.status.canEdit()) {
        return ResultWrapper.error("Cannot edit diary in ${existingDiary.status.displayName()} status")
      }

      val updatedDiary = existingDiary.updateEntry(title, content)
      val savedDiary = diaryRepository.save(updatedDiary)
      
      ResultWrapper.success(savedDiary)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to update diary: ${exception.message}")
    }
  }

  suspend fun publishDiary(diaryId: DiaryId): ResultWrapper<Diary> {
    return try {
      val existingDiary = diaryRepository.getById(diaryId)
        ?: return ResultWrapper.error("Diary not found")

      if (!existingDiary.status.canPublish()) {
        return ResultWrapper.error("Cannot publish diary in ${existingDiary.status.displayName()} status")
      }

      val publishedDiary = existingDiary.publish()
      val savedDiary = diaryRepository.save(publishedDiary)
      
      ResultWrapper.success(savedDiary)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to publish diary: ${exception.message}")
    }
  }

  suspend fun archiveDiary(diaryId: DiaryId): ResultWrapper<Diary> {
    return try {
      val existingDiary = diaryRepository.getById(diaryId)
        ?: return ResultWrapper.error("Diary not found")

      if (!existingDiary.status.canArchive()) {
        return ResultWrapper.error("Cannot archive diary in ${existingDiary.status.displayName()} status")
      }

      val archivedDiary = existingDiary.archive()
      val savedDiary = diaryRepository.save(archivedDiary)
      
      ResultWrapper.success(savedDiary)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to archive diary: ${exception.message}")
    }
  }
}