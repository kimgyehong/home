# Requirements Document

## Introduction

This document outlines the requirements for implementing a complete Flower Diary application using Kotlin Multiplatform (KMP) architecture. The application will feature a diary system with birth flowers, collection management, BGM system, and decorative writing features. The core business logic will be 100% shared between Android and iOS platforms using SQLDelight for data persistence, while maintaining platform-neutral architecture with strict quality standards including SOLID principles, Detekt compliance, and zero hardcoding.

## Requirements

### Requirement 1: Platform-Neutral Architecture

**User Story:** As a developer, I want to share 100% of business logic between Android and iOS platforms, so that I can maintain consistent functionality while reducing development overhead.

#### Acceptance Criteria

1. WHEN the application is built THEN all core modules (common, domain, data) SHALL contain zero Android or iOS dependencies
2. WHEN platform-specific functionality is needed THEN the system SHALL use expect/actual pattern for abstraction
3. WHEN building for either platform THEN SQLDelight database layer SHALL work identically on both platforms
4. IF any core module imports androidx.* or UIKit THEN the build SHALL fail with clear error message
5. WHEN implementing DI THEN the system SHALL use Koin or manual factories to avoid platform-specific DI frameworks

### Requirement 2: SQLDelight Data Persistence

**User Story:** As a user, I want my diary entries and flower collection progress to be persistently stored, so that my data is preserved across app sessions.

#### Acceptance Criteria

1. WHEN a diary entry is saved THEN it SHALL be stored in SQLDelight database with all metadata
2. WHEN the app starts THEN it SHALL automatically unlock today's birth flower in the collection
3. WHEN querying diary entries THEN the system SHALL use Unit of Work pattern for complex transactions
4. WHEN database operations fail THEN the system SHALL return Result types with proper error handling
5. WHEN migrating database schema THEN SQLDelight SHALL handle version upgrades automatically

### Requirement 3: Birth Flower Collection System

**User Story:** As a user, I want to collect birth flowers daily and view detailed information about each flower, so that I can learn about flowers throughout the year.

#### Acceptance Criteria

1. WHEN the app launches THEN today's birth flower SHALL be automatically unlocked
2. WHEN viewing the collection THEN locked flowers SHALL display as grayed out with lock icons
3. WHEN clicking an unlocked flower THEN a detailed popup SHALL show flower information, meaning, and description
4. WHEN clicking the popup background THEN the popup SHALL close without triggering inner content clicks
5. WHEN viewing flower details THEN the popup SHALL display name (Korean/English), meaning, description, and image

### Requirement 4: Diary Writing and Decoration

**User Story:** As a user, I want to write diary entries with customizable fonts and colors against birth flower backgrounds, so that I can create personalized and beautiful diary pages.

#### Acceptance Criteria

1. WHEN writing a diary entry THEN the user SHALL be able to select font family from available options
2. WHEN writing a diary entry THEN the user SHALL be able to change text color from a color palette
3. WHEN writing a diary entry THEN the background SHALL display the current day's birth flower with transparency
4. WHEN saving a diary entry THEN all formatting preferences SHALL be preserved with the entry
5. WHEN reopening a saved entry THEN all original formatting SHALL be restored exactly

### Requirement 5: Media and Loading System

**User Story:** As a user, I want engaging opening video and varied loading screens, so that the app feels polished and interesting to use.

#### Acceptance Criteria

1. WHEN the app starts THEN an opening video SHALL play with skip button appearing after 2 seconds
2. WHEN the skip button is clicked THEN the app SHALL immediately navigate to the title screen
3. WHEN loading screens are shown THEN one of 7 random background images SHALL be displayed
4. WHEN loading THEN a rotating gear animation SHALL be shown in the bottom-left corner
5. WHEN the title screen is displayed THEN clicking anywhere SHALL navigate to the main screen

### Requirement 6: Background Music System

**User Story:** As a user, I want to play background music while using the app, so that I can have a more immersive and relaxing experience.

#### Acceptance Criteria

