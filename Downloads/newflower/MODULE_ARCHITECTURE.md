# 꽃 다이어리 모듈 아키텍처 가이드

## 🎯 핵심 원칙
1. **platform과 ui를 제외한 모든 모듈은 100% 플랫폼 중립적 Kotlin 코드**
2. **모든 플랫폼 의존성은 expect/actual 패턴으로 격리**
3. **의존성은 항상 단방향 (상위 → 하위)**

## 📦 모듈별 역할과 규칙

### 1. **core:common** (최하위 계층)
- **역할**: 플랫폼 추상화, 공통 상수, 유틸리티
- **의존성**: 없음 (순수 Kotlin만)
- **포함 내용**:
  - `expect` 인터페이스 (DatabaseDriverProvider, FileStore 등)
  - 상수 (ColorPalette, Dimens, Config)
  - 플랫폼 중립 유틸 (ExceptionUtil, DateTimeUtil)
- **금지 사항**: android.*, UIKit.* import

### 2. **core:domain**
- **역할**: 비즈니스 엔티티와 규칙
- **의존성**: core:common
- **포함 내용**:
  - 도메인 모델 (Diary, BirthFlower)
  - Repository 인터페이스
  - 유스케이스
- **금지 사항**: 플랫폼 코드, UI 로직, 데이터베이스 구현

### 3. **core:data**
- **역할**: 데이터 저장소 구현
- **의존성**: core:common + core:domain
- **포함 내용**:
  - SQLDelight 구현
  - Repository 구현체
  - 데이터 매퍼
- **금지 사항**: 플랫폼 특화 코드 (expect/actual 제외)

### 4. **feature:diary**
- **역할**: 프레젠테이션 로직
- **의존성**: core:*
- **포함 내용**:
  - ViewModel
  - UI State
  - Facade
- **금지 사항**: 플랫폼 UI 코드, 직접적인 플랫폼 API 호출

### 5. **platform:android/ios**
- **역할**: expect 인터페이스의 actual 구현
- **의존성**: core:common (expect 인터페이스만)
- **포함 내용**:
  - AndroidDatabaseDriverProvider / IosDatabaseDriverProvider
  - AndroidFileStore / IosFileStore
  - AndroidLogger / IosLogger (actual object)
- **허용 사항**: 플랫폼 API 사용 (android.*, UIKit.*)

### 6. **ui:android/ios**
- **역할**: 플랫폼별 UI 구현
- **의존성**: feature:* + core:*
- **포함 내용**:
  - Compose UI / SwiftUI
  - Navigation
  - Theme
- **허용 사항**: 플랫폼 UI 프레임워크
- **금지 사항**: 비즈니스 로직

### 7. **app:android/ios**
- **역할**: 앱 진입점과 DI 설정
- **의존성**: 모든 모듈
- **포함 내용**:
  - Application/AppDelegate
  - DI 모듈 조합
  - 초기화 로직
- **금지 사항**: 비즈니스 로직, UI 구현

## 🔄 올바른 의존성 흐름

```
app:android ─┬─→ ui:android ──→ feature:diary ──→ core:domain ──→ core:common
             └─→ platform:android ──────────────────────────────────┘
```

## ⚠️ 주의사항

### Object 사용 규칙
1. **플랫폼 중립 object** (DateTimeUtil 등): 직접 사용, DI 불필요
2. **expect/actual object** (Logger 등): 직접 사용, DI 불필요
3. **일반 클래스**: DI로 주입

### 순환 의존성 방지
- platform 모듈은 core:common의 expect만 구현
- ui 모듈은 platform 모듈을 직접 참조하지 않음
- 모든 플랫폼 의존성은 app 모듈에서 조합

### 코드 공유 극대화
- 가능한 모든 로직은 core/feature 모듈에
- platform/ui는 최소한의 플랫폼 특화 코드만
- 중복 코드는 common 모듈로 추출