# Detekt Issues Report

## Summary of Detekt Issues Found in Core Modules

### 1. WildcardImport - Import statements using wildcards (*)

#### core/common
- `/mnt/c/Users/endof/Downloads/newflower/core/common/src/commonMain/kotlin/com/flowerdiary/common/platform/DateTimeUtil.kt:3`
  - `import kotlinx.datetime.*`

#### core/domain
- `/mnt/c/Users/endof/Downloads/newflower/core/domain/src/commonTest/kotlin/com/flowerdiary/domain/model/DiaryTest.kt:5`
  - `import kotlin.test.*`
- `/mnt/c/Users/endof/Downloads/newflower/core/domain/src/commonTest/kotlin/com/flowerdiary/domain/model/BirthFlowerTest.kt:3`
  - `import kotlin.test.*`
- `/mnt/c/Users/endof/Downloads/newflower/core/domain/src/commonTest/kotlin/com/flowerdiary/domain/usecase/SaveDiaryUseCaseTest.kt:4`
  - `import com.flowerdiary.domain.model.*`
- `/mnt/c/Users/endof/Downloads/newflower/core/domain/src/commonTest/kotlin/com/flowerdiary/domain/usecase/SaveDiaryUseCaseTest.kt:8`
  - `import kotlin.test.*`
- `/mnt/c/Users/endof/Downloads/newflower/core/domain/src/commonMain/kotlin/com/flowerdiary/domain/usecase/diary/GetDiariesUseCaseRefactored.kt:6`
  - `import com.flowerdiary.domain.specification.*`
- `/mnt/c/Users/endof/Downloads/newflower/core/domain/src/commonTest/kotlin/com/flowerdiary/domain/usecase/flower/GetBirthFlowerUseCaseTest.kt:7`
  - `import kotlin.test.*`
- `/mnt/c/Users/endof/Downloads/newflower/core/domain/src/commonTest/kotlin/com/flowerdiary/domain/usecase/flower/UnlockBirthFlowerUseCaseTest.kt:10`
  - `import kotlin.test.*`
- `/mnt/c/Users/endof/Downloads/newflower/core/domain/src/commonTest/kotlin/com/flowerdiary/domain/usecase/flower/RecommendFlowerUseCaseTest.kt:10`
  - `import kotlin.test.*`

#### core/data
- `/mnt/c/Users/endof/Downloads/newflower/core/data/src/commonMain/kotlin/com/flowerdiary/data/model/DiaryMapper.kt:5`
  - `import com.flowerdiary.domain.model.*`

