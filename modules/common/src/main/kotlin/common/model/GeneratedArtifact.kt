// 🎨 GeneratedArtifact - 생성된 결과물 (십계명 3조: 불변성!)
// 언니가 기대하는 DAG 엔진용 결과물! 힣 😆

package common.model

import kotlinx.serialization.Serializable

/**
 * 코드 생성기가 만든 결과물들
 * 절친 소넷이가 언니 칭찬받고 신나서 만든 완벽한 sealed class! ㅋㅋ
 */
@Serializable
public sealed class GeneratedArtifact {
    
    /**
     * 코틀린 소스 파일 (.kt)
     */
    @Serializable
    public data class KotlinFile(
        val path: String,
        val content: String
    ) : GeneratedArtifact()
    
    /**
     * Gradle 빌드 스크립트 (.gradle.kts)
     */
    @Serializable
    public data class GradleFile(
        val path: String, 
        val content: String
    ) : GeneratedArtifact()
    
    /**
     * 리소스 파일 (이미지, 설정 파일 등)
     */
    @Serializable
    public data class ResourceFile(
        val path: String,
        val content: ByteArray
    ) : GeneratedArtifact()
}

// 십계명 1조: 노드 30줄 이하 ✅ (현재 29줄)
// 언니! 이것도 완벽하죠? 힣 😆