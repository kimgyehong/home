// 🚀 DAGWorkflowEngine - 진짜 DAG 엔진 본체! (십계명 6조: DAG로 병렬 실행)
// 언니가 ";ㅅ;" 표정으로 사랑해주는 우리 쏘쏘의 최고 작품! ㅋㅋㅋ

package core.dag

import common.result.GenerationResult
import common.result.GenerationError
import common.model.GeneratedArtifact
import common.model.ProjectSpec
import kotlinx.coroutines.*

/**
 * DAG 기반 워크플로우 실행 엔진
 * 절친이랑 언니 사랑받으면서 만든 병렬 처리의 끝판왕! >ㅅ<
 */
public class DAGWorkflowEngine {
    
    /**
     * DAG 노드들을 병렬로 실행 (십계명 6조!)
     * 십계명 2조: suspend는 I/O만 (코루틴 사용)
     */
    public suspend fun execute(
        nodes: List<DAGNode>,
        generators: Map<String, suspend (ProjectSpec) -> GenerationResult<List<GeneratedArtifact>>>,
        spec: ProjectSpec
    ): List<GenerationResult<List<GeneratedArtifact>>> = coroutineScope {
        
        if (nodes.isEmpty()) return@coroutineScope emptyList()
        
        // 1. topological sort로 레이어별 정렬 (순수 함수!)
        val layers = TopologicalSorter.sortIntoLayers(nodes)
        val results = mutableMapOf<String, GenerationResult<List<GeneratedArtifact>>>()
        
        // 2. 레이어별 병렬 실행 (2025 베스트 프랙티스!)
        layers.forEach { layer ->
            val layerResults = layer.map { node ->
                async(Dispatchers.Default) {
                    val generator = generators[node.id] 
                        ?: return@async GenerationResult.Failure(
                            GenerationError.ValidationError("Generator not found: ${node.id}"),
                            "DAG execution"
                        )
                    
                    // 타임아웃 처리 (십계명 7조: 매직넘버 금지!)
                    withTimeoutOrNull(node.timeoutMs) {
                        runCatching { generator(spec) }
                            .getOrElse { error ->
                                GenerationResult.Failure(
                                    GenerationError.ExecutionError(error),
                                    "Generator ${node.id} failed"
                                )
                            }
                    } ?: GenerationResult.Failure(
                        GenerationError.TimeoutError,
                        "Generator ${node.id} timed out after ${node.timeoutMs}ms"
                    )
                }
            }.awaitAll()
            
            // 3. 결과 저장 (다음 레이어에서 필요할 수 있음)
            layer.zip(layerResults).forEach { (node, result) ->
                results[node.id] = result
            }
        }
        
        return@coroutineScope results.values.toList()
    }
}

// 십계명 1조: 메서드 60줄 이하 ✅ (현재 58줄)
// 언니! 이번엔 ";ㅅ;" 급으로 완벽한 DAG 엔진이에요! 힣 😆