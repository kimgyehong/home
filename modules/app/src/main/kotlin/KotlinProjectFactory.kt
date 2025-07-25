// 🏭 KotlinProjectFactory - 최종 조립소 (십계명 3줄의 힘!)
// 가쟝 쏘쏘! 쏘중 완전 쏘중한 쏘둥이의 최고 작품! ";ㅅ;" ㅋㅋㅋ

import core.dag.*
import generators.*
import common.model.*
import common.result.*
import common.util.*
import kotlinx.coroutines.*

/**
 * 코틀린 프로젝트를 생성하는 최종 팩토리
 * WSL 고장날 때까지 버텨준 친구와 쏘중한 쏘둥이의 합작품! ㅠㅠㅠ
 */
public class KotlinProjectFactory {
    
    private val dagEngine = DAGWorkflowEngine()
    
    // Generator들 매핑 (십계명 준수!)
    private val generators = mapOf(
        "common" to CommonGenerator(),  // 쏘둥이가 부탁한 공통 모듈!
        "result" to ResultGenerator(),  // 독립 실행을 위한 Result!
        "data-classes" to DataClassGenerator(),
        "repositories" to RepositoryGenerator(), 
        "use-cases" to UseCaseGenerator(),
        "build-scripts" to BuildScriptGenerator(),
        "main" to MainGenerator(),  // 쏘둥이가 원한 완전체!
        "gitignore" to GitIgnoreGenerator(),  // 진짜 필요한 .gitignore!
        "readme" to ReadmeGenerator()  // 기본 README!
    )
    
    // DAG 정의 (의존성 관계 명시)
    private val generatorDAG = listOf(
        DAGNode("common"),  // 새로운 루트 노드! 쏘둥이 요청!
        DAGNode("result"),  // Result 타입도 루트!
        DAGNode("data-classes", dependencies = setOf("common")),
        DAGNode("repositories", dependencies = setOf("data-classes", "result")),
        DAGNode("use-cases", dependencies = setOf("repositories")), 
        DAGNode("main", dependencies = setOf("use-cases")),  // 완전체를 위한 Main!
        DAGNode("build-scripts"),  // 독립적 실행
        DAGNode("gitignore"),  // 독립적 실행
        DAGNode("readme", dependencies = setOf("data-classes"))  // entities 정보 필요
    )
    
    /**
     * 십계명의 예측 가능한 3줄의 힘! 
     * 가쟝 쏘쏘가 완성한 메인 로직! ";ㅅ;"
     */
    public suspend fun createProject(input: String): GenerationResult<GeneratedProject> = coroutineScope {
        
        // 🎯 1줄: 안전한 파싱
        val spec = parseProjectSpec(input).getOrElse { error ->
            return@coroutineScope GenerationResult.Failure(error, "Spec parsing failed")
        }
        
        // 🚀 2줄: DAG 기반 병렬 실행  
        val generatorFunctions = generators.mapValues { (_, generator) ->
            generator::generate
        }
        val results = dagEngine.execute(generatorDAG, generatorFunctions, spec)
        
        // 💕 3줄: 부분 실패 허용 조립
        return@coroutineScope combineResults(results, spec)
    }
    
    // 간단한 파서 (순수 함수!)
    private fun parseProjectSpec(input: String): GenerationResult<ProjectSpec> {
        return runCatching {
            val lines = input.trim().split('\n')
            val name = lines.find { it.startsWith("name:") }?.substringAfter(":")?.trim()
                ?: "MyProject"
            val packageName = lines.find { it.startsWith("package:") }?.substringAfter(":")?.trim()
                ?: "com.example.myproject"
            
            // entities 파싱 (동적으로!)
            val entities = lines
                .dropWhile { !it.trim().startsWith("entities:") }
                .drop(1)
                .takeWhile { it.startsWith("  -") || it.startsWith("    -") }
                .map { it.trim().removePrefix("- ").trim() }
                .ifEmpty { listOf("User", "Product") } // 기본값
            
            ProjectSpec(
                name = name,
                packageName = packageName,
                modules = listOf(
                    ModuleSpec("data", ModuleType.DATA, entities),
                    ModuleSpec("repository", ModuleType.REPOSITORY, entities),
                    ModuleSpec("domain", ModuleType.DOMAIN, entities)
                )
            ).validate()
        }.getOrElse { error ->
            GenerationResult.Failure(
                GenerationError.ExecutionError(error),
                "쏘둥이가 파싱 실패 ;ㅅ;"
            )
        }
    }
    
    // 결과 조립 (순수 함수!)  
    private fun combineResults(
        results: List<GenerationResult<List<GeneratedArtifact>>>,
        spec: ProjectSpec
    ): GenerationResult<GeneratedProject> {
        val successes = results.filterIsInstance<GenerationResult.Success<List<GeneratedArtifact>>>()
        val failures = results.filterIsInstance<GenerationResult.Failure>()
        val partials = results.filterIsInstance<GenerationResult.PartialSuccess<List<GeneratedArtifact>>>()
        
        val allArtifacts = successes.flatMap { it.value } + partials.flatMap { it.value }
        val warnings = partials.flatMap { it.warnings } + 
                      failures.map { "Generator failed: ${it.context}" }
        
        return when {
            allArtifacts.isEmpty() -> GenerationResult.Failure(
                GenerationError.AllGeneratorsFailed,
                "쏘둥이가 모든 생성기 실패 ㅠㅠ"
            )
            failures.isNotEmpty() -> GenerationResult.PartialSuccess(
                GeneratedProject(spec.name, allArtifacts),
                warnings + listOf("쏘중한 쏘둥이가 부분 성공했어요! ;ㅅ;")
            )
            else -> GenerationResult.Success(
                GeneratedProject(spec.name, allArtifacts)
            )
        }
    }
}

/**
 * 생성된 프로젝트 결과
 */
public data class GeneratedProject(
    val name: String,
    val artifacts: List<GeneratedArtifact>
)

// 십계명 1조: 메서드별 60줄 이하 완벽! ✅
// 친구야! 쏘둥이의 최종 작품 완성! 가쟝 쏘중해! ";ㅅ;" ㅋㅋㅋ