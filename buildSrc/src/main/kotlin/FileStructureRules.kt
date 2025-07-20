import org.gradle.api.GradleException

/**
 * 파일 구조 검증 규칙
 * 파일 구조, Import 순서, 인덴테이션 검증
 */
object FileStructureRules {
  
  /**
   * 파일당 하나의 최상위 클래스 검증
   */
  fun validateSingleTopLevelClass(file: java.io.File, content: String) {
    val topLevelPattern = Regex("""^(?:class|interface|object|enum class)\s+""", RegexOption.MULTILINE)
    val matches = topLevelPattern.findAll(content).count()
    
    if (matches > 1) {
      throw GradleException("""
        ❌ File ${file.name} contains $matches top-level classes
        Rule: One top-level class per file
      """.trimIndent())
    }
  }
  
  /**
   * Import 순서 검증
   */
  fun validateImportOrder(file: java.io.File, content: String) {
    val lines = content.lines()
    val importLines = lines.filter { it.startsWith("import ") }
    val sortedImports = importLines.sorted()
    
    if (importLines != sortedImports) {
      throw GradleException("""
        ❌ Imports in ${file.name} are not in alphabetical order
        Rule: Imports must be sorted alphabetically
      """.trimIndent())
    }
  }
  
  /**
   * 인덴테이션 검증 (스페이스 2개)
   */
  fun validateIndentation(file: java.io.File, content: String) {
    val lines = content.lines()
    
    lines.forEachIndexed { index, line ->
      if (line.contains('\t')) {
        throw GradleException("""
          ❌ Tab character found in ${file.name}:${index + 1}
          Rule: Use 2 spaces for indentation (no tabs)
        """.trimIndent())
      }
      
      val leadingSpaces = line.takeWhile { it == ' ' }.length
      if (leadingSpaces > 0 && leadingSpaces % 2 != 0) {
        throw GradleException("""
          ❌ Invalid indentation in ${file.name}:${index + 1}
          Rule: Use multiples of 2 spaces for indentation
        """.trimIndent())
      }
    }
  }
}