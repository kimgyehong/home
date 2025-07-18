package com.flowerdiary.feature.diary.common

/**
 * Value Classes for type safety and performance
 * Context7 KMP 모범 사례: 타입 안정성과 성능 최적화
 */

/**
 * 퍼센트 값을 타입 안전하게 표현
 */
@JvmInline
value class Percentage(val value: Int) {
    init {
        require(value in 0..100) { "Percentage must be between 0 and 100" }
    }
    
    override fun toString(): String = "$value%"
}

/**
 * 문자 수를 타입 안전하게 표현
 */
@JvmInline
value class CharCount(val value: Int) {
    init {
        require(value >= 0) { "Character count cannot be negative" }
    }
    
    val isWithinLimit: Boolean get() = value <= MAX_LIMIT
    
    companion object {
        private const val MAX_LIMIT = 1000
    }
}

/**
 * 볼륨 레벨을 타입 안전하게 표현
 */
@JvmInline
value class VolumeLevel(val value: Float) {
    init {
        require(value in 0f..1f) { "Volume level must be between 0.0 and 1.0" }
    }
    
    fun toPercentage(): Percentage = Percentage((value * 100f).toInt())
    val isMuted: Boolean get() = value == 0f
}

/**
 * 트랙 인덱스를 타입 안전하게 표현
 */
@JvmInline
value class TrackIndex(val value: Int) {
    init {
        require(value >= 0) { "Track index cannot be negative" }
    }
    
    fun toDisplayName(): String = "Track ${value + 1}"
}