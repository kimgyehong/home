// ⚡ Generators Module - 코드 생성기들
// Kotlin 파일, Gradle 스크립트 생성

plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
}

dependencies {
    // 의존성 계층 준수
    implementation(project(":common"))
    implementation(project(":core"))
    
    // KSP 2.0 지원
    implementation("com.google.devtools.ksp:symbol-processing-api:2.0.0-1.0.21")
    
    // 템플릿 엔진 (코드 생성용)
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")
}

ksp {
    arg("useKSP2", "true")
    arg("verbose", "true")
}