# 모듈 분할 가이드

## 🚨 분할 시점

- **75개 파일 초과**: 즉시 분할 필요 (빌드 실패)
- **60개 파일 초과**: 분할 준비 권장 (경고 표시)

## 🔧 분할 방법

### Step 1: 현재 구조 분석

```bash
./gradlew :feature-diary:moduleReport
```

출력 예시:
```
📊 Module Size Report: feature-diary
Total files: 72/75

Package distribution:
  com.flowerdiary.feature.diary._internal.domain: 18 files
  com.flowerdiary.feature.diary._internal.data: 20 files
  com.flowerdiary.feature.diary._internal.store: 15 files
  com.flowerdiary.feature.diary.ui: 15 files
  com.flowerdiary.feature.diary.api: 4 files
```

### Step 2: 서브 모듈 생성

1. **새 모듈 디렉토리 생성**
```
:feature-diary-domain/
:feature-diary-data/
:feature-diary-store/
:feature-diary-ui/
```

2. **build.gradle.kts 생성**
```kotlin
// :feature-diary-domain/build.gradle.kts
plugins {
    id("com.flowerdiary.conventions")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core)
            }
        }
    }
}
```

### Step 3: 코드 이동

1. **패키지별 이동**
   - `_internal/domain/*` → `:feature-diary-domain`
   - `_internal/data/*` → `:feature-diary-data`
   - `_internal/store/*` → `:feature-diary-store`
   - `ui/* + api/*` → `:feature-diary-ui`

2. **import 경로 수정**
   - IntelliJ의 "Move" 리팩토링 사용
   - 패키지명은 유지 (경로만 변경)

### Step 4: 의존성 재구성

```kotlin
// :feature-diary-ui/build.gradle.kts
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.featureDiaryDomain)
                implementation(projects.featureDiaryData)
                implementation(projects.featureDiaryStore)
                implementation(projects.shared)
            }
        }
    }
}

// :feature-diary-data/build.gradle.kts
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.featureDiaryDomain)
                implementation(projects.core)
            }
        }
    }
}
```

### Step 5: settings.gradle.kts 업데이트

```kotlin
include(
    ":feature-diary-domain",
    ":feature-diary-data",
    ":feature-diary-store",
    ":feature-diary-ui"
)
```

## ✅ 분할 후 검증

### 1. 순환 의존성 확인
```bash
./gradlew :feature-diary-ui:dependencies --configuration commonMainImplementation
```

### 2. 빌드 성공 확인
```bash
./gradlew :feature-diary-ui:build
```

### 3. 모듈 크기 재확인
```bash
./gradlew :feature-diary-domain:moduleReport
./gradlew :feature-diary-data:moduleReport
./gradlew :feature-diary-store:moduleReport
./gradlew :feature-diary-ui:moduleReport
```

## 📋 체크리스트

- [ ] 각 서브 모듈이 75개 파일 미만인가?
- [ ] 각 서브 모듈이 단일 책임을 갖는가?
- [ ] 순환 의존성이 없는가?
- [ ] 공개 API가 명확히 정의되었는가?
- [ ] 테스트가 모두 통과하는가?
- [ ] 빌드 시간이 개선되었는가?

## 🎯 모범 사례

### DO ✅
- 논리적 경계에 따라 분할
- 공개 API 최소화
- 인터페이스로 의존성 역전
- 각 모듈에 명확한 책임 부여

### DON'T ❌
- 임의로 파일 수만 맞추기
- 순환 의존성 만들기
- internal 구현 직접 참조
- 너무 작은 모듈로 과도하게 분할

## 📝 분할 예시

### Before: 단일 모듈
```
:feature-diary/ (72 files)
├── _internal/
│   ├── domain/ (18 files)
│   ├── data/ (20 files)
│   └── store/ (15 files)
├── api/ (4 files)
└── ui/ (15 files)
```

### After: 서브 모듈
```
:feature-diary-domain/ (18 files)
├── model/
├── usecase/
└── repository/

:feature-diary-data/ (20 files)
├── repository/
├── mapper/
└── source/

:feature-diary-store/ (15 files)
├── state/
├── reducer/
└── effect/

:feature-diary-ui/ (19 files)
├── screen/
├── component/
└── api/
```

## 🚀 자동화 스크립트

향후 자동 분할 스크립트 제공 예정:
```bash
./gradlew :feature-diary:autoSplit
```