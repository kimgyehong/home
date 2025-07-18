# 꽃 다이어리 최종 모듈 구조 정리

## 🎯 핵심 원칙
**"ui와 platform을 제외한 모든 모듈은 100% 플랫폼 중립적 Kotlin 코드"**

## 📦 모듈 구조 및 역할

### 1. **플랫폼 중립 모듈** (순수 Kotlin)

#### core:common
- **타입**: Kotlin Multiplatform Library
- **내용**: 
  - 상수 (ColorPalette, Dimens, Config)
  - 유틸리티 (ExceptionUtil, DateTimeUtil)
  - expect 인터페이스 정의
- **의존성**: 없음
- **금지**: android.*, UIKit.* import

#### core:domain
- **타입**: Kotlin Multiplatform Library
- **내용**: 도메인 엔티티, 유스케이스, Repository 인터페이스
- **의존성**: core:common
- **금지**: 플랫폼 특화 코드

#### core:data
- **타입**: Kotlin Multiplatform Library
- **내용**: SQLDelight 구현, Repository 구현체
- **의존성**: core:common, core:domain
- **금지**: 플랫폼 특화 코드

#### feature:diary
- **타입**: Kotlin Multiplatform Library
- **내용**: ViewModel, State, Facade
- **의존성**: core:*
- **금지**: UI 코드, 플랫폼 API

#### app:shared
- **타입**: Kotlin Multiplatform Library
- **내용**: 앱 레벨 공통 로직
- **의존성**: feature:*, core:*
- **금지**: 플랫폼 특화 코드

### 2. **플랫폼 특화 모듈**

#### platform:android / platform:ios
- **타입**: 플랫폼별 Library
- **내용**: expect 인터페이스의 actual 구현
- **의존성**: core:common (expect 인터페이스만)
- **허용**: android.*, UIKit.* 사용

#### ui:android / ui:ios  
- **타입**: 플랫폼별 Library
- **내용**: UI 컴포넌트 (Compose/SwiftUI)
- **의존성**: feature:*, core:*
- **허용**: 플랫폼 UI 프레임워크

#### app:android / app:ios
- **타입**: Application (실행 가능한 앱)
- **내용**: 진입점 (MainActivity/AppDelegate), DI 설정
- **의존성**: 모든 모듈
- **역할**: 모든 모듈을 조합하여 실행 가능한 앱 생성

## 🔄 의존성 흐름

```
app:android (Application)
    ├── app:shared (중립)
    ├── ui:android (플랫폼)
    ├── platform:android (플랫폼)
    └── feature:diary (중립)
         └── core:domain (중립)
              └── core:data (중립)
                   └── core:common (중립)
```

## ✅ 검증 체크리스트

### 플랫폼 중립성 검증
```bash
# core, feature, app:shared 모듈에 플랫폼 의존성이 없는지 확인
grep -r "import android\." core/ feature/ app/shared/
grep -r "import UIKit" core/ feature/ app/shared/
```

### 빌드 검증
```bash
# Android 앱 빌드
./gradlew :app:android:assembleDebug

# iOS 프레임워크 빌드  
./gradlew :app:shared:linkDebugFrameworkIosX64
```

### Detekt 검증
```bash
# 전체 프로젝트 품질 검사
./gradlew detekt
```

## 🚨 자주 하는 실수

1. **core 모듈에 플랫폼 코드 추가**
   - ❌ core:common에 `android.util.Log` 사용
   - ✅ expect/actual Logger 패턴 사용

2. **app 모듈을 multiplatform으로 설정**
   - ❌ app:android를 multiplatform으로 설정
   - ✅ app:android는 Android application

3. **ui 모듈에서 비즈니스 로직 구현**
   - ❌ ui:android에서 데이터 처리
   - ✅ feature 모듈에서 처리 후 State 전달

4. **플랫폼 모듈 간 직접 참조**
   - ❌ ui:android → platform:android 직접 참조
   - ✅ app:android에서만 조합

## 🎯 최종 목표

이 구조를 통해:
- **코드 재사용 극대화**: 비즈니스 로직 100% 공유
- **테스트 용이성**: 플랫폼 독립적 단위 테스트
- **유지보수성**: 명확한 책임 분리
- **확장성**: 새로운 플랫폼 추가 용이