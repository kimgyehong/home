plugins {
    id("com.android.library")
    kotlin("android")
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    // 플랫폼 중립 모듈 - platform은 common만 의존
    implementation(project(":core:common"))
    
    // Android 전용 의존성
    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.datastore:datastore-preferences:1.1.7")
    
    // SQLDelight Android Driver
    implementation("app.cash.sqldelight:android-driver:${Versions.sqldelight}")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")
    
    // Media Player (BGM용)
    implementation("androidx.media:media:1.7.0")
    
    // 테스트
    testImplementation(kotlin("test"))
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    
    // Detekt formatting plugin
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detekt}")
}