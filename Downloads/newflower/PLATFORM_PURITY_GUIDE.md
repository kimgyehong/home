# 🎯 꽃 다이어리 플랫폼 순수성 가이드

## 📌 핵심 원칙
**"ui와 platform을 제외한 모든 모듈은 100% 순수 Kotlin"**

## ✅ 플랫폼 중립 모듈 (순수 Kotlin)

| 모듈 | build.gradle.kts | 허용 | 금지 |
|------|------------------|------|------|
| core:common | `kotlin("multiplatform")` | expect 인터페이스, 순수 Kotlin | android.*, UIKit.* |
| core:domain | `kotlin("multiplatform")` | 도메인 로직, 엔티티 | 플랫폼 API |
| core:data | `kotlin("multiplatform")` | SQLDelight, Repository | 플랫폼 특화 드라이버 |
| feature:diary | `kotlin("multiplatform")` | ViewModel, State | UI 컴포넌트 |
| **app:shared** | `kotlin("multiplatform")` | 공통 앱 로직 | 플랫폼 API |

### 순수 모듈 예시:
```kotlin
// ✅ 올바른 예시 - core/domain/build.gradle.kts
plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    iosX64()
    iosArm64()
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
                // 순수 Kotlin 라이브러리만!
            }
        }
    }
}
// ⚠️ android {}, com.android.* 플러그인 없음!
```

## 🚫 플랫폼 특화 모듈

| 모듈 | 플러그인 | 역할 |
|------|----------|------|
| platform:android | `com.android.library` + `kotlin("android")` | actual 구현 |
| platform:ios | `kotlin("multiplatform")` with iOS targets | actual 구현 |
| ui:android | `com.android.library` + `kotlin("android")` | Compose UI |
| ui:ios | `kotlin("multiplatform")` with iOS targets | SwiftUI 브릿지 |
| app:android | `com.android.application` + `kotlin("android")` | 앱 진입점 |
| app:ios | `kotlin("multiplatform")` with iOS targets | 앱 진입점 |

### 플랫폼 모듈 예시:
```kotlin
// ✅ 플랫폼 모듈만 - platform/android/build.gradle.kts
plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.flowerdiary.platform.android"
    compileSdk = 34
}

dependencies {
    implementation(projects.core.common) // expect 인터페이스용
    implementation("app.cash.sqldelight:android-driver:2.0.2") // 플랫폼 특화 OK
}
```

## 🔒 순수성 검증 명령어

### 1. Android 임포트 검사
```bash
grep -R "import android\." core/ feature/ app/shared | grep -v "expect"
# 결과가 없어야 함
```

### 2. iOS 임포트 검사
```bash
grep -R "import UIKit\|import platform\.ios" core/ feature/ app/shared | grep -v "expect"
# 결과가 없어야 함
```

### 3. Android 플러그인 검사
```bash
grep -r "com\.android\.\|kotlin(\"android\")" core/ feature/ app/shared --include="*.gradle.kts"
# 결과가 없어야 함
```

## 📊 최종 정리

### 순수 Kotlin 모듈 (플랫폼 중립):
```
core:common      ✅
core:domain      ✅
core:data        ✅
feature:diary    ✅
app:shared       ✅
```

### 플랫폼 특화 모듈:
```
platform:android ❌ (Android SDK 사용)
platform:ios     ❌ (iOS SDK 사용)
ui:android       ❌ (Compose 사용)
ui:ios           ❌ (SwiftUI 브릿지)
app:android      ❌ (Android 앱)
app:ios          ❌ (iOS 앱)
```

## 🎯 이점

1. **빌드 속도**: 순수 모듈은 Android SDK 없이 빌드 가능
2. **테스트 용이성**: JVM에서 모든 비즈니스 로직 테스트
3. **플랫폼 독립성**: Swift로 전환해도 core/feature 재사용
4. **명확한 경계**: 플랫폼 코드가 어디에 있는지 명확

## ⚠️ 주의사항

1. **expect/actual은 core:common에만**: 다른 모듈에서는 직접 expect 선언 금지
2. **플랫폼 API는 격리**: 필요시 expect/actual로 추상화
3. **CI 자동 검사**: PR마다 순수성 검증 실행

이 가이드를 따르면 **완전무결한 플랫폼 순수성**을 유지할 수 있습니다! 🚀