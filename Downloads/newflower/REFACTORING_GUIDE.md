# 꽃 다이어리 리팩토링 가이드

## 🔴 현재 발견된 문제점들

### 1. **모듈 타입 오류**
- ❌ `ui/android`가 `application`으로 설정됨 → ✅ `library`여야 함
- ❌ `app/android`가 `multiplatform`으로 설정됨 → ✅ `application`이어야 함

### 2. **DI 구조 문제**
- ❌ `DateTimeUtil`, `Logger` object를 DI로 주입 → ✅ 직접 사용
- ❌ iOS에서 수동 DI 사용 → ✅ 구조화된 DI 시스템 필요

### 3. **의존성 꼬임**
- ❌ ui 모듈이 최종 APK 생성 → ✅ app 모듈이 APK 생성
- ❌ 순환 의존성 가능성 → ✅ 단방향 의존성 강제

## 🟢 해결 방안

### 1. **즉시 수정 필요 (Critical)**

#### a) ui/android/build.gradle.kts
```kotlin
// 변경 전
id("com.android.application")
applicationId = "com.flowerdiary.android"

// 변경 후
id("com.android.library")
// applicationId 제거
```

#### b) app/android/build.gradle.kts
```kotlin
// 변경 전
plugins {
    kotlin("multiplatform")
}

// 변경 후
plugins {
    id("com.android.application")
    kotlin("android")
}
```

#### c) PlatformModule.kt
```kotlin
// 제거
single { DateTimeUtil }
single { Logger }
```

### 2. **프로젝트 구조 재정립**

```
✅ 올바른 의존성 흐름:

app (Application)
 ├─→ ui (Library)
 ├─→ platform (Library)
 └─→ feature (Library)
      └─→ core (Library)

❌ 잘못된 의존성:
- ui → platform (직접 참조 금지)
- core → platform (순환 의존성)
- feature → ui (역방향)
```

### 3. **모듈별 책임 명확화**

| 모듈 | 타입 | 책임 | 허용 | 금지 |
|------|------|------|------|------|
| app/android | application | APK 생성, DI 설정 | 모든 의존성 | 비즈니스 로직 |
| ui/android | library | Compose UI | feature, core | 플랫폼 직접 호출 |
| platform/android | library | actual 구현 | android.* | 비즈니스 로직 |
| feature/* | library | ViewModel, State | core | 플랫폼 코드 |
| core/* | library | 도메인, 데이터 | 순수 Kotlin | 플랫폼 코드 |

## 🔧 리팩토링 순서

1. **긴급**: ui/android를 library로 변경
2. **긴급**: app/android를 application으로 변경
3. **중요**: PlatformModule에서 object 주입 제거
4. **중요**: 의존성 그래프 검증
5. **개선**: iOS 수동 DI 구조화

## 📝 검증 체크리스트

- [ ] `./gradlew app:android:assembleDebug` 성공
- [ ] ui 모듈에서 APK 생성 시도 시 실패
- [ ] 모든 core 모듈에 android.* import 없음
- [ ] Logger와 DateTimeUtil이 DI 없이 직접 사용됨
- [ ] 순환 의존성 없음

## 🎯 최종 목표

**"platform과 ui를 제외한 모든 모듈은 100% 플랫폼 중립적 Kotlin 코드"**

이를 통해:
- Swift 전환 시 core/feature 재사용 가능
- 테스트 용이성 향상
- 유지보수성 개선
- 명확한 책임 분리