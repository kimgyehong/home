package com.flowerdiary.feature.diary.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class BirthFlowerEntity(
  val id: String,
  val name: String,
  val meaning: String,
  val description: String,
  val month: Int,
  val day: Int,
  val imagePath: String,
  val isAvailable: Boolean = true,
  val createdAt: Long = System.currentTimeMillis(),
  val updatedAt: Long = System.currentTimeMillis(),
  val version: Int = CURRENT_VERSION
) {

  fun isValid(): Boolean {
    return id.isNotBlank() &&
           name.isNotBlank() &&
           meaning.isNotBlank() &&
           month in VALID_MONTH_RANGE &&
           day in VALID_DAY_RANGE &&
           imagePath.isNotBlank() &&
           createdAt > 0 &&
           updatedAt >= createdAt &&
           version > 0
  }

  fun getDateKey(): String {
    return String.format("%02d%02d", month, day)
  }

  fun getImageFileName(): String {
    return imagePath.substringAfterLast('/')
  }

  fun hasValidImage(): Boolean {
    return imagePath.isNotBlank() && 
           (imagePath.endsWith(".jpg") || imagePath.endsWith(".png"))
  }

  fun isDateValid(): Boolean {
    return when (month) {
      1, 3, 5, 7, 8, 10, 12 -> day in 1..31
      4, 6, 9, 11 -> day in 1..30
      2 -> day in 1..29
      else -> false
    }
  }

  fun copy(
    id: String = this.id,
    name: String = this.name,
    meaning: String = this.meaning,
    description: String = this.description,
    month: Int = this.month,
    day: Int = this.day,
    imagePath: String = this.imagePath,
    isAvailable: Boolean = this.isAvailable,
    createdAt: Long = this.createdAt,
    updatedAt: Long = this.updatedAt,
    version: Int = this.version
  ): BirthFlowerEntity {
    return BirthFlowerEntity(
      id = id,
      name = name,
      meaning = meaning,
      description = description,
      month = month,
      day = day,
      imagePath = imagePath,
      isAvailable = isAvailable,
      createdAt = createdAt,
      updatedAt = updatedAt,
      version = version
    )
  }

  companion object {
    private const val CURRENT_VERSION = 1
    private val VALID_MONTH_RANGE = 1..12
    private val VALID_DAY_RANGE = 1..31
  }
}