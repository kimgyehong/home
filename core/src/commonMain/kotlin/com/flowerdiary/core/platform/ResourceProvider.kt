package com.flowerdiary.core.platform

/**
 * 리소스 파일 접근 인터페이스
 * 탄생화 이미지, BGM, UI 요소 등 리소스 파일 처리
 */
interface ResourceProvider {
  
  /**
   * 탄생화 이미지 경로 조회
   * @param month 월 (1-12)
   * @param day 일 (1-31)
   * @return 탄생화 이미지 파일 경로
   */
  suspend fun getBirthFlowerImagePath(month: Int, day: Int): String

  /**
   * BGM 파일 경로 조회
   * @param trackNumber BGM 트랙 번호 (1-4)
   * @return BGM 파일 경로
   */
  suspend fun getBgmPath(trackNumber: Int): String

  /**
   * 오프닝 비디오 경로 조회
   * @return 오프닝 비디오 파일 경로
   */
  suspend fun getOpeningVideoPath(): String

  /**
   * UI 이미지 경로 조회
   * @param imageName 이미지 파일명
   * @return UI 이미지 파일 경로
   */
  suspend fun getUIImagePath(imageName: String): String

  /**
   * 로딩 배경 이미지 경로 조회
   * @param imageNumber 로딩 이미지 번호 (1-7)
   * @return 로딩 배경 이미지 파일 경로
   */
  suspend fun getLoadingImagePath(imageNumber: Int): String

  /**
   * 리소스 파일 존재 여부 확인
   * @param resourcePath 리소스 파일 경로
   * @return 파일이 존재하면 true
   */
  suspend fun resourceExists(resourcePath: String): Boolean

  /**
   * 리소스 파일을 바이트 배열로 읽기
   * @param resourcePath 리소스 파일 경로
   * @return 파일 내용 바이트 배열
   */
  suspend fun readResourceBytes(resourcePath: String): ByteArray

  companion object {
    const val UI_FOLDER = "ui"
    const val BIRTHFLOWER_FOLDER = "탄생화"
    const val LOADING_FOLDER = "로딩 배경"
    const val APP_COVER_FOLDER = "app cover art"
  }
}