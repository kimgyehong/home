package com.flowerdiary.feature.diary.domain.validator

import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class DiaryValidator(
  private val contentValidator: ContentValidator = ContentValidator()
) {

  fun validate(diary: Diary): ValidationResult {
    val results = listOf(
      validateDiaryId(diary),
      validateEntry(diary),
      validateDate(diary),
      validateStatus(diary),
      validateTimestamps(diary)
    )

    return ValidationResult.combine(results)
  }

  fun validateForSave(diary: Diary): ValidationResult {
    val basicValidation = validate(diary)
    if (!basicValidation.isValid) {
      return basicValidation
    }

    return when {
      diary.isEmpty() -> ValidationResult.warning("빈 일기가 저장됩니다")
      diary.entry.content.isBlank() -> ValidationResult.warning("내용이 없는 일기가 저장됩니다")
      else -> ValidationResult.valid()
    }
  }

  fun validateForPublish(diary: Diary): ValidationResult {
    val basicValidation = validate(diary)
    if (!basicValidation.isValid) {
      return basicValidation
    }

    return when {
      diary.entry.content.isBlank() -> ValidationResult.invalid("빈 내용은 게시할 수 없습니다")
      diary.entry.title.isBlank() -> ValidationResult.invalid("제목이 없으면 게시할 수 없습니다")
      !diary.status.canPublish() -> ValidationResult.invalid("현재 상태에서는 게시할 수 없습니다")
      else -> ValidationResult.valid()
    }
  }

  private fun validateDiaryId(diary: Diary): ValidationResult {
    return try {
      if (diary.id.value.isBlank()) {
        ValidationResult.invalid("일기 ID가 비어있습니다")
      } else {
        ValidationResult.valid()
      }
    } catch (exception: Exception) {
      ValidationResult.invalid("유효하지 않은 일기 ID입니다")
    }
  }

  private fun validateEntry(diary: Diary): ValidationResult {
    return contentValidator.validateEntry(diary.entry)
  }

  private fun validateDate(diary: Diary): ValidationResult {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val entryDate = diary.entry.date

    return when {
      entryDate > today -> ValidationResult.warning("미래 날짜의 일기입니다")
      entryDate < today.minus(kotlinx.datetime.DatePeriod(years = MAX_PAST_YEARS)) -> 
        ValidationResult.warning("너무 오래된 날짜의 일기입니다")
      else -> ValidationResult.valid()
    }
  }

  private fun validateStatus(diary: Diary): ValidationResult {
    return when (diary.status) {
      DiaryStatus.Published -> {
        if (diary.entry.content.isBlank()) {
          ValidationResult.invalid("게시된 일기는 내용이 있어야 합니다")
        } else {
          ValidationResult.valid()
        }
      }
      else -> ValidationResult.valid()
    }
  }

  private fun validateTimestamps(diary: Diary): ValidationResult {
    return when {
      diary.entry.createdAt <= 0 -> ValidationResult.invalid("생성 시간이 유효하지 않습니다")
      diary.entry.updatedAt < diary.entry.createdAt -> ValidationResult.invalid("수정 시간이 생성 시간보다 이전입니다")
      else -> ValidationResult.valid()
    }
  }

  companion object {
    private const val MAX_PAST_YEARS = 10
  }
}