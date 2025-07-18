plugins {
    id("com.android.library") // UI 모듈은 library여야 함
    kotlin("android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.flowerdiary.ui.android"
    compileSdk = Versions.Android.compileSdk
    
    defaultConfig {
        // library 모듈에는 applicationId가 없음
        minSdk = Versions.Android.minSdk
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    
    kotlinOptions {
        jvmTarget = "21"
    }
    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // 플랫폼 중립적인 app 공통 모듈
    implementation(project(":app:shared"))
    
    // Feature 모듈 (플랫폼 중립적)
    implementation(project(":feature:diary"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    
    // Platform 모듈
    implementation(project(":platform:android"))
    
    // Android Compose
    val composeBom = platform("androidx.compose:compose-bom:${Versions.Dependencies.composeBom}")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:${Versions.Dependencies.composeActivity}")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    
    // Android Core
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Dependencies.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${Versions.Dependencies.lifecycle}")
    
    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
    
    // Media3 (비디오 재생)
    implementation("androidx.media3:media3-exoplayer:1.2.0")
    implementation("androidx.media3:media3-ui:1.2.0")
    
    // Koin
    implementation("io.insert-koin:koin-android:${Versions.koin}")
    implementation("io.insert-koin:koin-androidx-compose:${Versions.koin}")
    
    // SQLDelight Android Driver - 이것은 platform:android에서 제공해야 함
    // implementation("app.cash.sqldelight:android-driver:${Versions.sqldelight}")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")
    
    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    
    // Detekt formatting plugin
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
}