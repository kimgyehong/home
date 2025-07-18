# 🎯 올바른 3-모듈 플랫폼 아키텍처

## 핵심 원칙: **오직 3개 모듈만 플랫폼 SDK 사용**

```
                 ┌────────────┐
                 │ ui-android │  ← Android SDK (Compose + Activity + Application)
                 └────▲───▲───┘
                      │   │
┌────────────┐   ┌────┴───┴────┐
│platform-ios│   │platform-android│  ← 플랫폼 SDK (Driver, 권한 등)
└────▲───▲───┘   └────▲───▲─────┘
     │   │            │   │         
     │   └────────────┘   │         ➊ 오직 이 3개만 SDK 사용!
     │                    │
┌────┴──────────────┬─────┴────────┐
│    app:shared     │   feature-*   │  ← KMP only (순수 Kotlin)
└────────┬──────────┴──────────────┘
         │
   ┌─────▼──────┐
   │   core-*   │  ← KMP only (순수 Kotlin)
   └────────────┘
```

## 📁 파일 재배치 필요

### 현재 (❌ 잘못됨):
```
app/android/
├── MainActivity.kt              ❌ Android SDK 사용
├── FlowerDiaryApplication.kt    ❌ Android SDK 사용
└── di/*.kt                      ❌ Android 의존성
```

### 올바른 구조 (✅):
```
ui/android/
├── MainActivity.kt              ✅ 여기로 이동
├── FlowerDiaryApplication.kt    ✅ 여기로 이동
├── di/*.kt                      ✅ 여기로 이동
├── screen/*.kt                  ✅ 이미 있음
└── AndroidManifest.xml          ✅ 여기로 이동

app/android/                     
└── (삭제하거나 최소한의 래퍼만)
```

## 🔧 build.gradle.kts 수정 필요

### ui/android/build.gradle.kts
```kotlin
plugins {
    id("com.android.application")  // library가 아닌 application!
    kotlin("android")
}

android {
    defaultConfig {
        applicationId = "com.flowerdiary"
        // 앱 설정
    }
}

dependencies {
    // 모든 의존성 조합
    implementation(projects.app.shared)
    implementation(projects.platform.android)
    implementation(projects.feature.diary)
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}
```

### app/shared/build.gradle.kts (이미 올바름)
```kotlin
plugins {
    kotlin("multiplatform")  // 순수 KMP
}
// Android 블록 없음!
```

## ✅ 검증 방법

```bash
# 1. 플랫폼 SDK는 오직 3개 모듈에만
find . -name "build.gradle.kts" -exec grep -l "com.android" {} \; | sort
# 결과: ui/android, platform/android만 나와야 함

# 2. 나머지는 순수 KMP
grep -l "kotlin(\"multiplatform\")" core/*/build.gradle.kts feature/*/build.gradle.kts app/shared/build.gradle.kts
# 모두 나와야 함
```

## 🎯 이점

1. **완벽한 플랫폼 격리**: SDK 의존성이 3개 모듈에만 존재
2. **빠른 빌드**: 대부분의 코드가 Android SDK 없이 컴파일
3. **테스트 용이**: 비즈니스 로직을 JVM에서 테스트
4. **명확한 경계**: 플랫폼 코드 위치가 명확

## ⚠️ 주의사항

- app:android는 삭제하거나 아주 얇은 래퍼만 유지
- 모든 Android 코드는 ui:android로 집중
- DI 설정도 ui:android에서 처리