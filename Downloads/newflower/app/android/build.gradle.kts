plugins {
    id("com.android.application")
    kotlin("android")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    
    kotlinOptions {
        jvmTarget = "21"
    }
    
    // 명시적으로 소스셋 구성
    sourceSets {
        getByName("main") {
            res.srcDirs("src/main/res")
            assets.srcDirs("src/main/assets")
        }
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
        kotlinCompilerExtensionVersion = "1.5.14"
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
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Dependencies.lifecycle}")
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
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    
    // Detekt formatting plugin
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
}