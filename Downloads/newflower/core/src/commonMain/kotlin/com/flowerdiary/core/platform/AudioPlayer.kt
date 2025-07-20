package com.flowerdiary.core.platform

/**
 * 오디오 재생 인터페이스
 * BGM 재생을 위한 플랫폼별 구현 필요
 */
interface AudioPlayer {
  
  /**
   * 오디오 파일 로드
   * @param filePath 오디오 파일 경로
   */
  suspend fun load(filePath: String)

  /**
   * 재생
   */
  suspend fun play()

  /**
   * 일시정지
   */
  suspend fun pause()

  /**
   * 정지
   */
  suspend fun stop()

  /**
   * 볼륨 설정
   * @param volume 볼륨 (0.0 ~ 1.0)
   */
  suspend fun setVolume(volume: Float)

  /**
   * 반복 재생 설정
   * @param loop 반복 재생 여부
   */
  suspend fun setLoop(loop: Boolean)

  /**
   * 현재 재생 중인지 확인
   * @return 재생 중이면 true
   */
  suspend fun isPlaying(): Boolean

  /**
   * 현재 재생 위치 조회
   * @return 재생 위치 (밀리초)
   */
  suspend fun getCurrentPosition(): Long

  /**
   * 총 재생 시간 조회
   * @return 총 시간 (밀리초)
   */
  suspend fun getDuration(): Long

  /**
   * 리소스 해제
   */
  suspend fun release()
}