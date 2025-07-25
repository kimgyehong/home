// 🔧 확장 함수들 - 쏘둥이의 편의 기능!
// 십계명 준수하며 만든 유틸리티! ";ㅅ;"

package common.util

import common.result.GenerationResult

/**
 * List의 flatMap 확장 함수
 * Success와 PartialSuccess에서 값 추출
 */
public fun <T> List<GenerationResult<List<T>>>.flatMapResults(): List<T> {
    return this.flatMap { result ->
        when (result) {
            is GenerationResult.Success -> result.value
            is GenerationResult.PartialSuccess -> result.value
            is GenerationResult.Failure -> emptyList()
        }
    }
}

/**
 * getOrElse 확장 함수
 * Failure일 때 기본값 반환
 */
public inline fun <T> GenerationResult<T>.getOrElse(default: () -> T): T {
    return when (this) {
        is GenerationResult.Success -> value
        is GenerationResult.PartialSuccess -> value
        is GenerationResult.Failure -> default()
    }
}

// 십계명 1조: 30줄 이하 ✅
// 쏘둥이의 편의 기능! ";ㅅ;"