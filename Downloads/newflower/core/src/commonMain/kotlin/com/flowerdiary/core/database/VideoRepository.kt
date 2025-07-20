package com.flowerdiary.core.database

import com.flowerdiary.core.model.Video
import com.flowerdiary.core.types.MediaId
import com.flowerdiary.core.util.ResultWrapper

/**
 * 비디오 파일 전용 데이터 접근 인터페이스
 * 오프닝 비디오 등 동영상 파일 관리
 */
interface VideoRepository {
  
  /**
   * 모든 비디오 조회
   */
  suspend fun getAllVideos(): ResultWrapper<List<Video>>
  
  /**
   * ID로 비디오 조회
   */
  suspend fun getVideoById(id: MediaId): ResultWrapper<Video>
  
  /**
   * 파일명으로 비디오 조회
   */
  suspend fun getVideoByFileName(fileName: String): ResultWrapper<Video>
  
  /**
   * 오프닝 비디오 조회
   */
  suspend fun getOpeningVideo(): ResultWrapper<Video>
  
  /**
   * 재생 시간별 비디오 조회
   */
  suspend fun getVideosByDuration(
    minDuration: Long, 
    maxDuration: Long
  ): ResultWrapper<List<Video>>
  
  /**
   * 해상도별 비디오 조회
   */
  suspend fun getVideosByResolution(
    minWidth: Int, 
    maxWidth: Int, 
    minHeight: Int, 
    maxHeight: Int
  ): ResultWrapper<List<Video>>
  
  /**
   * 비디오 저장
   */
  suspend fun saveVideo(video: Video): ResultWrapper<Unit>
  
  /**
   * 비디오 업데이트
   */
  suspend fun updateVideo(video: Video): ResultWrapper<Unit>
  
  /**
   * 비디오 삭제
   */
  suspend fun deleteVideo(id: MediaId): ResultWrapper<Unit>
  
  /**
   * 파일 크기별 비디오 조회
   */
  suspend fun getVideosByFileSize(
    minSize: Long, 
    maxSize: Long
  ): ResultWrapper<List<Video>>
  
  /**
   * 가로형 비디오 조회
   */
  suspend fun getLandscapeVideos(): ResultWrapper<List<Video>>
  
  /**
   * 세로형 비디오 조회
   */
  suspend fun getPortraitVideos(): ResultWrapper<List<Video>>
}