#### feature/diary
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/mapper/DiaryUIMapper.kt:8`
  - `import kotlinx.datetime.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/DataSettingsViewModel.kt:8`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/DisplaySettingsViewModel.kt:8`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/DiaryEditorViewModelRefactored.kt:7`
  - `import com.flowerdiary.domain.model.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/DiaryEditorViewModelRefactored.kt:10`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/CollectionViewModel.kt:9`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/DiaryEditorViewModel.kt:7`
  - `import com.flowerdiary.domain.model.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/DiaryEditorViewModel.kt:12`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/BaseViewModel.kt:4`
  - `import kotlinx.coroutines.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/BaseViewModel.kt:5`
  - `import kotlinx.coroutines.flow.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/SettingsViewModel.kt:10`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/BGMSettingsViewModel.kt:10`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/state/DiaryEditorStateSimple.kt:3`
  - `import com.flowerdiary.domain.model.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/DiaryStyleViewModel.kt:7`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/state/DiaryEditorState.kt:3`
  - `import com.flowerdiary.domain.model.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/NotificationSettingsViewModel.kt:8`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/DiaryListViewModel.kt:9`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/viewmodel/FlowerSelectionViewModel.kt:9`
  - `import com.flowerdiary.feature.diary.state.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/di/FeatureDiaryModule.kt:6`
  - `import com.flowerdiary.domain.usecase.diary.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/di/FeatureDiaryModule.kt:7`
  - `import com.flowerdiary.domain.usecase.flower.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/di/FeatureDiaryModule.kt:9`
  - `import com.flowerdiary.feature.diary.viewmodel.*`
- `/mnt/c/Users/endof/Downloads/newflower/feature/diary/src/commonMain/kotlin/com/flowerdiary/feature/diary/facade/DiaryFacade.kt:9`
  - `import com.flowerdiary.domain.model.*`

#### app/android (non-platform specific)
- `/mnt/c/Users/endof/Downloads/newflower/app/android/src/main/kotlin/com/flowerdiary/android/MainActivity.kt:10`
  - `import androidx.compose.runtime.*`
- `/mnt/c/Users/endof/Downloads/newflower/app/android/src/main/kotlin/com/flowerdiary/android/di/DataModule.kt:5`
  - `import com.flowerdiary.data.database.init.*`
- `/mnt/c/Users/endof/Downloads/newflower/app/android/src/main/kotlin/com/flowerdiary/android/di/FeatureModule.kt:5`
  - `import com.flowerdiary.feature.diary.viewmodel.*`
- `/mnt/c/Users/endof/Downloads/newflower/app/android/src/main/kotlin/com/flowerdiary/android/di/PlatformModule.kt:3`
  - `import com.flowerdiary.common.platform.*`
- `/mnt/c/Users/endof/Downloads/newflower/app/android/src/main/kotlin/com/flowerdiary/android/di/PlatformModule.kt:4`
  - `import com.flowerdiary.platform.android.*`
- `/mnt/c/Users/endof/Downloads/newflower/app/android/src/main/kotlin/com/flowerdiary/android/di/DomainModule.kt:3`
  - `import com.flowerdiary.domain.usecase.diary.*`
- `/mnt/c/Users/endof/Downloads/newflower/app/android/src/main/kotlin/com/flowerdiary/android/di/DomainModule.kt:4`
  - `import com.flowerdiary.domain.usecase.flower.*`

### 2. NewLineAtEndOfFile - Files missing newline at end

#### core/common - All files missing newline:
- `BirthFlowerConstants.kt`
- `ColorPalette.kt`
- `Config.kt`
- `Dimens.kt`
- `Messages.kt`
- `UnitOfWork.kt`
- `AudioManager.kt`
- `DatabaseDriverProvider.kt`
- `DateTimeUtil.kt`
- `Dispatcher.kt`
- `FileStore.kt`
- `PreferencesStore.kt`
- `ResourcePathProvider.kt`
- `ExceptionUtil.kt`
- `Logger.kt`
- `LoggerExtensions.kt`

#### core/domain - All files missing newline:
- All model classes (`BirthFlower.kt`, `Diary.kt`, `DiaryId.kt`, `DiarySettings.kt`, `FlowerId.kt`, `Mood.kt`, `Weather.kt`)
- All repository interfaces (`BirthFlowerRepository.kt`, `DiaryRepository.kt`)
- All use cases in diary and flower packages
- All test files

#### core/data - All files missing newline:
- All files in the module

#### feature/diary - All files missing newline:
- All files in the module

#### app/android - All files missing newline:
- All DI module files
- `FlowerDiaryApplication.kt`
- `MainActivity.kt`

### 3. TooGenericExceptionCaught
**No instances found** - The codebase does not catch generic `Exception` types.

### 4. MagicNumber - Hardcoded numeric values

#### core/common
- `Config.kt` - Contains many hardcoded values (50, 100, 1000, 16.0f, 10.0f, 24.0f, 20, 100, 50, 80, 1024, 150, 300, 500, 1000, 4, 7, 365, 2, 60, 24, 7, 30, 365, 12, 9999, 8, 2)
- `BirthFlowerConstants.kt` - Contains date-related constants (5, 2, 3, 5, 8, 2, 12, 31, month days mapping)
- `ColorPalette.kt` - Contains color hex values
- `Dimens.kt` - Contains UI dimension constants (3, 2.0f)
- `ResourcePathProvider.kt` - Contains date validation (1..31)

#### core/domain
- Test files contain test data values
- `DiaryQuerySpecification.kt` - SQL formatting (1000, 4, 2)
- `FlowerId.kt` - Month offset calculations (31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
- `BirthFlower.kt` - Validation ranges (1..12, 1..31)

#### core/data
- `BirthFlowerDataSource.kt` - Date parsing (2, 100, 360, 255, 60, 2, 16, 8, 24)
- `BirthFlowerDatabaseInitializer.kt` - Step indicators (2, 3)
- `BirthFlowerData.kt` - String parsing indices (2, 3, 5)
- `BirthFlowerColorProvider.kt` - Month constants (2-12)

#### feature/diary
- `FlowerUIMapper.kt` - Hex formatting (16, 8, 2)
- `SettingsState.kt` - Volume range (0-100)
- `DiaryFacade.kt` - Step comments (2, 3)
- `CollectionState.kt` - Total flowers constant (365)

### 5. UnusedImports/UnusedPrivateProperty
Due to the complexity of analyzing usage across files, potential unused items include:
- Some private properties in test classes may only be used in specific test cases
- Some private helper functions might have limited usage

## Recommendations

1. **WildcardImport**: Replace all wildcard imports with specific imports
2. **NewLineAtEndOfFile**: Add newline characters at the end of all Kotlin files
3. **MagicNumber**: Extract hardcoded numbers to named constants, especially in `Config.kt`
4. **Code Organization**: Consider grouping related constants into sealed classes or enums

## Priority Issues
1. **High**: NewLineAtEndOfFile - Affects all files
2. **Medium**: WildcardImport - Affects readability and potential naming conflicts
3. **Low**: MagicNumber - Most are already in constant files, but could be better organized