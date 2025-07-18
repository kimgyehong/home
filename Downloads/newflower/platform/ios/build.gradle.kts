plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "FlowerDiaryPlatformIOS"
            isStatic = true
        }
    }
    
    cocoapods {
        summary = "iOS platform implementations for Flower Diary"
        homepage = "https://github.com/flowerdiary/flower-diary"
        version = "1.0.0"
        ios.deploymentTarget = "14.0"
        
        framework {
            baseName = "FlowerDiaryPlatformIOS"
            export(project(":core:common"))
        }
        
        pod("SQLite.swift") {
            version = "~> 0.14.1"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":core:common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Dependencies.kotlinDateTime}")
                implementation("app.cash.sqldelight:native-driver:${Versions.sqldelight}")
            }
        }
        
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