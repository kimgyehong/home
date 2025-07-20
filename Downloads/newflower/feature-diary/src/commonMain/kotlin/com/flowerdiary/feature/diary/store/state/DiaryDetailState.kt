package com.flowerdiary.feature.diary.store.state

import com.flowerdiary.core.model.BirthFlower
import com.flowerdiary.feature.diary.domain.model.Diary
import com.flowerdiary.feature.diary.domain.model.DiaryId
import kotlinx.serialization.Serializable

@Serializable
data class DiaryDetailState(
  val diary: Diary? = null,
  val birthFlower: BirthFlower? = null,
  val isLoading: Boolean = false,
  val isDeleting: Boolean = false,
  val errorMessage: String? = null,
  val showDeleteConfirmation: Boolean = false,
  val showShareOptions: Boolean = false,
  val isArchiving: Boolean = false,
  val isPublishing: Boolean = false
) {

  fun hasDiary(): Boolean {
    return diary != null
  }

  fun isProcessing(): Boolean {
    return isLoading || isDeleting || isArchiving || isPublishing
  }

  fun canEdit(): Boolean {
    return diary?.status?.canEdit() == true && !isProcessing()
  }

  fun canDelete(): Boolean {
    return diary != null && !isProcessing()
  }

  fun canPublish(): Boolean {
    return diary?.status?.canPublish() == true && !isProcessing()
  }

  fun canArchive(): Boolean {
    return diary?.status?.canArchive() == true && !isProcessing()
  }

  fun canShare(): Boolean {
    return diary != null && !diary.isEmpty() && !isProcessing()
  }

  fun hasError(): Boolean {
    return errorMessage != null
  }

  fun getDiaryId(): DiaryId? {
    return diary?.id
  }

  fun getTitle(): String {
    return diary?.entry?.title ?: ""
  }

  fun getContent(): String {
    return diary?.entry?.content ?: ""
  }

  fun getDateString(): String {
    return diary?.entry?.date?.toString() ?: ""
  }

  fun getStatus(): String {
    return diary?.status?.displayName() ?: ""
  }

  fun getBirthFlowerName(): String {
    return birthFlower?.name ?: ""
  }

  fun getBirthFlowerMeaning(): String {
    return birthFlower?.meaning ?: ""
  }

  fun hasValidContent(): Boolean {
    return diary?.let { !it.isEmpty() } ?: false
  }

  companion object {
    
    fun loading(): DiaryDetailState {
      return DiaryDetailState(isLoading = true)
    }
    
    fun loaded(diary: Diary, birthFlower: BirthFlower? = null): DiaryDetailState {
      return DiaryDetailState(
        diary = diary,
        birthFlower = birthFlower
      )
    }
    
    fun error(message: String): DiaryDetailState {
      return DiaryDetailState(errorMessage = message)
    }
    
    fun empty(): DiaryDetailState {
      return DiaryDetailState()
    }
    
    fun deleting(diary: Diary): DiaryDetailState {
      return DiaryDetailState(
        diary = diary,
        isDeleting = true
      )
    }
  }
}