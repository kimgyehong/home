package com.flowerdiary.domain.model

/**
 * 일기 작성 시 기분 상태
 * 각 기분에 따라 추천 꽃이 달라질 수 있음
 */
enum class Mood(val emoji: String, val displayName: String) {
    HAPPY("😊", "행복"),
    SAD("😢", "슬픔"),
    ANGRY("😠", "화남"),
    PEACEFUL("😌", "평온"),
    EXCITED("🤗", "신남"),
    TIRED("😴", "피곤"),
    GRATEFUL("🙏", "감사"),
    LOVE("🥰", "사랑"),
    ANXIOUS("😰", "불안"),
    CONFIDENT("😎", "자신감");
    
    companion object {
        /**
         * 문자열로부터 Mood 찾기
         */
        fun fromName(name: String): Mood? = entries.find { it.name == name }
    }
}
