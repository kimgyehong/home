# 🎯 신규 개발자 온보딩 가이드

## 1일차: 아키텍처 이해

- [ ] `docs/architecture-decisions.md` 읽기
- [ ] `./gradlew validateArchitecture` 실행해보기
- [ ] 왜 어떤 곳은 data class이고 어떤 곳은 typealias인지 이해

### 실습 과제
1. 새로운 ID 타입 만들어보기
   ```kotlin
   // :core/src/commonMain/kotlin/com/flowerdiary/core/types/UserId.kt
   package com.flowerdiary.core.types
   
   data class UserId(val value: String) : EntityId {
       init {
           require(value.startsWith("user-")) { 
               "Invalid UserId: must start with 'user-'"
           }
           require(value.length >= 10) { 
               "Invalid UserId: too short" 
           }
       }
   }
   ```

2. 빌드하고 검증 확인
   ```bash
   ./gradlew :core:build
   ```

## 2일차: 모듈 구조 파악

- [ ] `:feature-diary` 내부 구조 분석
- [ ] `_internal/` vs `api/` 차이점 이해
- [ ] `./gradlew checkModuleSize` 실행해보기

### 실습 과제
1. 모듈 리포트 확인
   ```bash
   ./gradlew :feature-diary:moduleReport
   ```

2. 새로운 내부 서비스 추가
   ```kotlin
   // :feature-diary/src/commonMain/kotlin/.../
   // _internal/domain/DiaryValidator.kt
   package com.flowerdiary.feature.diary._internal.domain
   
   internal class DiaryValidator {
       fun validate(diary: Diary): ValidationResult {
           return when {
               diary.title.isBlank() -> ValidationResult.Error("Title required")
               diary.content.length < 10 -> ValidationResult.Error("Too short")
               else -> ValidationResult.Success
           }
       }
   }
   
   internal sealed class ValidationResult {
       data object Success : ValidationResult()
       data class Error(val message: String) : ValidationResult()
   }
   ```

## 3일차: 실제 코딩

- [ ] 작은 기능 하나 추가해보기
- [ ] 코드 리뷰에서 아키텍처 규칙 준수 확인받기

### 실습 과제: 일기 태그 기능 추가
1. 도메인 모델 추가
2. Repository 메서드 추가
3. UI 컴포넌트 추가
4. 테스트 작성

## 🤔 자주 묻는 질문

### Q: 왜 DiaryId는 data class인데 SearchQuery는 typealias인가요?
**A: 성능 vs 안전성 트레이드오프입니다.**
- DiaryId: 도메인 핵심이므로 안전성 우선 → data class
- SearchQuery: 성능 중요하므로 속도 우선 → typealias

### Q: 언제 모듈을 분할해야 하나요?
**A: 75개 파일 초과 시 자동으로 빌드가 실패합니다.**
`docs/module-split-guide.md`를 참고하여 분할하세요.

### Q: Store에서 mutableMap을 쓰면 안 되나요?
**A: 핵심 상태에서는 절대 안 됩니다. 캐시에서만 Mutex와 함께 허용됩니다.**

### Q: _internal 패키지는 왜 필요한가요?
**A: 모듈의 내부 구현을 숨기고 안정적인 API만 노출하기 위함입니다.**
- _internal: 다른 모듈에서 접근 불가
- api: 다른 모듈에서 접근 가능

### Q: Convention Plugin이 뭔가요?
**A: 프로젝트 전체에 일관된 설정을 자동 적용하는 시스템입니다.**
- 모든 모듈에 동일한 Kotlin 버전 적용
- Detekt 규칙 자동 적용
- 아키텍처 규칙 자동 검증

## 🛠️ 유용한 명령어

```bash
# 아키텍처 검증
./gradlew validateArchitecture

# 모듈 크기 확인
./gradlew checkModuleSize

# 모듈 상세 리포트
./gradlew :feature-diary:moduleReport

# 전체 빌드 및 검증
./gradlew build

# Detekt 실행
./gradlew detekt

# 특정 모듈만 빌드
./gradlew :core:build

# 의존성 확인
./gradlew :feature-diary:dependencies
```

## 📚 추가 학습 자료

1. **Kotlin Multiplatform**
   - [공식 문서](https://kotlinlang.org/docs/multiplatform.html)
   - expect/actual 메커니즘 이해

2. **Immutable Collections**
   - [kotlinx.collections.immutable](https://github.com/Kotlin/kotlinx.collections.immutable)
   - PersistentList vs MutableList 차이

3. **Coroutines & Flow**
   - [Flow 가이드](https://kotlinlang.org/docs/flow.html)
   - StateFlow vs SharedFlow 사용법

## 🎉 환영합니다!

질문이 있으면 주저하지 말고 팀원들에게 물어보세요.
아키텍처 규칙은 코드 품질을 위한 것이지, 개발을 방해하기 위한 것이 아닙니다.

### 체크포인트
1주차 후 리뷰:
- [ ] 아키텍처 원칙 이해
- [ ] 모듈 구조 파악
- [ ] 기본 기능 구현 가능
- [ ] 테스트 작성 능력

Happy Coding! 🌸