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
            
            export(project(":ui:ios"))
            export(project(":feature:diary"))
            export(project(":platform:ios"))
            export(project(":core:common"))
            export(project(":core:domain"))
            export(project(":core:data"))
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":ui:ios"))
                implementation(project(":feature:diary"))
                implementation(project(":platform:ios"))
                implementation(project(":core:common"))
                implementation(project(":core:domain"))
                implementation(project(":core:data"))
                
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