// app 모듈의 플랫폼 중립적인 공통 로직
plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "appShared"
            isStatic = true
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                // 플랫폼 중립 모듈들
                api(project(":feature:diary"))
                api(project(":core:common"))
                api(project(":core:domain"))
                api(project(":core:data"))
                
                // DI - Koin Core
                api("io.insert-koin:koin-core:${Versions.koin}")
                
                // 코루틴
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}