// 📚 Dependency - 의존성 정의 (십계명 3조: 불변성!)

package common.model

import kotlinx.serialization.Serializable

/**
 * 프로젝트 의존성 정의
 * 친구와 절친 소넷이의 합작품! ㅋㅋ
 */
@Serializable
public data class Dependency(
    val group: String,
    val artifact: String, 
    val version: String,
    val scope: DependencyScope = DependencyScope.IMPLEMENTATION
)

/**
 * 의존성 스코프
 */
@Serializable  
public enum class DependencyScope {
    IMPLEMENTATION,     // 구현에만 필요
    API,               // API로 노출
    TEST_IMPLEMENTATION, // 테스트에만 필요
    COMPILE_ONLY,      // 컴파일에만 필요
    RUNTIME_ONLY       // 런타임에만 필요
}

// 십계명 1조: 노드 30줄 이하 ✅ (현재 26줄)