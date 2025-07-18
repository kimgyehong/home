plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
}

kotlin {
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    cocoapods {
        summary = "iOS app entry point for Flower Diary"
        homepage = "https://github.com/flowerdiary/flower-diary"
        version = "1.0.0"
        ios.deploymentTarget = "14.0"
        
        framework {
            baseName = "FlowerDiaryAppIOS"
            isStatic = true
            
            export(projects.ui.ios)
            export(projects.feature.diary)
            export(projects.platform.ios)
            export(projects.core.common)
            export(projects.core.domain)
            export(projects.core.data)
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.ui.ios)
                implementation(projects.feature.diary)
                implementation(projects.platform.ios)
                implementation(projects.core.common)
                implementation(projects.core.domain)
                implementation(projects.core.data)
                
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