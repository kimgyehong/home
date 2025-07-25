// 🚀 App Module - 메인 오케스트레이션
// KotlinProjectFactory, 최종 조립

plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("MainKt")
}

dependencies {
    // 모든 하위 모듈 의존
    implementation(project(":common"))
    implementation(project(":core"))
    implementation(project(":generators"))
    
    // 코루틴 (필수!)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    
    // CLI 지원
    implementation("com.github.ajalt.clikt:clikt:4.2.1")
    
    // 파일 시스템 작업
    implementation("com.squareup.okio:okio:3.6.0")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
    
    // Fat JAR 생성 (모든 의존성 포함)
    from(configurations.runtimeClasspath.get().map { 
        if (it.isDirectory) it else zipTree(it) 
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}