package com.flowerdiary.core.types

import kotlinx.serialization.Serializable

/**
 * 미디어 파일의 고유 식별자
 * 이미지, 비디오, 오디오 파일을 구분
 */
@Serializable
data class MediaId(override val value: String) : EntityId {
  init {
    require(hasValidPrefix()) { 
      "MediaId must have valid prefix: $value" 
    }
    require(EntityId.validateFormat(value, getPrefix())) { 
      "Invalid MediaId format: $value" 
    }
  }

  val mediaType: MediaType
    get() = when {
      value.startsWith(IMAGE_PREFIX) -> MediaType.IMAGE
      value.startsWith(VIDEO_PREFIX) -> MediaType.VIDEO
      value.startsWith(AUDIO_PREFIX) -> MediaType.AUDIO
      else -> MediaType.UNKNOWN
    }

  private fun hasValidPrefix(): Boolean {
    return value.startsWith(IMAGE_PREFIX) || 
           value.startsWith(VIDEO_PREFIX) || 
           value.startsWith(AUDIO_PREFIX)
  }

  private fun getPrefix(): String {
    return when {
      value.startsWith(IMAGE_PREFIX) -> IMAGE_PREFIX
      value.startsWith(VIDEO_PREFIX) -> VIDEO_PREFIX
      value.startsWith(AUDIO_PREFIX) -> AUDIO_PREFIX
      else -> ""
    }
  }

  enum class MediaType {
    IMAGE, VIDEO, AUDIO, UNKNOWN
  }

  companion object {
    private const val IMAGE_PREFIX = "img-"
    private const val VIDEO_PREFIX = "vid-"
    private const val AUDIO_PREFIX = "aud-"

    /**
     * 미디어 타입별 MediaId 생성
     */
    fun createImage(suffix: String = generateSuffix()): MediaId = 
      MediaId("$IMAGE_PREFIX$suffix")

    fun createVideo(suffix: String = generateSuffix()): MediaId = 
      MediaId("$VIDEO_PREFIX$suffix")

    fun createAudio(suffix: String = generateSuffix()): MediaId = 
      MediaId("$AUDIO_PREFIX$suffix")

    private fun generateSuffix(): String {
      return kotlin.random.Random.nextLong(100000, 999999).toString()
    }
  }
}