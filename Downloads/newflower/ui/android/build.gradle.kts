plugins {
    id("com.android.library") // UI 모듈은 library여야 함
    id("org.jetbrains.kotlin.android")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Dependencies.composeCompiler
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
    implementation("androidx.navigation:navigation-compose:${Versions.Dependencies.navigationCompose}")
    // Android Core
    implementation("androidx.core:core-ktx:${Versions.Dependencies.coreKtx}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Dependencies.lifecycleRuntimeKtx}")
    
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Dependencies.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${Versions.Dependencies.lifecycle}")
    
    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:${Versions.Dependencies.accompanistSystemUi}")
    
    // Media3 (비디오 재생)
    implementation("androidx.media3:media3-exoplayer:${Versions.Dependencies.media3}")
    implementation("androidx.media3:media3-ui:${Versions.Dependencies.media3}")
    
    // Koin
    implementation("io.insert-koin:koin-android:${Versions.koin}")
    implementation("io.insert-koin:koin-androidx-compose:${Versions.koin}")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")
    
    // Debug
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    
    // Detekt formatting plugin - 순환 의존성 방지를 위해 제거
    // detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
}