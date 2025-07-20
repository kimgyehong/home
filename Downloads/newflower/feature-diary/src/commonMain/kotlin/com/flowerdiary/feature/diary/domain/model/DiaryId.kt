package com.flowerdiary.feature.diary.domain.model

import com.flowerdiary.core.types.EntityId
import kotlinx.serialization.Serializable

@Serializable
data class DiaryId(val value: String) : EntityId {
  
  init {
    require(value.isNotBlank()) { 
      "DiaryId cannot be blank" 
    }
    require(value.startsWith(PREFIX)) { 
      "DiaryId must start with '$PREFIX'" 
    }
    require(value.length >= MIN_LENGTH) { 
      "DiaryId must be at least $MIN_LENGTH characters" 
    }
    require(value.length <= MAX_LENGTH) { 
      "DiaryId cannot exceed $MAX_LENGTH characters" 
    }
  }

  companion object {
    private const val PREFIX = "diary-"
    private const val MIN_LENGTH = 6
    private const val MAX_LENGTH = 50
    
    fun generate(): DiaryId {
      val timestamp = System.currentTimeMillis()
      return DiaryId("$PREFIX$timestamp")
    }
    
    fun fromString(value: String): DiaryId {
      return DiaryId(value)
    }
  }
}