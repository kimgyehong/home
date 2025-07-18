# Design Document

## Overview

The Flower Diary application will be implemented using Kotlin Multiplatform (KMP) architecture with complete business logic sharing between Android and iOS platforms. The design follows Clean Architecture principles with strict separation of concerns, using SQLDelight for cross-platform data persistence and expect/actual patterns for platform abstraction. All core modules will be written in pure Kotlin without any platform dependencies, ensuring true platform neutrality. The design strictly adheres to SOLID principles, Detekt compliance, SRP enforcement, zero hardcoding policy, and boilerplate minimization. The application features a diary system with birth flower themes, collection management, BGM system, and rich text decoration capabilities.

## Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    UI Layer (Platform Specific)             │
├─────────────────────┬───────────────────────────────────────┤
│   Android UI        │            iOS UI                     │
│   (Compose/XML)     │         (SwiftUI)                     │
└─────────────────────┴───────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                    Feature Layer (Shared)                   │
├─────────────────────────────────────────────────────────────┤
│  • DiaryViewModel    • CollectionViewModel                  │
│  • OpeningViewModel  • SettingsViewModel                    │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                   Domain Layer (Shared)                     │
├─────────────────────────────────────────────────────────────┤
│  • Entities (Diary, BirthFlower)                           │
│  • Use Cases (SaveDiary, GetFlower, UnlockTodayFlower)     │
│  • Repository Interfaces                                    │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                    Data Layer (Shared)                      │
├─────────────────────────────────────────────────────────────┤
│  • SQLDelight Database                                      │
│  • Repository Implementations                               │
│  • Unit of Work Implementation                              │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                  Platform Layer (Actual)                    │
├─────────────────────┬───────────────────────────────────────┤
│   Android Platform  │         iOS Platform                  │
│   • DatabaseDriver  │       • DatabaseDriver               │
│   • AudioManager    │       • AudioManager                 │
│   • FileManager     │       • FileManager                  │
└─────────────────────┴───────────────────────────────────────┘
```

### Module Dependency Flow

```
ui-* → feature-* → core-domain → core-data → core-common
                                      ↑
                              platform-* (actual implementations)
```

## Components and Interfaces

### Core Common Module

**Purpose:** Platform abstraction and shared utilities (Pure Kotlin Only)

**Key Components:**
- `DatabaseDriverProvider` - expect/actual for SQLDelight drivers (pure Kotlin interface)
- `AudioManager` - expect/actual for BGM playback (pure Kotlin interface)
- `FileManager` - expect/actual for image/video handling (pure Kotlin interface)
- `Constants` - All app constants and configuration (zero hardcoding enforcement)
- `ExceptionUtil` - Centralized error handling (SOLID compliant)
- `Result` - Sealed class for operation results (boilerplate minimization)

**SOLID Principles Implementation:**
- **SRP**: Each interface has single responsibility
- **OCP**: Extensible through expect/actual without modification
- **LSP**: All actual implementations are substitutable
- **ISP**: Interfaces are focused and minimal
- **DIP**: High-level modules depend on abstractions

```kotlin
// Platform Abstractions
expect interface DatabaseDriverProvider {
    fun createDriver(schema: SqlDriver.Schema, name: String): SqlDriver
}

expect interface AudioManager {
    fun playBGM(trackIndex: Int)
    fun stopBGM()
    fun setBGMVolume(volume: Float)
}

expect interface FileManager {
    suspend fun saveImage(data: ByteArray, fileName: String): String
    suspend fun loadImage(fileName: String): ByteArray?
}

// Constants Management
object AppConstants {
    const val DATABASE_NAME = "flower_diary.db"
    const val DATABASE_VERSION = 1
    const val MAX_TITLE_LENGTH = 50
    const val MAX_CONTENT_LENGTH = 1000
    const val BGM_TRACK_COUNT = 4
    const val LOADING_IMAGE_COUNT = 7
}

object ColorPalette {
    val Primary = Color(0xFF6A9AFC)
    val Secondary = Color(0xFFFFC96A)
    val Background = Color(0xFFF8F9FA)
    val Error = Color(0xFFFF6A6A)
}

object FlowerDiaryDimens {
    object Padding {
        val Small = LogicalPx(8f)
        val Medium = LogicalPx(16f)
        val Large = LogicalPx(24f)
    }
    object Size {
        val FlowerCardSize = LogicalPx(120f)
        val FlowerDetailSize = LogicalPx(200f)
    }
}
```

### Domain Layer

**Purpose:** Business logic and entities

**Key Entities:**
```kotlin
data class Diary(
    val id: DiaryId,
    val title: String,
    val content: String,
    val date: LocalDate,
    val flowerId: FlowerId,
    val fontFamily: String,
    val fontColor: String,
    val createdAt: Instant,
    val updatedAt: Instant
)

