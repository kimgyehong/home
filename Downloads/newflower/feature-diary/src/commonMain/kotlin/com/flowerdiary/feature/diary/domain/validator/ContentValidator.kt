package com.flowerdiary.feature.diary.domain.validator

import com.flowerdiary.feature.diary.domain.model.DiaryEntry

class ContentValidator {

  fun validateTitle(title: String): ValidationResult {
    return when {
      title.isBlank() -> ValidationResult.invalid("제목을 입력해주세요")
      title.length > MAX_TITLE_LENGTH -> ValidationResult.invalid("제목은 $MAX_TITLE_LENGTH자를 초과할 수 없습니다")
      title.trim() != title -> ValidationResult.warning("제목의 앞뒤 공백이 제거됩니다")
      containsInvalidCharacters(title) -> ValidationResult.invalid("제목에 사용할 수 없는 문자가 포함되어 있습니다")
      else -> ValidationResult.valid()
    }
  }

  fun validateContent(content: String): ValidationResult {
    return when {
      content.length > MAX_CONTENT_LENGTH -> ValidationResult.invalid("내용은 $MAX_CONTENT_LENGTH자를 초과할 수 없습니다")
      content.isBlank() -> ValidationResult.warning("내용이 비어있습니다")
      content.length < MIN_CONTENT_WARNING_LENGTH -> ValidationResult.warning("내용이 너무 짧습니다")
      containsInvalidCharacters(content) -> ValidationResult.invalid("내용에 사용할 수 없는 문자가 포함되어 있습니다")
      else -> ValidationResult.valid()
    }
  }

  fun validateEntry(entry: DiaryEntry): ValidationResult {
    val titleResult = validateTitle(entry.title)
    if (!titleResult.isValid) {
      return titleResult
    }

    val contentResult = validateContent(entry.content)
    if (!contentResult.isValid) {
      return contentResult
    }

    return titleResult.combineWith(contentResult)
  }

  fun validateContentLength(content: String): ValidationResult {
    return when {
      content.length > MAX_CONTENT_LENGTH -> ValidationResult.invalid("내용이 너무 깁니다 (${content.length}/$MAX_CONTENT_LENGTH)")
      content.length > CONTENT_WARNING_LENGTH -> ValidationResult.warning("내용이 깁니다 (${content.length}/$MAX_CONTENT_LENGTH)")
      else -> ValidationResult.valid()
    }
  }

  private fun containsInvalidCharacters(text: String): Boolean {
    return INVALID_CHARACTERS.any { text.contains(it) }
  }

  private fun isValidLength(text: String, maxLength: Int): Boolean {
    return text.length <= maxLength
  }

  companion object {
    private const val MAX_TITLE_LENGTH = 100
    private const val MAX_CONTENT_LENGTH = 10000
    private const val MIN_CONTENT_WARNING_LENGTH = 10
    private const val CONTENT_WARNING_LENGTH = 8000
    
    private val INVALID_CHARACTERS = listOf(
      "\u0000", "\u0001", "\u0002", "\u0003", "\u0004"
    )
  }
}