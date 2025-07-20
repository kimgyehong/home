package com.flowerdiary.core.model

import com.flowerdiary.core.types.MediaId
import kotlinx.serialization.Serializable

/**
 * 비디오 파일 모델
 * 오프닝 동영상 등
 */
@Serializable
data class Video(
  val id: MediaId,
  val fileName: String,
  val filePath: String,
  val width: Int,
  val height: Int,
  val duration: Long,
  val fileSize: Long
) {
  init {
    require(fileName.isNotBlank()) { "File name cannot be blank" }
    require(filePath.isNotBlank()) { "File path cannot be blank" }
    require(width > 0) { "Width must be positive: $width" }
    require(height > 0) { "Height must be positive: $height" }
    require(duration > 0) { "Duration must be positive: $duration" }
    require(fileSize > 0) { "File size must be positive: $fileSize" }
    require(fileName.endsWith(".mp4")) { "Video file must be MP4 format: $fileName" }
  }

  /**
   * 재생 시간을 MM:SS 형식으로 반환
   */
  fun getFormattedDuration(): String {
    val minutes = (duration / 1000) / 60
    val seconds = (duration / 1000) % 60
    return "%02d:%02d".format(minutes, seconds)
  }

  /**
   * 비디오 종횡비 계산
   */
  fun getAspectRatio(): Float = width.toFloat() / height.toFloat()

  companion object {
    const val OPENING_VIDEO_NAME = "opening.mp4"
  }
}