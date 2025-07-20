package com.flowerdiary.core.database

import com.flowerdiary.core.model.User
import com.flowerdiary.core.types.MediaTimestamp
import com.flowerdiary.core.types.UserId
import com.flowerdiary.core.util.ResultWrapper

/**
 * 사용자 데이터 접근 인터페이스
 * 단일 사용자 앱용 사용자 정보 관리
 */
interface UserRepository {
  
  /**
   * 기본 사용자 조회
   */
  suspend fun getDefaultUser(): ResultWrapper<User>
  
  /**
   * 사용자 ID로 조회
   */
  suspend fun getUserById(id: UserId): ResultWrapper<User>
  
  /**
   * 사용자 저장
   */
  suspend fun saveUser(user: User): ResultWrapper<Unit>
  
  /**
   * 사용자 업데이트
   */
  suspend fun updateUser(user: User): ResultWrapper<Unit>
  
  /**
   * 마지막 활동 시간 업데이트
   */
  suspend fun updateLastActive(id: UserId, timestamp: MediaTimestamp): ResultWrapper<Unit>
  
  /**
   * 일기 작성 횟수 증가
   */
  suspend fun incrementDiaryCount(id: UserId): ResultWrapper<Unit>
  
  /**
   * 탄생화 잠금 해제 횟수 증가
   */
  suspend fun incrementFlowerUnlockCount(id: UserId): ResultWrapper<Unit>
  
  /**
   * 프로필 이미지 경로 업데이트
   */
  suspend fun updateProfileImage(id: UserId, imagePath: String?): ResultWrapper<Unit>
  
  /**
   * 닉네임 업데이트
   */
  suspend fun updateNickname(id: UserId, nickname: String): ResultWrapper<Unit>
  
  /**
   * 사용자 존재 여부 확인
   */
  suspend fun userExists(id: UserId): ResultWrapper<Boolean>
  
  /**
   * 기본 사용자 생성
   */
  suspend fun createDefaultUser(): ResultWrapper<User>
}