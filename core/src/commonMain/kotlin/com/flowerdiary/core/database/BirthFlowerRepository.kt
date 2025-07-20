package com.flowerdiary.core.database

import com.flowerdiary.core.model.BirthFlower
import com.flowerdiary.core.types.FlowerId
import com.flowerdiary.core.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

/**
 * 탄생화 데이터 접근 인터페이스
 * 탄생화 조회 및 잠금 해제 작업
 */
interface BirthFlowerRepository {
  
  /**
   * 모든 탄생화 조회
   */
  fun getAllBirthFlowers(): Flow<List<BirthFlower>>
  
  /**
   * ID로 탄생화 조회
   */
  suspend fun getBirthFlowerById(id: FlowerId): ResultWrapper<BirthFlower>
  
  /**
   * 월과 일로 탄생화 조회
   */
  suspend fun getBirthFlowerByDate(month: Int, day: Int): ResultWrapper<BirthFlower>
  
  /**
   * 잠금 해제된 탄생화 목록 조회
   */
  fun getUnlockedBirthFlowers(): Flow<List<BirthFlower>>
  
  /**
   * 잠금된 탄생화 목록 조회
   */
  fun getLockedBirthFlowers(): Flow<List<BirthFlower>>
  
  /**
   * 탄생화 저장 (초기 데이터 삽입)
   */
  suspend fun saveBirthFlower(birthFlower: BirthFlower): ResultWrapper<Unit>
  
  /**
   * 탄생화 잠금 해제
   */
  suspend fun unlockBirthFlower(id: FlowerId): ResultWrapper<Unit>
  
  /**
   * 탄생화 잠금 해제 횟수 증가
   */
  suspend fun incrementUnlockCount(id: FlowerId): ResultWrapper<Unit>
  
  /**
   * 월별 탄생화 목록 조회
   */
  suspend fun getBirthFlowersByMonth(month: Int): ResultWrapper<List<BirthFlower>>
  
  /**
   * 색상으로 탄생화 검색
   */
  suspend fun searchBirthFlowersByColor(colorHex: String): ResultWrapper<List<BirthFlower>>
  
  /**
   * 이름으로 탄생화 검색
   */
  suspend fun searchBirthFlowersByName(name: String): ResultWrapper<List<BirthFlower>>
  
  /**
   * 전체 탄생화 개수 조회
   */
  suspend fun getBirthFlowerCount(): ResultWrapper<Int>
  
  /**
   * 잠금 해제된 탄생화 개수 조회
   */
  suspend fun getUnlockedCount(): ResultWrapper<Int>
}