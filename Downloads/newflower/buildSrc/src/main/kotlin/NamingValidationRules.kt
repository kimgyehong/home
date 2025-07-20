import org.gradle.api.GradleException

/**
 * 네이밍 컨벤션 검증 규칙
 * 클래스, 함수, 상수 네이밍 규칙 강제
 */
object NamingValidationRules {
  
  /**
   * 클래스/인터페이스 네이밍 검증
   */
  fun validateClassNaming(file: java.io.File, content: String) {
    val classPattern = Regex("""(?:class|interface|object|enum class)\s+([A-Za-z][A-Za-z0-9]*)""")
    val matches = classPattern.findAll(content)
    
    matches.forEach { match ->
      val className = match.groupValues[1]
      if (!className.matches(Regex("^[A-Z][a-zA-Z0-9]*$"))) {
        throw GradleException("""
          ❌ Class name '$className' in ${file.name} must use PascalCase
          Example: MyClass, UserRepository, FlowerDiaryApp
        """.trimIndent())
      }
    }
  }
  
  /**
   * 함수 네이밍 검증
   */
  fun validateFunctionNaming(file: java.io.File, content: String) {
    val functionPattern = Regex("""fun\s+([a-zA-Z][a-zA-Z0-9]*)\s*\(""")
    val matches = functionPattern.findAll(content)
    
    matches.forEach { match ->
      val functionName = match.groupValues[1]
      if (!functionName.matches(Regex("^[a-z][a-zA-Z0-9]*$"))) {
        throw GradleException("""
          ❌ Function name '$functionName' in ${file.name} must use camelCase
          Example: getUserById, calculateTotal, processData
        """.trimIndent())
      }
    }
  }
  
  /**
   * 상수 네이밍 검증
   */
  fun validateConstantNaming(file: java.io.File, content: String) {
    val constantPattern = Regex("""const val\s+([A-Za-z][A-Za-z0-9_]*)\s*=""")
    val matches = constantPattern.findAll(content)
    
    matches.forEach { match ->
      val constantName = match.groupValues[1]
      if (!constantName.matches(Regex("^[A-Z][A-Z0-9_]*$"))) {
        throw GradleException("""
          ❌ Constant name '$constantName' in ${file.name} must use UPPER_SNAKE_CASE
          Example: MAX_VALUE, DEFAULT_TIMEOUT, API_BASE_URL
        """.trimIndent())
      }
    }
  }
}