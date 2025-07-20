package com.flowerdiary.core.database

import com.flowerdiary.core.model.Image
import com.flowerdiary.core.types.MediaId
import com.flowerdiary.core.util.ResultWrapper

/**
 * 이미지 파일 전용 데이터 접근 인터페이스
 * 탄생화 이미지, UI 요소 이미지 관리
 */
interface ImageRepository {
  
  /**
   * 모든 이미지 조회
   */
  suspend fun getAllImages(): ResultWrapper<List<Image>>
  
  /**
   * ID로 이미지 조회
   */
  suspend fun getImageById(id: MediaId): ResultWrapper<Image>
  
  /**
   * 파일명으로 이미지 조회
   */
  suspend fun getImageByFileName(fileName: String): ResultWrapper<Image>
  
  /**
   * 포맷별 이미지 조회
   */
  suspend fun getImagesByFormat(format: Image.ImageFormat): ResultWrapper<List<Image>>
  
  /**
   * 해상도별 이미지 조회
   */
  suspend fun getImagesByResolution(
    minWidth: Int, 
    maxWidth: Int, 
    minHeight: Int, 
    maxHeight: Int
  ): ResultWrapper<List<Image>>
  
  /**
   * 이미지 저장
   */
  suspend fun saveImage(image: Image): ResultWrapper<Unit>
  
  /**
   * 이미지 업데이트
   */
  suspend fun updateImage(image: Image): ResultWrapper<Unit>
  
  /**
   * 이미지 삭제
   */
  suspend fun deleteImage(id: MediaId): ResultWrapper<Unit>
  
  /**
   * 탄생화 이미지 조회
   */
  suspend fun getBirthFlowerImages(): ResultWrapper<List<Image>>
  
  /**
   * UI 이미지 조회
   */
  suspend fun getUIImages(): ResultWrapper<List<Image>>
  
  /**
   * 정사각형 이미지 조회
   */
  suspend fun getSquareImages(): ResultWrapper<List<Image>>
  
  /**
   * 가로형 이미지 조회
   */
  suspend fun getLandscapeImages(): ResultWrapper<List<Image>>
  
  /**
   * 세로형 이미지 조회
   */
  suspend fun getPortraitImages(): ResultWrapper<List<Image>>
}