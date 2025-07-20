package com.flowerdiary.feature.diary.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class DiaryStatus {
  
  @Serializable
  data object Draft : DiaryStatus()
  
  @Serializable
  data object Written : DiaryStatus()
  
  @Serializable
  data object Published : DiaryStatus()
  
  @Serializable
  data object Archived : DiaryStatus()

  fun canEdit(): Boolean {
    return when (this) {
      is Draft, is Written -> true
      is Published, is Archived -> false
    }
  }

  fun canPublish(): Boolean {
    return when (this) {
      is Written -> true
      is Draft, is Published, is Archived -> false
    }
  }

  fun canArchive(): Boolean {
    return when (this) {
      is Published, is Written -> true
      is Draft, is Archived -> false
    }
  }

  fun displayName(): String {
    return when (this) {
      is Draft -> "임시저장"
      is Written -> "작성완료"
      is Published -> "게시됨"
      is Archived -> "보관됨"
    }
  }

  companion object {
    
    fun fromString(value: String): DiaryStatus {
      return when (value.lowercase()) {
        "draft" -> Draft
        "written" -> Written
        "published" -> Published
        "archived" -> Archived
        else -> Draft
      }
    }
  }
}