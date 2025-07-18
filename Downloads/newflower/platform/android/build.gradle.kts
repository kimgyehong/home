plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.flowerdiary.platform.android"
    compileSdk = Versions.Android.compileSdk
    
    defaultConfig {
        minSdk = Versions.Android.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // 플랫폼 중립 모듈 - platform은 common만 의존
    implementation(project(":core:common"))
    
    // Android 전용 의존성
    implementation("androidx.core:core-ktx:${Versions.Dependencies.coreKtx}")
    implementation("androidx.datastore:datastore-preferences:${Versions.Dependencies.datastorePreferences}")
    
    // SQLDelight Android Driver
    implementation("app.cash.sqldelight:android-driver:${Versions.sqldelight}")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")
    
    // Media Player (BGM용)
    implementation("androidx.media:media:${Versions.Dependencies.androidxMedia}")
    
    // 테스트
    testImplementation(kotlin("test"))
    androidTestImplementation("androidx.test.ext:junit:${Versions.Dependencies.junit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.Dependencies.espresso}")
    
    // Detekt formatting plugin - 순환 의존성 방지를 위해 제거
    // detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
}