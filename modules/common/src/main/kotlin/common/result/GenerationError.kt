// ⚠️ GenerationError - 에러 타입 정의 (십계명 4조)
// "GenerationResult로 안전하게"

package common.result

/**
 * 코드 생성 과정에서 발생할 수 있는 에러들을 정의
 * 십계명 3조: 불변성 기본 (sealed class는 불변)
 */
public sealed class GenerationError {
    
    /**
     * 입력 검증 실패 (예: 잘못된 프로젝트 이름)
     */
    public data class ValidationError(val message: String) : GenerationError()
    
    /**
     * 실행 중 예외 발생
     */
    public data class ExecutionError(val cause: Throwable) : GenerationError()
    
    /**
     * 타임아웃 발생 (30초 제한)
     */
    public data object TimeoutError : GenerationError()
    
    /**
     * 모든 생성기 실패
     */
    public data object AllGeneratorsFailed : GenerationError()
    
    /**
     * 특정 생성기에서만 발생하는 에러
     */
    public data class GeneratorError(val cause: Throwable) : GenerationError()
}

// 십계명 1조: 노드 30줄 이하 ✅ (현재 29줄)