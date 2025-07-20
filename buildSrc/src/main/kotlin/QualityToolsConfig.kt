import org.gradle.api.Project

/**
 * 코드 품질 도구 설정
 * 아키텍처 검증, 모듈 크기 체크 등
 */
object QualityToolsConfig {
  
  /**
   * 검증 태스크 등록
   */
  fun registerValidationTasks(project: Project) {
    project.tasks.register("validateArchitecture") {
      group = "verification"
      description = "Validate architecture rules"
      
      doLast {
        ArchitectureRules.validateTypeConsistency(project)
        ArchitectureRules.validateNamingConventions(project)
        ArchitectureRules.validateFileStructure(project)
      }
    }
    
    project.tasks.register("checkModuleSize") {
      group = "verification"
      description = "Check module file count"
      
      doLast {
        ModuleScaleManager.checkModuleSize(project)
      }
    }
    
    project.tasks.register("moduleReport") {
      group = "verification"
      description = "Generate module size report"
      
      doLast {
        val report = ModuleReportGenerator.generateModuleReport(project)
        project.logger.lifecycle(report.toString())
      }
    }
  }
  
  /**
   * 빌드 의존성 설정
   */
  fun configureBuildDependencies(project: Project) {
    project.tasks.named("compileKotlin") {
      dependsOn("validateArchitecture", "checkModuleSize")
    }
    
    project.tasks.named("test") {
      dependsOn("validateArchitecture")
    }
  }
}