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
                // 플랫폼 중립 데이터 계층 - 순수 코틀린만 사용
                api(project(":core:common"))
                api(project(":core:domain"))
                
                // 순수 코틀린 외부 의존성 - 플랫폼 중립적
                implementation("app.cash.sqldelight:coroutines-extensions:${Versions.sqldelight}")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}")
            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        
        val jvmMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:sqlite-driver:${Versions.sqldelight}")
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
                implementation("app.cash.sqldelight:native-driver:${Versions.sqldelight}")
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