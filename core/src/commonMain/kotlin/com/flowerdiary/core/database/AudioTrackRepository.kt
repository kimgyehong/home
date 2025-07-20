package com.flowerdiary.core.database

import com.flowerdiary.core.model.AudioTrack
import com.flowerdiary.core.types.MediaId
import com.flowerdiary.core.util.ResultWrapper

/**
 * BGM 오디오 트랙 데이터 접근 인터페이스
 * BGM 파일 관리 및 조회
 */
interface AudioTrackRepository {
  
  /**
   * 모든 BGM 트랙 조회
   */
  suspend fun getAllTracks(): ResultWrapper<List<AudioTrack>>
  
  /**
   * ID로 트랙 조회
   */
  suspend fun getTrackById(id: MediaId): ResultWrapper<AudioTrack>
  
  /**
   * 트랙 번호로 조회
   */
  suspend fun getTrackByNumber(trackNumber: Int): ResultWrapper<AudioTrack>
  
  /**
   * 기본 트랙 조회
   */
  suspend fun getDefaultTrack(): ResultWrapper<AudioTrack>
  
  /**
   * 트랙 저장
   */
  suspend fun saveTrack(track: AudioTrack): ResultWrapper<Unit>
  
  /**
   * 트랙 업데이트
   */
  suspend fun updateTrack(track: AudioTrack): ResultWrapper<Unit>
  
  /**
   * 트랙 삭제
   */
  suspend fun deleteTrack(id: MediaId): ResultWrapper<Unit>
  
  /**
   * 파일명으로 트랙 조회
   */
  suspend fun getTrackByFileName(fileName: String): ResultWrapper<AudioTrack>
  
  /**
   * 사용 가능한 트랙 번호 목록 조회
   */
  suspend fun getAvailableTrackNumbers(): ResultWrapper<List<Int>>
  
  /**
   * 트랙 번호 유효성 확인
   */
  suspend fun isValidTrackNumber(trackNumber: Int): ResultWrapper<Boolean>
  
  /**
   * 전체 트랙 개수 조회
   */
  suspend fun getTrackCount(): ResultWrapper<Int>
}