# 아키텍처 결정 기록

## #domain-types: 도메인 타입 시스템

### 결정: data class + validation 사용

```kotlin
// ✅ 올바른 방법
data class DiaryId(val value: String) : EntityId {
    init {
        require(value.startsWith("diary-")) { "Invalid DiaryId format" }
        require(value.length > 6) { "DiaryId too short" }
    }
}

// ❌ 잘못된 방법
@JvmInline value class DiaryId(val value: String)
typealias DiaryId = String
```

**이유:**
1. **KMP 완전 호환**: 모든 플랫폼에서 동일한 동작
2. **Serialization 안전**: kotlinx-serialization 완벽 지원
3. **타입 안전성**: 컴파일 타임 + 런타임 모두 보장
4. **코드 중복 방지**: expect/actual 불필요

**언제 사용:**
- 모든 도메인 엔티티 ID (DiaryId, FlowerId, UserId)
- 핵심 비즈니스 로직에서 사용되는 타입

---

## #performance-types: 성능 최적화 타입

### 결정: typealias 사용

```kotlin
// ✅ 성능 중요 영역
typealias SearchQuery = String
typealias FilterKey = String
typealias SortField = String
```

**이유:**
1. **성능 우선**: 메모리 오버헤드 Zero
2. **빈번한 생성**: 검색/필터링에서 자주 생성됨
3. **타입 혼동 허용**: 성능을 위해 일부 안전성 포기

**언제 사용:**
- 검색, 필터링, 정렬 관련 타입
- UI 상태 관련 임시 타입

---

## #store-immutability: Store 불변성

### 결정: 완전 불변 + 제어된 캐시

```kotlin
// ✅ 핵심 상태는 완전 불변
data class DiaryState(
    val diaries: ImmutableList<Diary> = persistentListOf(),
    val selectedDiaryId: DiaryId? = null,
    val isLoading: Boolean = false
)

// ✅ 성능 캐시는 제어된 가변성
internal class DiaryStateCache {
    private val searchCache = mutableMapOf<String, List<Diary>>()
    private val cacheMutex = Mutex()
    
    suspend fun getCached(query: String): List<Diary>? = cacheMutex.withLock {
        searchCache[query]
    }
}
```

**이유:**
1. **안전성**: 핵심 상태는 절대 변경 불가
2. **성능**: 캐시는 Mutex로 보호된 가변성 허용
3. **격리**: 캐시 변경이 핵심 상태에 영향 없음

**언제 적용:**
- 모든 Store의 핵심 상태: 완전 불변
- 성능 최적화 캐시: 제어된 가변성 허용

---

## #module-structure: 모듈 구조

### 결정: _internal + api 패턴

```
:feature-diary/
├── _internal/    # 내부 구현 (외부 접근 불가)
│   ├── domain/
│   ├── data/
│   └── store/
├── api/          # 공개 API (외부 모듈 접근 가능)
│   ├── DiaryApi.kt
│   └── DiaryModels.kt
└── ui/           # UI 컴포넌트
```

**이유:**
1. **캡슐화**: 내부 구현 상세 숨김
2. **안정적 API**: 공개 인터페이스만 외부 노출
3. **리팩토링 용이**: 내부 변경이 외부에 영향 없음

---

## #dependency-injection: DI 전략

### 결정: 수동 DI + Factory 패턴

```kotlin
// ✅ 명시적이고 타입 안전한 DI
class DiaryModule(
    private val database: Database,
    private val dispatcher: CoroutineDispatcher
) {
    fun provideDiaryRepository(): DiaryRepository {
        return SqlDelightDiaryRepository(database, dispatcher)
    }
    
    fun provideDiaryStore(repository: DiaryRepository): DiaryStore {
        return DiaryStoreImpl(repository, dispatcher)
    }
}

// ❌ 런타임 DI 프레임워크 사용 금지
```

**이유:**
1. **컴파일 타임 안전성**
2. **디버깅 용이**
3. **KMP 완벽 호환**
4. **성능 오버헤드 없음**

---

## #error-handling: 에러 처리 전략

### 결정: Result 타입 + sealed class

```kotlin
// ✅ 명시적 에러 처리
sealed class DiaryError : Error {
    data object NotFound : DiaryError()
    data class ValidationFailed(val reason: String) : DiaryError()
    data class StorageError(val cause: Throwable) : DiaryError()
}

suspend fun getDiary(id: DiaryId): Result<Diary> {
    return runCatching {
        repository.findById(id) ?: throw DiaryError.NotFound
    }
}
```

**이유:**
1. **명시적 에러 타입**: 어떤 에러가 발생할 수 있는지 명확
2. **함수형 에러 처리**: map, flatMap 등 활용 가능
3. **테스트 용이**: 에러 케이스 테스트 쉬움

---

## #testing-strategy: 테스트 전략

### 결정: 단위 테스트 + Fake 구현

```kotlin
// ✅ Fake 구현으로 빠른 테스트
class FakeDiaryRepository : DiaryRepository {
    private val diaries = mutableMapOf<DiaryId, Diary>()
    
    override suspend fun save(diary: Diary): Result<Unit> {
        diaries[diary.id] = diary
        return Result.success(Unit)
    }
}

// 테스트에서 사용
class DiaryStoreTest {
    private val repository = FakeDiaryRepository()
    private val store = DiaryStore(repository)
    
    @Test
    fun `should save diary successfully`() = runTest {
        // 테스트 구현
    }
}
```

**이유:**
1. **빠른 실행**: Mock 대신 Fake 사용으로 빠른 테스트
2. **실제와 유사**: Fake가 실제 구현과 유사하게 동작
3. **유지보수 용이**: 인터페이스 변경 시 컴파일 에러로 감지