// 🏗️ Common Module - 기초 타입들
// Result 타입, 공통 인터페이스

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    // 직렬화
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    
    // 로깅 (선택사항)
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
}

kotlin {
    explicitApi()  // 명시적 API 모드
}