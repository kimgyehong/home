package com.flowerdiary.common.constants

/**
 * Repository 관련 상수
 * SRP: Repository 계층에서 사용하는 상수만 담당
 * 하드코딩 완전 제거를 위한 중앙집중식 관리
 */
object RepositoryConstants {
    
    /**
     * 로깅 태그
     */
    object LogTags {
        const val DIARY_REPOSITORY = "DiaryRepository"
        const val BIRTH_FLOWER_REPOSITORY = "BirthFlowerRepository"
        const val DIARY_QUERY_EXECUTOR = "DiaryQueryExecutor"
        const val DIARY_DATA_MAPPER = "DiaryDataMapper"
        const val DIARY_ERROR_HANDLER = "DiaryErrorHandler"
        const val FLOW_TRANSFORMER = "FlowTransformer"
    }
    
    /**
     * 쿼리 관련 상수
     */
    object Query {
        const val DEFAULT_LIMIT = 100
        const val DEFAULT_OFFSET = 0
        const val BATCH_SIZE = 50
        const val CACHE_TIMEOUT_MS = 5000L
    }
    
    /**
     * 에러 메시지
     */
    object ErrorMessages {
        const val DIARY_NOT_FOUND = "일기를 찾을 수 없습니다"
        const val SAVE_FAILED = "일기 저장에 실패했습니다"
        const val DELETE_FAILED = "일기 삭제에 실패했습니다"
        const val DATABASE_ERROR = "데이터베이스 오류가 발생했습니다"
        const val MAPPING_ERROR = "데이터 변환 중 오류가 발생했습니다"
        const val QUERY_EXECUTION_ERROR = "쿼리 실행 중 오류가 발생했습니다"
    }
    
    /**
     * 성능 관련 상수
     */
    object Performance {
        const val QUERY_TIMEOUT_MS = 30000L
        const val FLOW_BUFFER_SIZE = 64
        const val COROUTINE_BUFFER_SIZE = 16
    }
}