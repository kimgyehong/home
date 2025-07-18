plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.kotlin
    id("app.cash.sqldelight")
}

kotlin {
    jvm() // ✅ 순수 JVM 타겟 - Android SDK 의존성 제거
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "data"
            isStatic = true
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                // 플랫폼 중립 데이터 계층
                api(project(":core:common"))
                // ❌ core/domain 의존성 제거 - Clean Architecture 원칙 준수
                
                // SQLDelight
                implementation("app.cash.sqldelight:runtime:${Versions.sqldelight}")
                implementation("app.cash.sqldelight:coroutines-extensions:${Versions.sqldelight}")
                
                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
                
                // Serialization for JSON parsing
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Dependencies.kotlinxSerializationJson}")
            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        
        val jvmMain by getting {
            dependencies {
                // JVM 드라이버는 platform 모듈에서 제공
            }
        }
        
        val jvmTest by getting
        
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            
            dependencies {
                // native-driver는 platform/ios 모듈에서 제공
            }
        }
    }
}

// Android 설정은 UI/Platform 모듈에서만 사용
// Core 모듈은 순수 KMP로만 구성

sqldelight {
    databases {
        create("FlowerDiaryDatabase") {
            packageName.set("com.flowerdiary.data")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            verifyMigrations.set(true)
        }
    }
}