1. WHEN the user selects BGM THEN one of 4 available tracks SHALL start playing
2. WHEN BGM is playing THEN it SHALL loop continuously until stopped or changed
3. WHEN switching between screens THEN BGM SHALL continue playing uninterrupted
4. WHEN the user changes BGM track THEN the previous track SHALL stop and new track SHALL start
5. WHEN the app is backgrounded THEN BGM SHALL continue playing according to system audio policies

### Requirement 7: Code Quality and Standards

**User Story:** As a developer, I want the codebase to follow strict quality standards, so that the code is maintainable, testable, and follows best practices.

#### Acceptance Criteria

1. WHEN building the project THEN all Detekt rules SHALL pass with zero warnings
2. WHEN writing code THEN no magic numbers or hardcoded strings SHALL be used outside of Constants files
3. WHEN handling exceptions THEN generic Exception catching SHALL be prohibited
4. WHEN implementing classes THEN each class SHALL follow Single Responsibility Principle (SRP)
5. WHEN writing methods THEN no method SHALL exceed 60 lines of code
6. WHEN creating files THEN no file SHALL exceed 200 lines of code
7. WHEN implementing features THEN Unit of Work pattern SHALL be used for multi-table transactions
8. WHEN designing classes THEN all SOLID principles SHALL be strictly followed
9. WHEN writing code THEN boilerplate code SHALL be minimized using data classes, inline functions, and extensions
10. WHEN creating abstractions THEN Open-Closed Principle (OCP) SHALL be applied for extensibility

### Requirement 8: Resource Management

**User Story:** As a developer, I want all UI dimensions, colors, and configuration values to be centrally managed, so that the app maintains visual consistency and values can be easily modified.

#### Acceptance Criteria

1. WHEN defining colors THEN all color values SHALL be declared in ColorPalette object
2. WHEN defining dimensions THEN all spacing and size values SHALL be declared in Dimens object  
3. WHEN defining configuration THEN all app constants SHALL be declared in Config object
4. WHEN using these values THEN direct hardcoded values SHALL NOT be used anywhere in the codebase
5. WHEN Detekt runs THEN MagicNumber and StringLiteralDuplication rules SHALL pass completely

### Requirement 9: Error Handling and Resilience

**User Story:** As a user, I want the app to handle errors gracefully without crashing, so that I have a stable and reliable experience.

#### Acceptance Criteria

1. WHEN any operation fails THEN the system SHALL use ExceptionUtil for consistent error handling
2. WHEN database operations fail THEN Result types SHALL be returned with meaningful error messages
3. WHEN coroutines are cancelled THEN CancellationException SHALL be properly propagated
4. WHEN network or file operations fail THEN the app SHALL continue functioning with appropriate fallbacks
5. WHEN errors occur THEN they SHALL be logged appropriately without exposing sensitive information

### Requirement 10: Pure Kotlin Implementation

**User Story:** As a developer, I want all non-UI code to be written in pure Kotlin without platform dependencies, so that the codebase remains truly platform-neutral and maintainable.

#### Acceptance Criteria

1. WHEN implementing core modules THEN only pure Kotlin language features SHALL be used
2. WHEN writing business logic THEN no Android or iOS specific APIs SHALL be imported
3. WHEN creating data models THEN only Kotlin standard library types SHALL be used
4. WHEN implementing algorithms THEN pure Kotlin functions and classes SHALL be sufficient
5. IF platform-specific functionality is needed THEN it SHALL be abstracted through expect/actual interfaces
6. WHEN reviewing code THEN any import of android.*, androidx.*, UIKit.*, or Foundation.* in core modules SHALL be rejected
7. WHEN building shared modules THEN they SHALL compile successfully for all target platforms using only Kotlin multiplatform dependencies

### Requirement 11: Build and CI Integration

**User Story:** As a developer, I want automated quality checks in the build pipeline, so that code quality standards are enforced consistently.

#### Acceptance Criteria

1. WHEN code is committed THEN GitHub Actions SHALL run Detekt and fail on any violations
2. WHEN generic Exception is used THEN CI SHALL detect and fail the build
3. WHEN building Android THEN the build SHALL complete successfully with all quality checks
4. WHEN building iOS THEN the build SHALL complete successfully with all quality checks  
5. WHEN tests are run THEN all unit tests SHALL pass for both platforms