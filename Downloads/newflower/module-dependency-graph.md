# Module Dependency Graph for FlowerDiary Project

## Module Overview

The project follows a clean architecture pattern with clear separation between platform-neutral and platform-specific modules.

### All Gradle Modules (from settings.gradle.kts):
1. `:core:common`
2. `:core:domain`
3. `:core:data`
4. `:feature:diary`
5. `:platform:android`
6. `:platform:ios`
7. `:ui:android`
8. `:ui:ios`
9. `:app:shared`
10. `:app:android`
11. `:app:ios`

## Dependency Analysis for Mentioned Modules

### Visual Dependency Graph

```
┌─────────────────┐
│  :app:android   │ (Android Application Module)
└────────┬────────┘
         │ implements
         ├──────────────────────────────────────────┐
         │                                          │
         ▼                                          ▼
┌─────────────────┐                        ┌───────────────┐
│  :app:shared    │ ◄──────────────────────│ :ui:android   │
└────────┬────────┘         implements     └───────┬───────┘
         │ api                                      │ implements
         │                                          │
         ▼                                          ▼
┌─────────────────┐                        ┌─────────────────┐
│ :feature:diary  │ ◄──────────────────────│:platform:android│
└────────┬────────┘         implements     └────────┬────────┘
         │ api/impl                                 │ implements
         │                                          │
         ▼                                          ▼
┌─────────────────┐                        ┌─────────────────┐
│  :core:domain   │                        │  :core:common   │
└────────┬────────┘                        └─────────────────┘
         │ api                                      ▲
         │                                          │
         ▼                                          │
┌─────────────────┐                                 │
│   :core:data    │ ────────────────────────────────┘
└─────────────────┘         api
```

### Detailed Module Dependencies

#### 1. `:app:android` (Application Module)
**Type:** Android Application
**Dependencies:**
- `implementation(projects.app.shared)`
- `implementation(projects.feature.diary)`
- `implementation(projects.core.common)`
- `implementation(projects.core.domain)`
- `implementation(projects.core.data)`
- `implementation(projects.ui.android)`
- `implementation(projects.platform.android)`
- Plus Android-specific libraries (Compose, Koin Android, etc.)

#### 2. `:app:shared` (Platform-neutral shared app logic)
**Type:** Kotlin Multiplatform (JVM + iOS)
**Dependencies:**
- `api(projects.feature.diary)`
- `api(projects.core.common)`
- `api(projects.core.domain)`
- `api(projects.core.data)`
- Plus Koin Core and Coroutines

#### 3. `:feature:diary` (Feature Module)
**Type:** Kotlin Multiplatform (JVM + iOS)
**Dependencies:**
- `api(project(":core:common"))`
- `api(project(":core:domain"))`
- `implementation(project(":core:data"))`
- Plus Coroutines and DateTime libraries

#### 4. `:core:common` (Common utilities)
**Type:** Kotlin Multiplatform (JVM + iOS)
**Dependencies:**
- Only external libraries (Coroutines, DateTime)
- No internal module dependencies

#### 5. `:core:domain` (Domain layer)
**Type:** Kotlin Multiplatform (JVM + iOS)
**Dependencies:**
- `api(project(":core:common"))`
- Plus Coroutines

#### 6. `:core:data` (Data layer)
**Type:** Kotlin Multiplatform (JVM + iOS)
**Dependencies:**
- `api(project(":core:common"))`
- **Note:** Does NOT depend on `:core:domain` (Clean Architecture compliance)
- Plus SQLDelight, Coroutines, Serialization

#### 7. `:ui:android` (Android UI components)
**Type:** Android Library
**Dependencies:**
- `implementation(projects.app.shared)`
- `implementation(projects.feature.diary)`
- `implementation(projects.core.common)`
- `implementation(projects.core.domain)`
- `implementation(projects.core.data)`
- `implementation(projects.platform.android)`
- Plus Android Compose and UI libraries

#### 8. `:platform:android` (Android platform implementations)
**Type:** Android Library
**Dependencies:**
- `implementation(project(":core:common"))`
- Plus Android-specific implementations (DataStore, SQLDelight Android Driver, etc.)

## Circular Dependency Analysis

### ✅ No Circular Dependencies Detected

The module structure follows a clear hierarchical pattern with no circular dependencies:

1. **Core Layer** (bottom):
   - `:core:common` - No internal dependencies
   - `:core:domain` - Depends only on `:core:common`
   - `:core:data` - Depends only on `:core:common`

2. **Feature Layer** (middle):
   - `:feature:diary` - Depends on core modules

3. **Platform Layer** (parallel):
   - `:platform:android` - Depends only on `:core:common`
   - `:ui:android` - Depends on multiple modules but no cycles

4. **App Layer** (top):
   - `:app:shared` - Aggregates platform-neutral modules
   - `:app:android` - Final composition of all modules

### Key Architecture Insights

1. **Clean Architecture Compliance**: 
   - `:core:data` does NOT depend on `:core:domain`, preventing circular dependencies
   - Domain layer remains pure with minimal dependencies

2. **Platform Separation**:
   - All core and feature modules are platform-neutral (Kotlin Multiplatform)
   - Android-specific code is isolated in `:platform:android` and `:ui:android`

3. **Dependency Direction**:
   - Dependencies flow from app → feature → core
   - Platform modules only depend on `:core:common`
   - No module depends on app or UI modules

4. **Module Responsibilities**:
   - `:core:common` - Shared utilities and extensions
   - `:core:domain` - Business logic and entities
   - `:core:data` - Data persistence and repositories
   - `:feature:diary` - Diary feature implementation
   - `:platform:android` - Android-specific implementations
   - `:ui:android` - Android UI components
   - `:app:shared` - Shared app configuration and DI
   - `:app:android` - Android app entry point

This architecture ensures maintainability, testability, and clear separation of concerns with no circular dependencies.