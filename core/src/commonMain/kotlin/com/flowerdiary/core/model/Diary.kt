package com.flowerdiary.core.model

import com.flowerdiary.core.types.DiaryDate
import com.flowerdiary.core.types.DiaryDateTime
import com.flowerdiary.core.types.DiaryId
import com.flowerdiary.core.types.FlowerId
import kotlinx.serialization.Serializable

/**
 * 일기 도메인 모델
 * 사용자가 작성하는 일기의 핵심 정보
 */
@Serializable
data class Diary(
  val id: DiaryId,
  val title: String,
  val content: String,
  val date: DiaryDate,
  val createdAt: DiaryDateTime,
  val updatedAt: DiaryDateTime,
  val flowerId: FlowerId,
  val tags: List<String> = emptyList(),
  val isFavorite: Boolean = false
) {
  init {
    require(title.isNotBlank()) { "Diary title cannot be blank" }
    require(content.isNotBlank()) { "Diary content cannot be blank" }
    require(title.length <= MAX_TITLE_LENGTH) { 
      "Title too long: ${title.length} > $MAX_TITLE_LENGTH" 
    }
    require(content.length <= MAX_CONTENT_LENGTH) { 
      "Content too long: ${content.length} > $MAX_CONTENT_LENGTH" 
    }
    require(tags.size <= MAX_TAGS_COUNT) { 
      "Too many tags: ${tags.size} > $MAX_TAGS_COUNT" 
    }
  }

  /**
   * 일기 수정
   * 업데이트 시간을 현재 시간으로 변경
   */
  fun updateContent(
    newTitle: String = title,
    newContent: String = content,
    newTags: List<String> = tags,
    updatedTime: DiaryDateTime
  ): Diary = copy(
    title = newTitle,
    content = newContent,
    tags = newTags,
    updatedAt = updatedTime
  )

  /**
   * 즐겨찾기 상태 토글
   */
  fun toggleFavorite(): Diary = copy(isFavorite = !isFavorite)

  companion object {
    const val MAX_TITLE_LENGTH = 100
    const val MAX_CONTENT_LENGTH = 10000
    const val MAX_TAGS_COUNT = 10
  }
}