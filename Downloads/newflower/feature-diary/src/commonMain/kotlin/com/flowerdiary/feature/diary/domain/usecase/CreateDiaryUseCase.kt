package com.flowerdiary.feature.diary.domain.usecase

import com.flowerdiary.core.types.FlowerId
import com.flowerdiary.core.util.ResultWrapper
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.repository.DiaryRepository
import com.flowerdiary.feature.diary.domain.repository.BirthFlowerRepository
import kotlinx.datetime.LocalDate

class CreateDiaryUseCase(
  private val diaryRepository: DiaryRepository,
  private val birthFlowerRepository: BirthFlowerRepository
) {

  suspend fun execute(date: LocalDate): ResultWrapper<Diary> {
    return try {
      val birthFlowerId = getBirthFlowerIdForDate(date)
      val diary = Diary.create(date, birthFlowerId)
      
      val savedDiary = diaryRepository.save(diary)
      ResultWrapper.success(savedDiary)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to create diary: ${exception.message}")
    }
  }

  suspend fun executeWithContent(
    date: LocalDate,
    title: String,
    content: String
  ): ResultWrapper<Diary> {
    return try {
      require(title.isNotBlank()) { "Title cannot be blank" }
      
      val birthFlowerId = getBirthFlowerIdForDate(date)
      val diary = Diary.create(date, birthFlowerId)
        .updateEntry(title, content)
      
      val savedDiary = diaryRepository.save(diary)
      ResultWrapper.success(savedDiary)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to create diary: ${exception.message}")
    }
  }

  private suspend fun getBirthFlowerIdForDate(date: LocalDate): FlowerId? {
    return try {
      val birthFlower = birthFlowerRepository.getByDate(date)
      birthFlower?.id
    } catch (exception: Exception) {
      null
    }
  }
}