package com.flowerdiary.core.model

import com.flowerdiary.core.types.MediaId
import kotlinx.serialization.Serializable

/**
 * BGM 오디오 트랙 모델
 * 앱에서 사용되는 배경음악 정보
 */
@Serializable
data class AudioTrack(
  val id: MediaId,
  val trackNumber: Int,
  val fileName: String,
  val filePath: String,
  val title: String,
  val duration: Long,
  val isDefault: Boolean = false
) {
  init {
    require(trackNumber > 0) { "Track number must be positive: $trackNumber" }
    require(fileName.isNotBlank()) { "File name cannot be blank" }
    require(filePath.isNotBlank()) { "File path cannot be blank" }
    require(title.isNotBlank()) { "Title cannot be blank" }
    require(duration > 0) { "Duration must be positive: $duration" }
    require(fileName.endsWith(".mp3")) { "Audio file must be MP3 format: $fileName" }
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
   * 기본 BGM인지 확인
   */
  fun isDefaultTrack(): Boolean = isDefault

  companion object {
    const val BGM_1_TRACK = 1
    const val BGM_2_TRACK = 2
    const val BGM_3_TRACK = 3
    const val BGM_4_TRACK = 4
    const val MIN_TRACK_NUMBER = 1
    const val MAX_TRACK_NUMBER = 4

    /**
     * 트랙 번호가 유효한지 확인
     */
    fun isValidTrackNumber(trackNumber: Int): Boolean {
      return trackNumber in MIN_TRACK_NUMBER..MAX_TRACK_NUMBER
    }
  }
}