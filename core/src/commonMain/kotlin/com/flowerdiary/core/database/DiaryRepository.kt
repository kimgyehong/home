package com.flowerdiary.core.database

import com.flowerdiary.core.model.Diary
import com.flowerdiary.core.types.DiaryDate
import com.flowerdiary.core.types.DiaryId
import com.flowerdiary.core.types.FlowerId
import com.flowerdiary.core.util.ResultWrapper
import kotlinx.coroutines.flow.Flow

/**
 * 일기 데이터 접근 인터페이스
 * 일기 CRUD 작업 정의
 */
interface DiaryRepository {
  
  /**
   * 모든 일기 조회
   */
  fun getAllDiaries(): Flow<List<Diary>>
  
  /**
   * ID로 일기 조회
   */
  suspend fun getDiaryById(id: DiaryId): ResultWrapper<Diary>
  
  /**
   * 날짜로 일기 조회
   */
  suspend fun getDiaryByDate(date: DiaryDate): ResultWrapper<Diary>
  
  /**
   * 탄생화로 일기 목록 조회
   */
  suspend fun getDiariesByFlower(flowerId: FlowerId): ResultWrapper<List<Diary>>
  
  /**
   * 즐겨찾기 일기 목록 조회
   */
  fun getFavoriteDiaries(): Flow<List<Diary>>
  
  /**
   * 일기 저장
   */
  suspend fun saveDiary(diary: Diary): ResultWrapper<Unit>
  
  /**
   * 일기 업데이트
   */
  suspend fun updateDiary(diary: Diary): ResultWrapper<Unit>
  
  /**
   * 일기 삭제
   */
  suspend fun deleteDiary(id: DiaryId): ResultWrapper<Unit>
  
  /**
   * 태그로 일기 검색
   */
  suspend fun searchDiariesByTag(tag: String): ResultWrapper<List<Diary>>
  
  /**
   * 제목이나 내용으로 일기 검색
   */
  suspend fun searchDiaries(query: String): ResultWrapper<List<Diary>>
  
  /**
   * 전체 일기 개수 조회
   */
  suspend fun getDiaryCount(): ResultWrapper<Int>
}