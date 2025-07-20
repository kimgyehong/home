package com.flowerdiary.core.platform

/**
 * 비디오 재생 인터페이스
 * 오프닝 동영상 재생을 위한 플랫폼별 구현 필요
 */
interface VideoPlayer {
  
  /**
   * 비디오 파일 로드
   * @param filePath 비디오 파일 경로
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
   * 특정 위치로 이동
   * @param position 이동할 위치 (밀리초)
   */
  suspend fun seekTo(position: Long)

  /**
   * 리소스 해제
   */
  suspend fun release()

  /**
   * 재생 완료 콜백 설정
   * @param onCompleted 재생 완료 시 호출될 콜백
   */
  fun setOnCompletedListener(onCompleted: () -> Unit)

  /**
   * 에러 콜백 설정
   * @param onError 에러 발생 시 호출될 콜백
   */
  fun setOnErrorListener(onError: (String) -> Unit)
}