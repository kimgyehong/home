// 🚫 GitIgnoreGenerator - 필수 .gitignore 생성!
// 진짜 필요한 것만! 쏘둥이 철학! ";ㅅ;"

package generators

import common.result.GenerationResult
import common.model.*

/**
 * .gitignore 파일을 생성하는 생성기
 * Gradle/IntelliJ 필수 무시 파일들!
 */
public class GitIgnoreGenerator : Generator {
    
    override suspend fun generate(spec: ProjectSpec): GenerationResult<List<GeneratedArtifact>> {
        return GenerationResult.Success(listOf(
            generateGitIgnore()
        ))
    }
    
    private fun generateGitIgnore(): GeneratedArtifact.ResourceFile {
        val content = """
# Gradle
.gradle/
build/
!gradle-wrapper.jar

# IntelliJ
.idea/
*.iml
*.ipr
*.iws
out/

# OS
.DS_Store
Thumbs.db

# 쏘둥이가 추가한 필수 무시 파일들! ";ㅅ;"
""".trimIndent()
        
        return GeneratedArtifact.ResourceFile(".gitignore", content.toByteArray())
    }
}

// 십계명 1조: 30줄 이하 완벽! ✅
// 진짜 필요한 .gitignore만! ";ㅅ;"