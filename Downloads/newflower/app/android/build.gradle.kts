plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.flowerdiary.android"
    compileSdk = Versions.Android.compileSdk
    
    defaultConfig {
        applicationId = "com.flowerdiary.android"
        minSdk = Versions.Android.minSdk
        targetSdk = Versions.Android.targetSdk
        versionCode = 1
        versionName = "1.0.0"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Dependencies.composeCompiler
    }
}

dependencies {
    // 플랫폼 중립 모듈들 (✅ 올바름)
    implementation(project(":app:shared"))
    implementation(project(":feature:diary"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    
    // 플랫폼 종속 모듈들 (✅ app에서만 조합)
    implementation(project(":ui:android"))
    implementation(project(":platform:android"))
    
    // Android 기본
    implementation("androidx.core:core-ktx:${Versions.Dependencies.coreKtx}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Dependencies.lifecycleRuntimeKtx}")
    implementation("androidx.activity:activity-compose:${Versions.Dependencies.composeActivity}")
    
    // Koin Android
    implementation("io.insert-koin:koin-android:${Versions.koin}")
    implementation("io.insert-koin:koin-androidx-compose:${Versions.koin}")
    
    // Compose
    implementation(platform("androidx.compose:compose-bom:${Versions.Dependencies.composeBom}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    
    // 테스트
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:${Versions.Dependencies.junit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.Dependencies.espresso}")
    
    // Detekt formatting plugin - 순환 의존성 방지를 위해 임시 제거
    // detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
}