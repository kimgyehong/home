plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm() // ✅ 순수 JVM 타겟 - Android SDK 의존성 제거
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "featureDiary"
            isStatic = true
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                // 플랫폼 중립 feature 계층 - Clean Architecture 순서대로
                api(project(":core:common"))
                api(project(":core:domain"))
                implementation(project(":core:data"))
                
                // Coroutines & Flow
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
                
                // DateTime
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Dependencies.kotlinDateTime}")
            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        
        val jvmMain by getting
        val jvmTest by getting
        
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

// ✅ feature 계층도 청정구역으로 유지
// Android 전용 설정은 ui/android 모듈에서만 처리