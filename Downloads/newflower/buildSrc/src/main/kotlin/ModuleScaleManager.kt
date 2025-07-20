import org.gradle.api.GradleException
import org.gradle.api.Project

/**
 * 모듈 크기 자동 관리
 * Fat Module 방지를 위한 파일 수 제한
 */
object ModuleScaleManager {
  
  private const val MAX_FILES_PER_MODULE = 75
  private const val WARNING_FILES_PER_MODULE = 60
  private const val CORE_MODULE_LIMIT = 40
  private const val FEATURE_MODULE_LIMIT = 50
  private const val SHARED_MODULE_LIMIT = 20
  
  /**
   * 모듈 크기 검증
   */
  fun checkModuleSize(project: Project) {
    val sourceFiles = getKotlinSourceFiles(project)
    val moduleLimit = getModuleLimit(project.name)
    
    when {
      sourceFiles.size > moduleLimit -> {
        throw GradleException(generateErrorMessage(project, sourceFiles.size, moduleLimit))
      }
      
      sourceFiles.size > WARNING_FILES_PER_MODULE -> {
        project.logger.warn(generateWarningMessage(project, sourceFiles.size, moduleLimit))
      }
      
      else -> {
        project.logger.info("✅ Module ${project.name}: ${sourceFiles.size}/$moduleLimit files")
      }
    }
  }
  
  /**
   * 모듈별 파일 제한 조회
   */
  private fun getModuleLimit(moduleName: String): Int {
    return when {
      moduleName.contains("core") -> CORE_MODULE_LIMIT
      moduleName.startsWith("feature-") -> FEATURE_MODULE_LIMIT
      moduleName.contains("shared") -> SHARED_MODULE_LIMIT
      else -> MAX_FILES_PER_MODULE
    }
  }
  
  /**
   * Kotlin 소스 파일 조회
   */
  private fun getKotlinSourceFiles(project: Project): List<java.io.File> {
    return project.fileTree("src").matching {
      include("**/*.kt")
      exclude("**/build/**")
      exclude("**/test/**")
    }.files.toList()
  }
  
  /**
   * 에러 메시지 생성
   */
  private fun generateErrorMessage(project: Project, fileCount: Int, limit: Int): String {
    val splitGuide = when {
      project.name.contains("core") -> generateCoreSplitGuide()
      project.name.startsWith("feature-") -> generateFeatureSplitGuide(project.name)
      else -> generateGenericSplitGuide()
    }
    
    return """
      ❌ Module ${project.name} has $fileCount files (max: $limit)
      
      🔧 Consider splitting into submodules:
      $splitGuide
      
      📖 See docs/module-split-guide.md for details
    """.trimIndent()
  }
  
  /**
   * 경고 메시지 생성
   */
  private fun generateWarningMessage(project: Project, fileCount: Int, limit: Int): String {
    return """
      ⚠️  Module ${project.name} approaching size limit: $fileCount/$limit files
      Consider preparing for module split
    """.trimIndent()
  }
  
  /**
   * Core 모듈 분할 가이드
   */
  private fun generateCoreSplitGuide(): String {
    return """
      - :core-types (ID types, constants)
      - :core-model (domain models)
      - :core-database (repository interfaces)
      - :core-platform (platform contracts)
    """.trimIndent()
  }
  
  /**
   * Feature 모듈 분할 가이드
   */
  private fun generateFeatureSplitGuide(moduleName: String): String {
    return """
      - :$moduleName-domain (use cases, business logic)
      - :$moduleName-data (repositories, data sources)
      - :$moduleName-store (state management)
      - :$moduleName-ui (UI components)
    """.trimIndent()
  }
  
  /**
   * 일반 모듈 분할 가이드
   */
  private fun generateGenericSplitGuide(): String {
    return "- Split by logical boundaries\n- Separate data, domain, and UI layers\n- Follow single responsibility principle"
  }
}