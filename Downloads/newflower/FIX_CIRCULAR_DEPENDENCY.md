# 🔧 순환 의존성 해결 가이드

## 문제 상황
```
Circular dependency between the following tasks:
:app:android:processDebugResources
\--- :app:android:processDebugResources (*)
```

## 원인 분석
`processDebugResources` 태스크가 자기 자신을 의존하는 순환 참조가 발생했습니다. 이는 보통 다음과 같은 경우에 발생합니다:

1. 빌드 스크립트에서 태스크 의존성을 잘못 설정
2. 플러그인 간 충돌
3. 리소스 처리 관련 설정 오류

## 해결 방법

### 1단계: 임시 해결 (이미 적용됨)
```properties
# gradle.properties
android.disableResourceValidation=true
```

### 2단계: 근본적 해결
app:android 모듈이 ui:android를 의존하면서 발생할 수 있는 문제입니다.

## 권장 사항

### 옵션 1: 의존성 구조 단순화
현재:
```
app:android → ui:android (library)
app:android → platform:android
app:android → feature:diary
app:android → core:*
```

### 옵션 2: 빌드 설정 확인
```kotlin
// app/android/build.gradle.kts
android {
    // ... 기존 설정 ...
    
    // 리소스 처리 최적화
    buildFeatures {
        buildConfig = true
        resValues = false
    }
}
```

## 추가 디버깅
```bash
# 상세 로그 확인
./gradlew :app:android:assembleDebug --info

# 태스크 의존성 확인
./gradlew :app:android:assembleDebug --dry-run
```

## 주의사항
- `android.disableResourceValidation=true`는 임시 해결책
- 프로덕션 빌드에서는 제거 권장
- 근본 원인 파악 필요