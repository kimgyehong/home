import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.*

/**
 * FlowerDiary 프로젝트 전체 컨벤션
 * 모든 모듈에 일관된 설정 적용
 */
object FlowerDiaryConventions {
  
  const val KOTLIN_JVM_TARGET = "17"
  const val JAVA_VERSION = 17
  const val COMPILE_SDK = 34
  const val MIN_SDK = 24
  const val TARGET_SDK = 34
  
  /**
   * 공통 Kotlin 설정 적용
   */
  fun applyKotlinConventions(project: Project) {
    project.extensions.configure<JavaPluginExtension> {
      toolchain {
        languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(JAVA_VERSION))
      }
    }
    
    project.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
      kotlinOptions {
        jvmTarget = KOTLIN_JVM_TARGET
        freeCompilerArgs += listOf(
          "-Xopt-in=kotlin.RequiresOptIn",
          "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
          "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi"
        )
      }
    }
  }
  
  /**
   * 코드 품질 도구 설정
   */
  fun applyQualityTools(project: Project) {
    QualityToolsConfig.registerValidationTasks(project)
    QualityToolsConfig.configureBuildDependencies(project)
  }
  
  /**
   * 공통 의존성 설정
   */
  fun applyCommonDependencies(project: Project) {
    project.dependencies {
      add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
      add("implementation", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
      add("testImplementation", "org.jetbrains.kotlin:kotlin-test:1.9.10")
      add("testImplementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    }
  }
  
  /**
   * 린트 설정
   */
  fun applyLintConfiguration(project: Project) {
    LintConfiguration.registerLintTasks(project)
  }
}