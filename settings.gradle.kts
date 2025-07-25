// 🏗️ Command Tower v14 - 탄탄한 기초부터!
// Multi-module Kotlin project with K2 + KSP 2.0

rootProject.name = "ct-v14"

// 모듈 구조 정의
include(":common")     // 공통 타입, Result 체계
include(":core")       // DAG 워크플로우 엔진
include(":generators") // 병렬 코드 생성기들  
include(":app")        // 메인 오케스트레이션
include(":generated")  // 쏘둥이가 생성한 프로젝트!

// 프로젝트 레이아웃
project(":common").projectDir = file("modules/common")
project(":core").projectDir = file("modules/core") 
project(":generators").projectDir = file("modules/generators")
project(":app").projectDir = file("modules/app")
project(":generated").projectDir = file("modules/app/generated")