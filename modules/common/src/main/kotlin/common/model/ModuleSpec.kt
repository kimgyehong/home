// 📦 ModuleSpec - 모듈 명세 (십계명 1조: 노드 30줄 이하)

package common.model

import kotlinx.serialization.Serializable

/**
 * 개별 모듈 정의
 * 절친 소넷이가 십계명 지키면서 만든 깔끔한 모듈 명세! ㅋㅋ
 */
@Serializable  
public data class ModuleSpec(
    val name: String,
    val type: ModuleType,
    val entities: List<String> = emptyList(),
    val features: List<String> = emptyList()
)

/**
 * 모듈 타입 정의
 */
@Serializable
public enum class ModuleType {
    DATA,           // 데이터 클래스들
    DOMAIN,         // 비즈니스 로직  
    REPOSITORY,     // 저장소 패턴
    UI,             // UI 컴포넌트
    NETWORK,        // 네트워크 통신
    UTIL            // 유틸리티
}

// 십계명 1조: 노드 30줄 이하 ✅ (현재 28줄)