data class BirthFlower(
    val id: FlowerId,
    val month: Int,
    val day: Int,
    val nameKr: String,
    val nameEn: String,
    val meaning: String,
    val description: String,
    val imageUrl: String,
    val backgroundColor: String,
    val isUnlocked: Boolean
)

data class AppSettings(
    val selectedBGMTrack: Int,
    val bgmVolume: Float,
    val isFirstLaunch: Boolean
)
```

**Key Use Cases:**
```kotlin
class SaveDiaryUseCase(
    private val repository: DiaryRepository,
    private val unitOfWork: UnitOfWork
) {
    suspend operator fun invoke(diary: Diary): Result<Unit>
}

class UnlockTodayFlowerUseCase(
    private val repository: BirthFlowerRepository,
    private val dateTimeUtil: DateTimeUtil
) {
    suspend operator fun invoke(): Result<BirthFlower>
}

class GetFlowerCollectionUseCase(
    private val repository: BirthFlowerRepository
) {
    suspend operator fun invoke(): Result<List<BirthFlower>>
}
```

### Data Layer

**Purpose:** Data persistence and repository implementations

**SQLDelight Schema:**
```sql
-- BirthFlower.sq
CREATE TABLE BirthFlower (
    id TEXT NOT NULL PRIMARY KEY,
    month INTEGER NOT NULL,
    day INTEGER NOT NULL,
    name_kr TEXT NOT NULL,
    name_en TEXT NOT NULL,
    meaning TEXT NOT NULL,
    description TEXT NOT NULL,
    image_url TEXT NOT NULL,
    background_color TEXT NOT NULL,
    is_unlocked INTEGER NOT NULL DEFAULT 0
);

-- Diary.sq  
CREATE TABLE Diary (
    id TEXT NOT NULL PRIMARY KEY,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    date_year INTEGER NOT NULL,
    date_month INTEGER NOT NULL,
    date_day INTEGER NOT NULL,
    flower_id TEXT NOT NULL,
    font_family TEXT NOT NULL DEFAULT 'default',
    font_color TEXT NOT NULL DEFAULT '#000000',
    created_at INTEGER NOT NULL,
    updated_at INTEGER NOT NULL,
    FOREIGN KEY (flower_id) REFERENCES BirthFlower(id)
);

-- AppSettings.sq
CREATE TABLE AppSettings (
    key TEXT NOT NULL PRIMARY KEY,
    value TEXT NOT NULL
);
```

**Repository Implementations:**
```kotlin
class SqlDelightDiaryRepository(
    private val driverProvider: DatabaseDriverProvider,
    private val unitOfWork: UnitOfWork
) : DiaryRepository {
    
    private val queries = FlowerDiaryDb(
        driverProvider.createDriver(FlowerDiaryDb.Schema, AppConstants.DATABASE_NAME)
    ).diaryQueries
    
    override suspend fun save(diary: Diary): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            queries.insertOrReplace(diary.toDbModel())
        }
    
    override suspend fun findById(id: DiaryId): Result<Diary?> =
        ExceptionUtil.runCatchingSuspend {
            queries.selectById(id.value).executeAsOneOrNull()?.toDomain()
        }
}
```

**Unit of Work Implementation:**
```kotlin
class SqlDelightUnitOfWork(
    private val driverProvider: DatabaseDriverProvider
) : UnitOfWork {
    
    private val database = FlowerDiaryDb(
        driverProvider.createDriver(FlowerDiaryDb.Schema, AppConstants.DATABASE_NAME)
    )
    
    override suspend fun <T> transaction(block: suspend () -> T): Result<T> =
        ExceptionUtil.runCatchingSuspend {
            database.transactionWithResult {
                runBlocking { block() }
            }
        }
}
```

### Feature Layer

**Purpose:** ViewModels and presentation logic

**Key ViewModels:**
```kotlin
class DiaryViewModel(
    private val saveDiaryUseCase: SaveDiaryUseCase,
    private val getDiaryUseCase: GetDiaryUseCase,
    private val getTodayFlowerUseCase: GetTodayFlowerUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DiaryUiState.Loading)
    val uiState: StateFlow<DiaryUiState> = _uiState.asStateFlow()
    
    fun saveDiary(title: String, content: String, fontFamily: String, fontColor: String) {
        viewModelScope.launch {
            // Implementation
        }
    }
}

