package com.flowerdiary.feature.diary.domain.usecase

import com.flowerdiary.core.util.ResultWrapper
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import com.flowerdiary.feature.diary.domain.repository.DiaryRepository
import kotlinx.datetime.LocalDate
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class GetDiariesUseCase(
  private val diaryRepository: DiaryRepository
) {

  suspend fun executeAll(): ResultWrapper<ImmutableList<Diary>> {
    return try {
      val diaries = diaryRepository.getAll().toImmutableList()
      ResultWrapper.success(diaries)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to get all diaries: ${exception.message}")
    }
  }

  suspend fun executeByStatus(status: DiaryStatus): ResultWrapper<ImmutableList<Diary>> {
    return try {
      val diaries = diaryRepository.getByStatus(status).toImmutableList()
      ResultWrapper.success(diaries)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to get diaries by status: ${exception.message}")
    }
  }

  suspend fun executeByDateRange(
    startDate: LocalDate,
    endDate: LocalDate
  ): ResultWrapper<ImmutableList<Diary>> {
    return try {
      require(startDate <= endDate) { "Start date must be before or equal to end date" }
      
      val diaries = diaryRepository.getByDateRange(startDate, endDate).toImmutableList()
      ResultWrapper.success(diaries)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to get diaries by date range: ${exception.message}")
    }
  }

  suspend fun executeByMonth(year: Int, month: Int): ResultWrapper<ImmutableList<Diary>> {
    return try {
      require(month in MONTH_RANGE) { "Month must be between 1 and 12" }
      require(year > 0) { "Year must be positive" }
      
      val diaries = diaryRepository.getByMonth(year, month).toImmutableList()
      ResultWrapper.success(diaries)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to get diaries by month: ${exception.message}")
    }
  }

  suspend fun executeRecent(limit: Int = DEFAULT_RECENT_LIMIT): ResultWrapper<ImmutableList<Diary>> {
    return try {
      require(limit > 0) { "Limit must be positive" }
      
      val diaries = diaryRepository.getRecent(limit).toImmutableList()
      ResultWrapper.success(diaries)
    } catch (exception: Exception) {
      ResultWrapper.error("Failed to get recent diaries: ${exception.message}")
    }
  }

  companion object {
    private const val DEFAULT_RECENT_LIMIT = 10
    private val MONTH_RANGE = 1..12
  }
}