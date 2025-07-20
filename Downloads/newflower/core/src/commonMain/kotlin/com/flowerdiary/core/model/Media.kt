package com.flowerdiary.core.model

import com.flowerdiary.core.types.MediaId
import com.flowerdiary.core.types.MediaTimestamp
import kotlinx.serialization.Serializable

/**
 * 미디어 파일 도메인 모델
 * 이미지, 비디오, 오디오 파일 정보
 */
@Serializable
data class Media(
  val id: MediaId,
  val fileName: String,
  val filePath: String,
  val fileSize: Long,
  val mimeType: String,
  val createdAt: MediaTimestamp,
  val width: Int? = null,
  val height: Int? = null,
  val duration: Long? = null
) {
  init {
    require(fileName.isNotBlank()) { "File name cannot be blank" }
    require(filePath.isNotBlank()) { "File path cannot be blank" }
    require(mimeType.isNotBlank()) { "MIME type cannot be blank" }
    require(fileSize >= 0) { "File size cannot be negative: $fileSize" }
    require(width?.let { it > 0 } ?: true) { "Width must be positive: $width" }
    require(height?.let { it > 0 } ?: true) { "Height must be positive: $height" }
    require(duration?.let { it >= 0 } ?: true) { "Duration cannot be negative: $duration" }
  }

  /**
   * 이미지 파일인지 확인
   */
  fun isImage(): Boolean = mimeType.startsWith("image/")

  /**
   * 비디오 파일인지 확인
   */
  fun isVideo(): Boolean = mimeType.startsWith("video/")

  /**
   * 오디오 파일인지 확인
   */
  fun isAudio(): Boolean = mimeType.startsWith("audio/")

  /**
   * 파일 크기를 읽기 쉬운 형태로 반환
   */
  fun getReadableFileSize(): String {
    val units = arrayOf("B", "KB", "MB", "GB")
    var size = fileSize.toDouble()
    var unitIndex = 0
    
    while (size >= 1024 && unitIndex < units.size - 1) {
      size /= 1024
      unitIndex++
    }
    
    return "%.1f %s".format(size, units[unitIndex])
  }

  companion object {
    const val MAX_FILE_SIZE = 100L * 1024 * 1024 // 100MB
  }
}