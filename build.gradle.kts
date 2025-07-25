// 🚀 Command Tower v14 - K2 컴파일러 + KSP 2.0 설정!
// 2025년 최신 최적화 설정

plugins {
    kotlin("jvm") version "2.0.0" apply false
    kotlin("plugin.serialization") version "2.0.0" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false
}

// 전체 서브프로젝트 공통 설정
subprojects {
    repositories {
        mavenCentral()
        google()
    }
    
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

// Gradle 속성
tasks.wrapper {
    gradleVersion = "8.5"
    distributionType = Wrapper.DistributionType.BIN
}