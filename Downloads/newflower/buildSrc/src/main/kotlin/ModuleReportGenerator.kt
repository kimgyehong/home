import org.gradle.api.Project

/**
 * 모듈 리포트 생성
 * 모듈별 파일 분포 및 분석 리포트
 */
object ModuleReportGenerator {
  
  /**
   * 모듈 리포트 생성
   */
  fun generateModuleReport(project: Project): ModuleReport {
    val sourceFiles = getKotlinSourceFiles(project)
    val packageDistribution = getPackageDistribution(sourceFiles)
    val moduleLimit = getModuleLimit(project.name)
    
    return ModuleReport(
      moduleName = project.name,
      totalFiles = sourceFiles.size,
      maxFiles = moduleLimit,
      packageDistribution = packageDistribution,
      needsSplit = sourceFiles.size > moduleLimit
    )
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
   * 모듈별 파일 제한 조회
   */
  private fun getModuleLimit(moduleName: String): Int {
    return when {
      moduleName.contains("core") -> 40
      moduleName.startsWith("feature-") -> 50
      moduleName.contains("shared") -> 20
      else -> 75
    }
  }
  
  /**
   * 패키지별 파일 분포 분석
   */
  private fun getPackageDistribution(files: List<java.io.File>): Map<String, Int> {
    return files.groupBy { file ->
      extractPackageName(file)
    }.mapValues { it.value.size }
  }
  
  /**
   * 파일에서 패키지명 추출
   */
  private fun extractPackageName(file: java.io.File): String {
    return try {
      file.readLines()
        .find { it.startsWith("package ") }
        ?.substringAfter("package ")
        ?.substringBefore(";")
        ?.trim()
        ?: "unknown"
    } catch (e: Exception) {
      "unknown"
    }
  }
  
  /**
   * 모듈 리포트 데이터 클래스
   */
  data class ModuleReport(
    val moduleName: String,
    val totalFiles: Int,
    val maxFiles: Int,
    val packageDistribution: Map<String, Int>,
    val needsSplit: Boolean
  ) {
    override fun toString(): String {
      val status = if (needsSplit) "❌ NEEDS SPLIT" else "✅ OK"
      val packageInfo = packageDistribution.entries
        .sortedByDescending { it.value }
        .joinToString("\n") { "  ${it.key}: ${it.value} files" }
      
      return """
        📊 Module Report: $moduleName
        Status: $status
        Files: $totalFiles/$maxFiles
        
        Package distribution:
        $packageInfo
      """.trimIndent()
    }
  }
}