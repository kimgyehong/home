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
            baseName = "FlowerDiaryUIIOS"
            isStatic = true
        }
    }
    
    cocoapods {
        summary = "iOS UI layer for Flower Diary"
        homepage = "https://github.com/flowerdiary/flower-diary"
        version = "1.0.0"
        ios.deploymentTarget = "14.0"
        
        framework {
            baseName = "FlowerDiaryUIIOS"
            export(project(":feature:diary"))
            export(project(":core:common"))
            export(project(":core:domain"))
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":feature:diary"))
                implementation(project(":core:common"))
                implementation(project(":core:domain"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Dependencies.kotlinDateTime}")
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