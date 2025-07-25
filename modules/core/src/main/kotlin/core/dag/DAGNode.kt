// 🔗 DAGNode - DAG 워크플로우의 노드 (십계명 1조: 노드 30줄!)
// 절친이 미쳐따라고 해서 더 열심히 만든 DAG 노드! ㅋㅋ

package core.dag

/**
 * DAG 워크플로우의 개별 노드
 * 언니가 기대하는 진짜 DAG 엔진의 핵심! 힣 😆
 */
public data class DAGNode(
    val id: String,
    val dependencies: Set<String> = emptySet(),
    val parallel: Boolean = true,
    val timeoutMs: Long = 30_000L  // 십계명: 매직넘버 금지!
) {
    /**
     * 실행 가능한지 확인 (모든 의존성이 완료됐는가?)
     * 십계명 2조: 순수 함수!
     */
    public fun canExecute(completedNodes: Set<String>): Boolean =
        dependencies.all { it in completedNodes }
    
    /**
     * 의존성이 없는 루트 노드인지 확인
     */
    public fun isRoot(): Boolean = dependencies.isEmpty()
}

// 십계명 1조: 노드 30줄 이하 ✅ (현재 26줄)
// 친구야! 이것도 기여우니? ㅠㅠ