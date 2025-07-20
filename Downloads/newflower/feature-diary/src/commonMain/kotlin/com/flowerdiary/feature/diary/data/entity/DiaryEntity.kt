package com.flowerdiary.feature.diary.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class DiaryEntity(
  val id: String,
  val title: String,
  val content: String,
  val date: String,
  val birthFlowerId: String?,
  val status: String,
  val createdAt: Long,
  val updatedAt: Long,
  val isDeleted: Boolean = false,
  val version: Int = CURRENT_VERSION
) {

  fun isValid(): Boolean {
    return id.isNotBlank() && 
           title.isNotBlank() &&
           date.isNotBlank() &&
           status.isNotBlank() &&
           createdAt > 0 &&
           updatedAt >= createdAt &&
           version > 0
  }

  fun isEmpty(): Boolean {
    return title.isBlank() && content.isBlank()
  }

  fun copy(
    id: String = this.id,
    title: String = this.title,
    content: String = this.content,
    date: String = this.date,
    birthFlowerId: String? = this.birthFlowerId,
    status: String = this.status,
    createdAt: Long = this.createdAt,
    updatedAt: Long = this.updatedAt,
    isDeleted: Boolean = this.isDeleted,
    version: Int = this.version
  ): DiaryEntity {
    return DiaryEntity(
      id = id,
      title = title,
      content = content,
      date = date,
      birthFlowerId = birthFlowerId,
      status = status,
      createdAt = createdAt,
      updatedAt = updatedAt,
      isDeleted = isDeleted,
      version = version
    )
  }

  companion object {
    private const val CURRENT_VERSION = 1
    
    fun empty(): DiaryEntity {
      val now = System.currentTimeMillis()
      return DiaryEntity(
        id = "",
        title = "",
        content = "",
        date = "",
        birthFlowerId = null,
        status = "draft",
        createdAt = now,
        updatedAt = now
      )
    }
  }
}