import org.gradle.api.GradleException
import org.gradle.api.Project

/**
 * 린트 설정 및 코드 스타일 검증
 * 파일 크기, 함수 길이, 줄 길이 검증
 */
object LintConfiguration {
  
  private const val MAX_LINE_LENGTH = 100
  private const val MAX_FUNCTION_LENGTH = 60
  private const val MAX_FILE_SIZE = 120
  
  /**
   * 린트 태스크 등록
   */
  fun registerLintTasks(project: Project) {
    project.tasks.register("lintCheck") {
      group = "verification"
      description = "Run lint checks"
      
      doLast {
        validateLineLength(project)
        validateFunctionLength(project)
        validateFileSize(project)
      }
    }
  }
  
  /**
   * 줄 길이 검증
   */
  private fun validateLineLength(project: Project) {
    project.fileTree("src").matching {
      include("**/*.kt")
    }.forEach { file ->
      file.readLines().forEachIndexed { index, line ->
        if (line.length > MAX_LINE_LENGTH) {
          throw GradleException(
            "Line too long in ${file.name}:${index + 1} (${line.length} > $MAX_LINE_LENGTH chars)"
          )
        }
      }
    }
  }
  
  /**
   * 함수 길이 검증
   */
  private fun validateFunctionLength(project: Project) {
    project.fileTree("src").matching {
      include("**/*.kt")
    }.forEach { file ->
      val content = file.readText()
      val functions = extractFunctions(content)
      
      functions.forEach { function ->
        if (function.lineCount > MAX_FUNCTION_LENGTH) {
          throw GradleException(
            "Function too long in ${file.name}: ${function.name} (${function.lineCount} > $MAX_FUNCTION_LENGTH lines)"
          )
        }
      }
    }
  }
  
  /**
   * 파일 크기 검증
   */
  private fun validateFileSize(project: Project) {
    project.fileTree("src").matching {
      include("**/*.kt")
    }.forEach { file ->
      val lineCount = file.readLines().size
      if (lineCount > MAX_FILE_SIZE) {
        throw GradleException(
          "File too large: ${file.name} ($lineCount > $MAX_FILE_SIZE lines)"
        )
      }
    }
  }
  
  /**
   * 함수 추출
   */
  private fun extractFunctions(content: String): List<FunctionInfo> {
    val functions = mutableListOf<FunctionInfo>()
    val lines = content.lines()
    var inFunction = false
    var functionStart = 0
    var braceCount = 0
    var functionName = ""
    
    lines.forEachIndexed { index, line ->
      when {
        line.trim().startsWith("fun ") && !inFunction -> {
          inFunction = true
          functionStart = index
          braceCount = 0
          functionName = line.substringAfter("fun ").substringBefore('(').trim()
        }
        inFunction -> {
          braceCount += line.count { it == '{' } - line.count { it == '}' }
          if (braceCount == 0 && line.contains('}')) {
            functions.add(FunctionInfo(functionName, index - functionStart + 1))
            inFunction = false
          }
        }
      }
    }
    return functions
  }
  
  /**
   * 함수 정보 데이터 클래스
   */
  private data class FunctionInfo(val name: String, val lineCount: Int)
}