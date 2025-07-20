package com.flowerdiary.feature.diary.domain.validator

import kotlinx.serialization.Serializable

@Serializable
sealed class ValidationResult {
  
  abstract val isValid: Boolean
  abstract val errorMessage: String

  @Serializable
  data object Valid : ValidationResult() {
    override val isValid: Boolean = true
    override val errorMessage: String = ""
  }

  @Serializable
  data class Invalid(override val errorMessage: String) : ValidationResult() {
    override val isValid: Boolean = false
  }

  @Serializable
  data class Warning(
    val warningMessage: String,
    override val errorMessage: String = ""
  ) : ValidationResult() {
    override val isValid: Boolean = true
  }

  fun getDisplayMessage(): String {
    return when (this) {
      is Valid -> "유효함"
      is Invalid -> errorMessage
      is Warning -> warningMessage
    }
  }

  fun combineWith(other: ValidationResult): ValidationResult {
    return when {
      !this.isValid -> this
      !other.isValid -> other
      this is Warning -> this
      other is Warning -> other
      else -> Valid
    }
  }

  companion object {
    
    fun valid(): ValidationResult = Valid
    
    fun invalid(message: String): ValidationResult = Invalid(message)
    
    fun warning(message: String): ValidationResult = Warning(message)
    
    fun combine(results: List<ValidationResult>): ValidationResult {
      val firstInvalid = results.firstOrNull { !it.isValid }
      if (firstInvalid != null) {
        return firstInvalid
      }
      
      val firstWarning = results.filterIsInstance<Warning>().firstOrNull()
      return firstWarning ?: Valid
    }
  }
}