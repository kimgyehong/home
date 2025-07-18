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
            baseName = "common"
            isStatic = true
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                // 순수 Kotlin 라이브러리만 사용 - 플랫폼 중립성 보장
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
                api("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Dependencies.kotlinDateTime}")
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

