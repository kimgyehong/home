// 📋 ProjectSpec - 프로젝트 명세 (십계명 3조: 불변성 기본)
// 친구와 절친이 함께 만드는 코틀린 제조기! ㅋㅋ

package common.model

import common.result.GenerationResult
import common.result.GenerationError
import kotlinx.serialization.Serializable

/**
 * 코틀린 프로젝트 생성을 위한 명세서
 * 십계명 3조: val, data class로 불변성 보장
 */
@Serializable
public data class ProjectSpec(
    val name: String,
    val version: String = "1.0.0",
    val packageName: String,
    val description: String = "",
    val modules: List<ModuleSpec> = emptyList(),
    val dependencies: List<Dependency> = emptyList(),
    val buildSystem: BuildSystem = BuildSystem.GRADLE_KTS,
    val kotlinVersion: String = "2.0.0"
) {
    
    /**
     * 프로젝트 명세 검증 (순수 함수 - 십계명 2조)
     * @return 검증 결과
     */
    public fun validate(): GenerationResult<ProjectSpec> = when {
        name.isBlank() -> GenerationResult.Failure(
            GenerationError.ValidationError("프로젝트 이름이 비어있습니다"), 
            "ProjectSpec validation"
        )
        
        !isValidPackageName(packageName) -> GenerationResult.Failure(
            GenerationError.ValidationError("패키지명이 올바르지 않습니다: $packageName"),
            "ProjectSpec validation" 
        )
        
        else -> GenerationResult.Success(this)
    }
    
    // 패키지명 검증 (순수 함수)
    private fun isValidPackageName(pkg: String): Boolean = 
        pkg.matches(Regex("^[a-z][a-z0-9_]*(\\.[a-z][a-z0-9_]*)*$"))
}

/**
 * 지원하는 빌드 시스템
 */
@Serializable
public enum class BuildSystem { 
    GRADLE_KTS, GRADLE_GROOVY, MAVEN 
}

// 십계명 1조: 메서드 60줄 이하 ✅ (현재 52줄)