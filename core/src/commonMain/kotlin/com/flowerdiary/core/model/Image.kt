package com.flowerdiary.core.model

import com.flowerdiary.core.types.MediaId
import kotlinx.serialization.Serializable

/**
 * 이미지 파일 모델
 * 탄생화 이미지, UI 요소 이미지 등
 */
@Serializable
data class Image(
  val id: MediaId,
  val fileName: String,
  val filePath: String,
  val width: Int,
  val height: Int,
  val fileSize: Long,
  val format: ImageFormat
) {
  init {
    require(fileName.isNotBlank()) { "File name cannot be blank" }
    require(filePath.isNotBlank()) { "File path cannot be blank" }
    require(width > 0) { "Width must be positive: $width" }
    require(height > 0) { "Height must be positive: $height" }
    require(fileSize > 0) { "File size must be positive: $fileSize" }
  }

  /**
   * 이미지 종횡비 계산
   */
  fun getAspectRatio(): Float = width.toFloat() / height.toFloat()

  /**
   * 정사각형 이미지인지 확인
   */
  fun isSquare(): Boolean = width == height

  /**
   * 가로형 이미지인지 확인
   */
  fun isLandscape(): Boolean = width > height

  /**
   * 세로형 이미지인지 확인
   */
  fun isPortrait(): Boolean = width < height

  /**
   * 파일 크기를 읽기 쉬운 형태로 반환
   */
  fun getReadableFileSize(): String {
    val kb = fileSize / 1024.0
    return if (kb < 1024) "%.1f KB".format(kb) else "%.1f MB".format(kb / 1024)
  }

  /**
   * 이미지 포맷 열거형
   */
  @Serializable
  enum class ImageFormat(val extension: String, val mimeType: String) {
    JPEG("jpg", "image/jpeg"),
    PNG("png", "image/png"),
    WEBP("webp", "image/webp");

    companion object {
      fun fromExtension(extension: String): ImageFormat? {
        return values().find { it.extension.equals(extension, ignoreCase = true) }
      }
    }
  }

  companion object {
    const val MAX_IMAGE_SIZE = 10L * 1024 * 1024 // 10MB
    const val MIN_DIMENSION = 1
    const val MAX_DIMENSION = 4096
  }
}