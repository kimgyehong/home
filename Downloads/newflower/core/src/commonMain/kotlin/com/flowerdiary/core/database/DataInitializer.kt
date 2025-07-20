package com.flowerdiary.core.database

import com.flowerdiary.core.model.BirthFlower
import com.flowerdiary.core.model.Settings
import com.flowerdiary.core.model.User
import com.flowerdiary.core.util.ResultWrapper

/**
 * 초기 데이터 삽입 인터페이스
 * 앱 최초 실행 시 필요한 기본 데이터 생성
 */
interface DataInitializer {
  
  /**
   * 모든 초기 데이터 삽입
   */
  suspend fun initializeAllData(): ResultWrapper<Unit>
  
  /**
   * 365개 탄생화 데이터 삽입
   */
  suspend fun initializeBirthFlowers(): ResultWrapper<Unit>
  
  /**
   * 기본 사용자 생성
   */
  suspend fun initializeDefaultUser(): ResultWrapper<User>
  
  /**
   * 기본 설정 생성
   */
  suspend fun initializeDefaultSettings(): ResultWrapper<Settings>
  
  /**
   * BGM 트랙 데이터 삽입
   */
  suspend fun initializeAudioTracks(): ResultWrapper<Unit>
  
  /**
   * 기본 미디어 파일 등록
   */
  suspend fun initializeMediaFiles(): ResultWrapper<Unit>
  
  /**
   * 탄생화 데이터 검증 및 수정
   */
  suspend fun validateBirthFlowerData(): ResultWrapper<List<String>>
  
  /**
   * 초기화 상태 확인
   */
  suspend fun isInitialized(): ResultWrapper<Boolean>
  
  /**
   * 특정 월의 탄생화 데이터 삽입
   */
  suspend fun initializeBirthFlowersForMonth(month: Int): ResultWrapper<Unit>
  
  /**
   * 초기 데이터 삭제 (테스트용)
   */
  suspend fun clearAllData(): ResultWrapper<Unit>
  
  /**
   * 손상된 데이터 복구
   */
  suspend fun repairCorruptedData(): ResultWrapper<List<String>>
  
  /**
   * 데이터 무결성 검사
   */
  suspend fun validateDataIntegrity(): ResultWrapper<Boolean>
  
  /**
   * 초기화 진행률 조회
   */
  suspend fun getInitializationProgress(): ResultWrapper<Int>
}

/**
 * 초기화 상태
 */
enum class InitializationState {
  NOT_STARTED,
  IN_PROGRESS,
  COMPLETED,
  FAILED
}

/**
 * 초기화 결과
 */
data class InitializationResult(
  val state: InitializationState,
  val completedSteps: Int,
  val totalSteps: Int,
  val errors: List<String> = emptyList()
)