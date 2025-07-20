package com.flowerdiary.core.database

import com.flowerdiary.core.model.Media
import com.flowerdiary.core.types.MediaId
import com.flowerdiary.core.util.ResultWrapper

/**
 * 미디어 파일 데이터 접근 인터페이스
 * 이미지, 비디오, 오디오 파일 관리
 */
interface MediaRepository {
  
  /**
   * 모든 미디어 파일 조회
   */
  suspend fun getAllMedia(): ResultWrapper<List<Media>>
  
  /**
   * ID로 미디어 파일 조회
   */
  suspend fun getMediaById(id: MediaId): ResultWrapper<Media>
  
  /**
   * 타입별 미디어 파일 조회
   */
  suspend fun getMediaByType(mimeType: String): ResultWrapper<List<Media>>
  
  /**
   * 이미지 파일만 조회
   */
  suspend fun getImages(): ResultWrapper<List<Media>>
  
  /**
   * 비디오 파일만 조회
   */
  suspend fun getVideos(): ResultWrapper<List<Media>>
  
  /**
   * 오디오 파일만 조회
   */
  suspend fun getAudios(): ResultWrapper<List<Media>>
  
  /**
   * 미디어 파일 저장
   */
  suspend fun saveMedia(media: Media): ResultWrapper<Unit>
  
  /**
   * 미디어 파일 업데이트
   */
  suspend fun updateMedia(media: Media): ResultWrapper<Unit>
  
  /**
   * 미디어 파일 삭제
   */
  suspend fun deleteMedia(id: MediaId): ResultWrapper<Unit>
  
  /**
   * 파일 경로로 미디어 조회
   */
  suspend fun getMediaByPath(filePath: String): ResultWrapper<Media>
  
  /**
   * 파일명으로 미디어 검색
   */
  suspend fun searchMediaByName(fileName: String): ResultWrapper<List<Media>>
  
  /**
   * 파일 크기별 미디어 조회
   */
  suspend fun getMediaBySize(minSize: Long, maxSize: Long): ResultWrapper<List<Media>>
  
  /**
   * 전체 미디어 개수 조회
   */
  suspend fun getMediaCount(): ResultWrapper<Int>
}