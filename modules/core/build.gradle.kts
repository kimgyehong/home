// 🔥 Core Module - DAG 워크플로우 엔진
// topological sort, 병렬 실행 엔진

plugins {
    kotlin("jvm")
}

dependencies {
    // common 모듈 의존
    implementation(project(":common"))
    
    // 코루틴 (DAG 병렬 처리용)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    
    // 컬렉션 유틸리티  
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
}