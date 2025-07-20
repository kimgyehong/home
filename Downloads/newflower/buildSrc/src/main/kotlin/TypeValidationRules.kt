import org.gradle.api.GradleException

/**
 * 타입 시스템 검증 규칙
 * 도메인 타입, 성능 타입, Store 불변성 검증
 */
object TypeValidationRules {
  
  /**
   * 도메인 타입 검증 (data class + validation)
   */
  fun validateDomainTypes(file: java.io.File, content: String, packageName: String) {
    if (!packageName.contains(".model.") && !packageName.contains(".types.")) return
    
    val isIdType = content.contains(": EntityId") || packageName.contains(".types.")
    val isModelType = packageName.contains(".model.")
    
    when {
      isIdType && content.contains("@JvmInline") -> {
        throw GradleException("""
          ❌ ID types in ${file.name} must use:
          - data class (not value class)
          - sealed interface EntityId
          
          📖 See architecture-decisions.md #domain-types
        """.trimIndent())
      }
      
      isModelType && !content.contains("data class") -> {
        throw GradleException("""
          ❌ Domain model in ${file.name} must use:
          - data class (not class/object)
          - init { require(...) } validation
          
          📖 See architecture-decisions.md #domain-types
        """.trimIndent())
      }
      
      isModelType && !content.contains("require(") -> {
        throw GradleException("""
          ❌ Domain model in ${file.name} must include validation:
          - init { require(...) } blocks
          
          📖 See architecture-decisions.md #domain-types
        """.trimIndent())
      }
    }
  }
  
  /**
   * 성능 최적화 타입 검증 (typealias)
   */
  fun validatePerformanceTypes(file: java.io.File, content: String, packageName: String) {
    val performanceKeywords = listOf("Search", "Filter", "Sort", "Query", "Cache")
    val isPerformanceType = performanceKeywords.any { content.contains(it) }
    
    if (isPerformanceType && content.contains("data class") && 
        performanceKeywords.any { content.contains("${it}Query") || content.contains("${it}Key") }) {
      
      throw GradleException("""
        ❌ Performance types in ${file.name} should use:
        - typealias (not data class)
        - For frequently created types like SearchQuery, FilterKey
        
        📖 See architecture-decisions.md #performance-types
      """.trimIndent())
    }
  }
  
  /**
   * Store 불변성 검증
   */
  fun validateStoreImmutability(file: java.io.File, content: String, packageName: String) {
    if (!packageName.contains(".store.") && !packageName.contains("State")) return
    
    val hasMutableCollections = content.contains("mutableListOf") || 
                               content.contains("mutableMapOf") || 
                               content.contains("mutableSetOf")
    
    val isInternalCache = content.contains("private val") && content.contains("Cache")
    val hasMutexProtection = content.contains("Mutex") || content.contains("withLock")
    
    if (hasMutableCollections && !isInternalCache) {
      throw GradleException("""
        ❌ Store in ${file.name} must use:
        - ImmutableList, ImmutableMap (not mutable collections)
        - Core state must be completely immutable
        
        📖 See architecture-decisions.md #store-immutability
      """.trimIndent())
    }
    
    if (isInternalCache && hasMutableCollections && !hasMutexProtection) {
      throw GradleException("""
        ❌ Mutable cache in ${file.name} must use:
        - Mutex protection for thread safety
        - private val cacheMutex = Mutex()
        
        📖 See architecture-decisions.md #store-immutability
      """.trimIndent())
    }
  }
}