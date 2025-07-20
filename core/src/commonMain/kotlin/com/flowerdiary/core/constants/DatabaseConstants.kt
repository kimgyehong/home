package com.flowerdiary.core.constants

/**
 * 데이터베이스 관련 상수들
 */
object DatabaseConstants {
  
  /**
   * 데이터베이스 설정
   */
  const val DATABASE_NAME = "flower_diary.db"
  const val DATABASE_VERSION = 1
  const val CONNECTION_POOL_SIZE = 5
  const val QUERY_TIMEOUT = 30000L
  
  /**
   * 테이블명
   */
  const val TABLE_DIARY = "diary"
  const val TABLE_BIRTH_FLOWER = "birth_flower"
  const val TABLE_MEDIA = "media"
  const val TABLE_USER = "user"
  const val TABLE_SETTINGS = "settings"
  const val TABLE_AUDIO_TRACK = "audio_track"
  
  /**
   * 컬럼명 - Diary
   */
  const val DIARY_ID = "id"
  const val DIARY_TITLE = "title"
  const val DIARY_CONTENT = "content"
  const val DIARY_DATE = "date"
  const val DIARY_CREATED_AT = "created_at"
  const val DIARY_UPDATED_AT = "updated_at"
  const val DIARY_FLOWER_ID = "flower_id"
  const val DIARY_TAGS = "tags"
  const val DIARY_IS_FAVORITE = "is_favorite"
  
  /**
   * 컬럼명 - BirthFlower
   */
  const val FLOWER_ID = "id"
  const val FLOWER_NAME = "name"
  const val FLOWER_DATE = "date"
  const val FLOWER_MEANING = "meaning"
  const val FLOWER_DESCRIPTION = "description"
  const val FLOWER_IMAGE_ID = "image_id"
  const val FLOWER_COLOR_HEX = "color_hex"
  const val FLOWER_IS_UNLOCKED = "is_unlocked"
  const val FLOWER_UNLOCK_COUNT = "unlock_count"
  
  /**
   * 인덱스명
   */
  const val INDEX_DIARY_DATE = "idx_diary_date"
  const val INDEX_DIARY_CREATED_AT = "idx_diary_created_at"
  const val INDEX_FLOWER_DATE = "idx_flower_date"
  const val INDEX_FLOWER_UNLOCKED = "idx_flower_unlocked"
  
  /**
   * 쿼리 제한
   */
  const val MAX_QUERY_LIMIT = 1000
  const val DEFAULT_PAGE_SIZE = 20
  const val MAX_BATCH_SIZE = 100
  
  /**
   * 트랜잭션
   */
  const val TRANSACTION_TIMEOUT = 10000L
  const val MAX_RETRY_ATTEMPTS = 3
  const val RETRY_DELAY = 1000L
}