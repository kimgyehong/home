# 🎯 완전무결한 KMP 아키텍처 구현 가이드

## 📌 핵심 원칙: **플랫폼 의존성 최소화**

```
플랫폼 SDK 사용 (오직 이것만!):
├── ui:android       ← Compose, Activity, Application
├── platform:android ← Driver, 권한
└── platform:ios     ← iOS SDK

순수 Kotlin (SDK import 0):
├── core:*          ← 비즈니스 로직
├── feature:*       ← ViewModel, State  
└── app:shared      ← 공통 초기화 로직

최소 런처 (얇은 래퍼):
├── app:android     ← 진입점만
└── app:ios         ← 진입점만
```

## 🔧 구체적인 구현 방법

### 1. **app:android (최소 런처)**

```kotlin
// app/android/build.gradle.kts
plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(projects.app.shared)    // 공통 로직
    implementation(projects.ui.android)    // UI 모듈
    // 최소한의 Android 의존성만
}
```

```kotlin
// app/android/src/main/kotlin/Launcher.kt
// 단 하나의 파일만! 진입점 역할
class LauncherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ui:android의 MainActivity로 즉시 전환
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
```

### 2. **ui:android (모든 Android 코드 집중)**

```kotlin
// ui:android로 이동할 파일들:
ui/android/
├── MainActivity.kt            ← app:android에서 이동
├── FlowerDiaryApplication.kt  ← app:android에서 이동
├── di/                        ← app:android에서 이동
│   ├── AppModule.kt
│   ├── DataModule.kt
│   ├── DomainModule.kt
│   ├── FeatureModule.kt
│   └── PlatformModule.kt
├── screen/                    ← 이미 있음
├── component/                 ← 이미 있음
└── AndroidManifest.xml        ← 여기로 이동
```

### 3. **app:shared (순수 Kotlin 공통 로직)**

```kotlin
// app/shared/build.gradle.kts
plugins {
    kotlin("multiplatform")  // 순수 KMP
}

kotlin {
    jvm()
    iosX64()
    iosArm64()
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.feature.diary)
                api(projects.core.common)
                api(projects.core.domain)
                api(projects.core.data)
                
                // DI Core만 (Android 의존성 없음)
                api("io.insert-koin:koin-core:${Versions.koin}")
            }
        }
    }
}
```

```kotlin
// app/shared/src/commonMain/kotlin/AppInitializer.kt
class AppInitializer {
    fun initializeCommonModules(): List<Module> {
        return listOf(
            // 플랫폼 중립적인 모듈만
            domainModule,
            dataModule,
            featureModule
        )
    }
}
```

## 📊 최종 구조

```
                 ┌────────────┐
                 │ ui:android │  ← 모든 Android 코드
                 └────▲───▲───┘     (Activity, Application, DI)
                      │   │
┌────────────┐   ┌────┴───┴────┐
│platform:ios│   │platform:android│  ← Driver, 권한
└────▲───▲───┘   └────▲───▲─────┘
     │   │            │   │         
     │   └────────────┘   │         
     │                    │
┌────┴──────────────┬─────┴────────┐
│    app:shared     │   feature:*   │  ← 순수 Kotlin
└────────┬──────────┴──────────────┘
         │
   ┌─────▼──────┐
   │   core:*   │  ← 순수 Kotlin
   └────────────┘
         
   ┌─────────────┐  ┌─────────────┐
   │app:android  │  │  app:ios    │  ← 최소 런처만
   └─────────────┘  └─────────────┘
```

## ✅ 검증 체크리스트

### 1. 플랫폼 SDK 위치 확인
```bash
# Android SDK는 3곳에만
find . -name "*.kt" -exec grep -l "import android\." {} \; | grep -E "(ui|platform|app)" | sort -u
# 결과: ui/android/*, platform/android/*, app/android/Launcher.kt만
```

### 2. 순수 모듈 검증
```bash
# core, feature, app:shared에 SDK 없음
grep -r "import android\." core/ feature/ app/shared/
# 결과: 없어야 함
```

### 3. app:android 최소화 확인
```bash
# app:android는 런처 하나만
ls app/android/src/main/kotlin/
# 결과: Launcher.kt 하나만
```

## 🎯 이점

1. **완벽한 플랫폼 격리**: SDK 의존성이 명확히 분리됨
2. **빠른 빌드**: 대부분의 코드가 순수 Kotlin
3. **테스트 용이**: 비즈니스 로직을 JVM에서 테스트
4. **유지보수성**: 플랫폼 코드 위치가 명확

## 🚀 마이그레이션 단계

1. app:android의 모든 코드를 ui:android로 이동
2. app:android에는 최소 런처만 남기기
3. AndroidManifest.xml도 ui:android로 이동
4. app:shared가 순수 Kotlin인지 확인
5. CI에 순수성 검사 추가

이제 진정한 **"ui/platform 제외 나머지 순수"**가 달성됩니다! 🎉