import org.gradle.api.Project

/**
 * 아키텍처 규칙 자동 강제
 * 메인 진입점 및 검증 오케스트레이션
 */
object ArchitectureRules {
  
  /**
   * 타입 시스템 일관성 검증
   */
  fun validateTypeConsistency(project: Project) {
    val sourceFiles = project.fileTree("src").matching {
      include("**/*.kt")
    }.files
    
    sourceFiles.forEach { file ->
      val content = file.readText()
      val packageName = extractPackage(content)
      
      TypeValidationRules.validateDomainTypes(file, content, packageName)
      TypeValidationRules.validatePerformanceTypes(file, content, packageName)
      TypeValidationRules.validateStoreImmutability(file, content, packageName)
    }
  }
  
  /**
   * 네이밍 컨벤션 검증
   */
  fun validateNamingConventions(project: Project) {
    val sourceFiles = project.fileTree("src").matching {
      include("**/*.kt")
    }.files
    
    sourceFiles.forEach { file ->
      val content = file.readText()
      NamingValidationRules.validateClassNaming(file, content)
      NamingValidationRules.validateFunctionNaming(file, content)
      NamingValidationRules.validateConstantNaming(file, content)
    }
  }
  
  /**
   * 파일 구조 검증
   */
  fun validateFileStructure(project: Project) {
    val sourceFiles = project.fileTree("src").matching {
      include("**/*.kt")
    }.files
    
    sourceFiles.forEach { file ->
      val content = file.readText()
      FileStructureRules.validateSingleTopLevelClass(file, content)
      FileStructureRules.validateImportOrder(file, content)
      FileStructureRules.validateIndentation(file, content)
    }
  }
  
  /**
   * 패키지명 추출
   */
  internal fun extractPackage(content: String): String {
    return content.lines()
      .find { it.startsWith("package ") }
      ?.substringAfter("package ")
      ?.substringBefore(";")
      ?.trim()
      ?: ""
  }
}