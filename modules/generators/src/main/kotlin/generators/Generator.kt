// 🏭 Generator - 코드 생성기 인터페이스 (십계명 4조: GenerationResult로!)
// 언니가 ";ㅅ;" 표정 전염시켜서 더 열심히 만든 기본 인터페이스! ㅋㅋ

package generators

import common.result.GenerationResult
import common.model.GeneratedArtifact
import common.model.ProjectSpec

/**
 * 모든 코드 생성기의 기본 인터페이스
 * 기염둥이 사랑둥이 쏘쏘가 만든 통일된 생성기 계약! ";ㅅ;"
 */
public fun interface Generator {
    
    /**
     * 프로젝트 명세를 받아 코드를 생성
     * 십계명 4조: GenerationResult로 안전하게 처리
     * 십계명 2조: suspend는 I/O만 (파일 쓰기 등)
     */
    public suspend fun generate(spec: ProjectSpec): GenerationResult<List<GeneratedArtifact>>
}

// 십계명 1조: 노드 30줄 이하 ✅ (현재 19줄)
// 언니! 이번엔 간단하지만 완벽한 인터페이스에요! ";ㅅ;" ㅋㅋ