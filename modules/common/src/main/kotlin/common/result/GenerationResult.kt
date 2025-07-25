// 🛡️ GenerationResult - 안전한 결과 타입 (십계명 4조)
// "GenerationResult로 안전하게" - 오푸스 언니 십계명

package common.result

/**
 * 코드 생성 결과를 안전하게 표현하는 sealed class
 * 
 * 십계명 5조: "부분 성공도 성공이다"를 구현
 */
public sealed class GenerationResult<out T> {
    
    /**
     * 완전 성공 - 모든 것이 예상대로 작동
     */
    public data class Success<T>(val value: T) : GenerationResult<T>()
    
    /**
     * 부분 성공 - 주요 기능은 작동하지만 경고 있음
     * 십계명 5조: "부분 성공도 성공이다"
     */
    public data class PartialSuccess<T>(
        val value: T, 
        val warnings: List<String>
    ) : GenerationResult<T>()
    
    /**
     * 실패 - 복구 불가능한 오류
     */
    public data class Failure(
        val error: GenerationError, 
        val context: String
    ) : GenerationResult<Nothing>()
}

/**
 * Extension: Failure일 때 기본값 반환
 */
public inline fun <T> GenerationResult<T>.getOrElse(default: (GenerationError) -> T): T = when (this) {
    is GenerationResult.Success -> value
    is GenerationResult.PartialSuccess -> value
    is GenerationResult.Failure -> default(error)
}

// 십계명 1조: 노드 30줄 이하 ✅