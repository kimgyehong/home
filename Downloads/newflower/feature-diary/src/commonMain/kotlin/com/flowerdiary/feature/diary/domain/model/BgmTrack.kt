package com.flowerdiary.feature.diary.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class BgmTrack(val displayName: String, val fileName: String) {
  BGM1("배경음악 1", "bgm.mp3"),
  BGM2("배경음악 2", "bgm2.mp3"),
  BGM3("배경음악 3", "bgm3.mp3"),
  BGM4("배경음악 4", "bgm4.mp3");

  fun getResourcePath(): String {
    return "audio/$fileName"
  }

  fun isValid(): Boolean {
    return fileName.isNotBlank() && fileName.endsWith(".mp3")
  }

  companion object {
    
    fun fromFileName(fileName: String): BgmTrack? {
      return entries.find { it.fileName == fileName }
    }
    
    fun fromDisplayName(displayName: String): BgmTrack? {
      return entries.find { it.displayName == displayName }
    }
    
    fun getAllTracks(): List<BgmTrack> {
      return entries.toList()
    }
    
    fun getDefault(): BgmTrack {
      return BGM1
    }
    
    fun getRandomTrack(): BgmTrack {
      return entries.random()
    }
  }
}