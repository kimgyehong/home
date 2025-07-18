package com.flowerdiary.domain.model

/**
 * 일기 ID 값 객체
 * 타입 안정성을 위한 inline value class 사용
 */
value class DiaryId(val value: String) {
    init {
        require(value.isNotBlank()) { "DiaryId cannot be blank" }
    }
    
    companion object {
        /**
         * 새로운 일기 ID 생성
         * 타임스탬프 기반 고유 ID
         */
        fun generate(): DiaryId = DiaryId("diary_${com.flowerdiary.common.platform.DateTimeUtil.now()}_${(0..com.flowerdiary.common.constants.Config.ID_RANDOM_RANGE).random()}")
    }
}
