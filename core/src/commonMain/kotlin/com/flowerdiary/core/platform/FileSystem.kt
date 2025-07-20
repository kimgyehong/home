package com.flowerdiary.core.platform

/**
 * 파일 시스템 접근 인터페이스
 * 플랫폼별 파일 처리 구현 필요
 */
interface FileSystem {
  
  /**
   * 파일 존재 여부 확인
   * @param path 파일 경로
   * @return 파일이 존재하면 true
   */
  suspend fun exists(path: String): Boolean

  /**
   * 파일 읽기
   * @param path 파일 경로
   * @return 파일 내용 바이트 배열
   */
  suspend fun readBytes(path: String): ByteArray

  /**
   * 파일 쓰기
   * @param path 파일 경로
   * @param data 저장할 데이터
   */
  suspend fun writeBytes(path: String, data: ByteArray)

  /**
   * 파일 삭제
   * @param path 파일 경로
   * @return 삭제 성공 여부
   */
  suspend fun delete(path: String): Boolean

  /**
   * 디렉토리 생성
   * @param path 디렉토리 경로
   * @return 생성 성공 여부
   */
  suspend fun createDirectory(path: String): Boolean

  /**
   * 파일 목록 조회
   * @param directoryPath 디렉토리 경로
   * @return 파일명 목록
   */
  suspend fun listFiles(directoryPath: String): List<String>

  /**
   * 파일 크기 조회
   * @param path 파일 경로
   * @return 파일 크기 (바이트)
   */
  suspend fun getFileSize(path: String): Long
}