class CollectionViewModel(
    private val getFlowerCollectionUseCase: GetFlowerCollectionUseCase,
    private val unlockTodayFlowerUseCase: UnlockTodayFlowerUseCase
) : ViewModel() {
    
    private val _flowers = MutableStateFlow<List<BirthFlower>>(emptyList())
    val flowers: StateFlow<List<BirthFlower>> = _flowers.asStateFlow()
    
    init {
        unlockTodayFlower()
        loadFlowers()
    }
}
```

### Platform Layer

**Purpose:** Platform-specific implementations

**Android Implementations:**
```kotlin
actual class AndroidDatabaseDriverProvider(
    private val context: Context
) : DatabaseDriverProvider {
    actual override fun createDriver(schema: SqlDriver.Schema, name: String): SqlDriver =
        AndroidSqliteDriver(schema, context, name)
}

actual class AndroidAudioManager(
    private val context: Context
) : AudioManager {
    private var mediaPlayer: MediaPlayer? = null
    
    actual override fun playBGM(trackIndex: Int) {
        // Implementation
    }
}
```

**iOS Implementations:**
```kotlin
actual class IosDatabaseDriverProvider : DatabaseDriverProvider {
    actual override fun createDriver(schema: SqlDriver.Schema, name: String): SqlDriver =
        NativeSqliteDriver(schema, name)
}

actual class IosAudioManager : AudioManager {
    actual override fun playBGM(trackIndex: Int) {
        // Implementation using AVAudioPlayer
    }
}
```

## Data Models

### Database Schema Design

The application uses three main tables:

1. **BirthFlower** - Stores 365 birth flowers with unlock status
2. **Diary** - Stores diary entries with formatting and flower associations  
3. **AppSettings** - Stores user preferences and app state

### Data Flow

```
User Input → ViewModel → UseCase → Repository → SQLDelight → Database
                ↓
UI State ← ViewModel ← UseCase ← Repository ← SQLDelight ← Database
```

### Caching Strategy

- Birth flowers are loaded once at app startup and cached in memory
- Diary entries are loaded on-demand with LRU caching
- App settings are cached and synchronized on changes

## Error Handling

### Exception Handling Strategy

All error handling follows a centralized pattern using `ExceptionUtil`:

```kotlin
object ExceptionUtil {
    suspend inline fun <T> runCatchingSuspend(
        crossinline block: suspend () -> T
    ): Result<T> = try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e // Preserve coroutine cancellation
    } catch (e: Throwable) {
        Result.failure(e)
    }
}
```

### Error Types

- `DatabaseException` - Database operation failures
- `ValidationException` - Input validation failures  
- `NetworkException` - Network-related failures (future)
- `FileException` - File operation failures

### Error Recovery

- Database errors: Retry with exponential backoff
- Validation errors: Show user-friendly messages
- File errors: Fallback to default resources
- Network errors: Offline mode with sync when available

## Testing Strategy

### Unit Testing

**Core Domain Testing:**
- Entity validation logic
- Use case business rules
- Repository contract compliance
- Error handling scenarios

**Data Layer Testing:**
- SQLDelight query correctness
- Repository implementations
- Unit of Work transaction behavior
- Database migration testing

### Integration Testing

**Cross-Platform Testing:**
- Database operations on both platforms
- expect/actual implementation consistency
- End-to-end data flow validation

### UI Testing

**Platform-Specific Testing:**
- Android: Espresso/Compose testing
- iOS: XCUITest integration
- Screenshot testing for visual consistency

### Test Structure

```
src/
├── commonTest/          # Shared test code
│   ├── domain/         # Use case tests
│   └── data/           # Repository tests
├── androidTest/        # Android-specific tests
└── iosTest/           # iOS-specific tests
```

### Quality Gates

1. **Unit Test Coverage:** Minimum 80% for core modules
2. **Detekt Rules:** Zero violations allowed
3. **Build Success:** Both platforms must build successfully
4. **Integration Tests:** All cross-platform scenarios must pass

## Performance Considerations

### Database Optimization

- Indexed queries for date-based diary lookups
- Batch operations for flower collection initialization
- Connection pooling for concurrent access
- Lazy loading for large content

### Memory Management

- Image caching with size limits
- ViewModel state cleanup on navigation
- Proper coroutine cancellation
- Resource cleanup in platform implementations

### Startup Performance

- Lazy initialization of non-critical components
- Background flower collection setup
- Preloaded essential resources
- Optimized splash screen transitions

This design provides a robust, maintainable, and scalable foundation for the Flower Diary application while ensuring complete platform neutrality and adherence to quality standards.