# Implementation Plan

- [x] 1. Set up core common module with platform abstractions (Pure Kotlin Only)

  - Create expect interfaces for DatabaseDriverProvider, AudioManager, and FileManager using only pure Kotlin types
  - Implement Constants, ColorPalette, and FlowerDiaryDimens objects with all required values (zero hardcoding)
  - Create ExceptionUtil with suspend-safe error handling that preserves CancellationException (SOLID compliant)
  - Implement Result sealed class for operation results (boilerplate minimization)
  - Create LogicalPx value class for dimension management (SRP compliance)
  - Ensure no Android/iOS dependencies are imported in any core common code
  - Apply Detekt rules and verify zero violations
  - _Requirements: 1.1, 1.2, 7.1, 7.2, 7.4, 7.8, 7.9, 8.1, 8.2, 8.3, 10.1, 10.2_


- [ ] 2. Implement domain entities and value objects (Pure Kotlin Only)
  - Create DiaryId and FlowerId value classes with proper validation using only Kotlin standard library
  - Implement Diary entity with all required fields and validation methods (SRP compliance)
  - Create BirthFlower entity with unlock status and display properties (SOLID principles)
  - Implement AppSettings entity for user preferences (boilerplate minimization with data class)
  - Add domain validation rules for title length, content length, and date constraints (zero hardcoding)
  - Ensure all entities use only pure Kotlin types without platform dependencies
  - Apply Detekt rules and verify compliance
  - _Requirements: 2.1, 4.4, 7.4, 7.8, 7.9, 9.4, 10.1, 10.2_


- [ ] 3. Create repository interfaces in domain layer (Pure Kotlin Only)
  - Define DiaryRepository interface with CRUD operations returning Result types (SRP compliance)
  - Create BirthFlowerRepository interface with collection and unlock methods (SOLID principles)
  - Implement AppSettingsRepository interface for user preferences (Interface Segregation)
  - Define UnitOfWork interface for transaction management (Dependency Inversion)
  - Add repository method signatures that use suspend functions and Result types (boilerplate minimization)
  - Ensure all interfaces use only pure Kotlin types without platform dependencies
  - Apply Detekt rules and verify zero violations

  - _Requirements: 2.3, 2.4, 7.4, 7.8, 7.9, 9.1, 9.2, 10.1, 10.2_

- [ ] 4. Implement core use cases (Pure Kotlin Only)
  - Create SaveDiaryUseCase with validation and repository interaction (SRP compliance)
  - Implement GetDiaryUseCase for retrieving diary entries by ID and date (SOLID principles)
  - Create UnlockTodayFlowerUseCase that automatically unlocks current day's flower (boilerplate minimization)
  - Implement GetFlowerCollectionUseCase for retrieving all flowers with unlock status (zero hardcoding)
  - Add GetTodayFlowerUseCase for current day's flower information (Dependency Inversion)
  - Create UpdateAppSettingsUseCase for BGM and preference management (Open-Closed Principle)
  - Ensure all use cases use only pure Kotlin types without platform dependencies

  - Apply Detekt rules and verify compliance
  - _Requirements: 2.1, 3.1, 4.4, 6.1, 6.2, 7.4, 7.8, 7.9, 10.1, 10.2_

- [ ] 5. Set up SQLDelight database schema
  - Create BirthFlower.sq with complete schema including unlock status
  - Implement Diary.sq with foreign key relationship to BirthFlower
  - Create AppSettings.sq for user preferences storage
  - Add all required SQL queries for CRUD operations

  - Implement database indexes for performance optimization
  - Create initial data insertion queries for 365 birth flowers
  - _Requirements: 2.1, 2.2, 3.2, 3.3, 6.1_

- [ ] 6. Implement SQLDelight repository implementations
  - Create SqlDelightDiaryRepository with all CRUD operations using ExceptionUtil
  - Implement SqlDelightBirthFlowerRepository with collection management

  - Create SqlDelightAppSettingsRepository for preferences
  - Implement proper domain-to-database model mapping functions
  - Add error handling for all database operations using Result types
  - _Requirements: 2.1, 2.4, 7.4, 9.1, 9.2_

- [ ] 7. Implement Unit of Work pattern
  - Create SqlDelightUnitOfWork class with transaction support


  - Implement transaction method that handles rollback on errors
  - Add proper error handling and Result type returns
  - Create integration with repository classes for complex operations
  - Test transaction behavior with multiple repository operations
  - _Requirements: 2.3, 7.4, 9.1, 9.2_

- [ ] 8. Create platform-specific implementations for Android
  - Implement AndroidDatabaseDriverProvider using AndroidSqliteDriver
  - Create AndroidAudioManager with MediaPlayer for BGM functionality
  - Implement AndroidFileManager for image and video file operations
  - Add proper resource management and cleanup in all implementations
  - Create Android-specific DI module configuration
  - _Requirements: 1.1, 1.5, 5.1, 6.1, 6.3_

- [ ] 9. Create platform-specific implementations for iOS
  - Implement IosDatabaseDriverProvider using NativeSqliteDriver
  - Create IosAudioManager with AVAudioPlayer for BGM functionality
  - Implement IosFileManager for image and video file operations
  - Add proper resource management and cleanup in all implementations
  - Create iOS-specific DI module configuration
  - _Requirements: 1.1, 1.5, 5.1, 6.1, 6.3_

- [ ] 10. Implement feature layer ViewModels
  - Create DiaryViewModel with state management for diary editing
  - Implement CollectionViewModel for birth flower collection display
  - Create OpeningViewModel for video playback and skip functionality
  - Implement SettingsViewModel for BGM and app preferences
  - Add LoadingViewModel for random loading screen management
  - Create proper StateFlow management and coroutine handling in all ViewModels
  - _Requirements: 3.4, 4.1, 4.2, 5.2, 5.3, 6.2_

- [ ] 11. Create diary writing and decoration features
  - Implement font family selection functionality in DiaryViewModel
  - Create color picker integration for text color customization
  - Add background flower image display with transparency
  - Implement formatting persistence when saving diary entries
  - Create formatting restoration when loading saved entries
  - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5_

- [ ] 12. Implement birth flower collection system
  - Create automatic daily flower unlocking on app launch
  - Implement collection grid display with lock/unlock states
  - Add flower detail popup with transparent background click handling
  - Create flower information display with Korean/English names, meaning, and description
  - Implement proper popup dismissal without triggering inner content clicks
  - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5_

- [ ] 13. Implement media and loading systems
  - Create opening video playback with skip button after 2 seconds
  - Implement random loading screen selection from 7 available images
  - Add rotating gear animation for loading indicator
  - Create title screen with click-anywhere navigation
  - Implement proper video resource management and cleanup
  - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5_

- [ ] 14. Implement BGM system
  - Create BGM track selection and playback functionality
  - Implement continuous looping and cross-screen persistence
  - Add volume control and track switching capabilities
  - Create background audio continuation when app is backgrounded
  - Implement proper audio session management for both platforms
  - _Requirements: 6.1, 6.2, 6.3, 6.4, 6.5_

- [ ] 15. Set up Detekt configuration and quality checks




  - Configure detekt.yml with all required rules and thresholds
  - Set up MagicNumber detection with appropriate exceptions
  - Configure TooGenericExceptionCaught rule to prevent generic exception handling
  - Add LongMethod and LongClass rules with specified limits
  - Create StringLiteralDuplication detection for hardcoded strings
  - _Requirements: 7.1, 7.2, 7.3, 7.5, 7.6, 8.4_

- [ ] 16. Implement comprehensive error handling
  - Replace all generic exception handling with ExceptionUtil patterns
  - Add meaningful error messages for all failure scenarios
  - Implement proper CancellationException propagation in coroutines
  - Create fallback mechanisms for file and network operations
  - Add appropriate error logging without exposing sensitive information
  - _Requirements: 9.1, 9.2, 9.3, 9.4, 9.5_

- [ ] 17. Create Android UI implementation
  - Implement opening video activity with skip functionality
  - Create title screen activity with click navigation
  - Build diary editor screen with font and color customization
  - Implement collection grid screen with flower cards and detail popups
  - Add loading screens with random backgrounds and gear animation
  - Create settings screen for BGM selection and volume control
  - _Requirements: 3.3, 3.4, 4.1, 4.2, 5.1, 5.3, 6.2_

- [ ] 18. Create iOS UI implementation
  - Implement opening video view with skip functionality using SwiftUI
  - Create title screen view with tap gesture navigation
  - Build diary editor view with font and color customization
  - Implement collection grid view with flower cards and detail popups
  - Add loading screens with random backgrounds and rotation animation
  - Create settings view for BGM selection and volume control
  - _Requirements: 3.3, 3.4, 4.1, 4.2, 5.1, 5.3, 6.2_

- [ ] 19. Set up dependency injection
  - Configure Koin modules for all layers (data, domain, feature)
  - Create platform-specific modules for Android and iOS
  - Implement proper dependency scoping and lifecycle management
  - Add DI integration in Android Application class and iOS app initialization
  - Create test modules for unit and integration testing
  - _Requirements: 1.5, 7.4_

- [ ] 20. Implement comprehensive testing
  - Create unit tests for all use cases with mock repositories
  - Add repository implementation tests with in-memory database
  - Implement ViewModel tests with test coroutine dispatchers
  - Create integration tests for cross-platform database operations
  - Add UI tests for critical user flows on both platforms
  - _Requirements: 7.4, 9.1, 9.2_

- [ ] 21. Set up CI/CD pipeline
  - Configure GitHub Actions workflow for Detekt quality checks
  - Add automated testing for both Android and iOS platforms
  - Implement build verification for both platform targets
  - Create quality gate that fails on any Detekt violations
  - Add generic exception detection in CI pipeline
  - _Requirements: 10.1, 10.2, 10.3, 10.4, 10.5_

- [ ] 22. Initialize birth flower data
  - Create data insertion script for all 365 birth flowers
  - Implement Korean and English names, meanings, and descriptions
  - Add appropriate background colors and image references for each flower
  - Create database migration for initial data population
  - Test data integrity and completeness across both platforms
  - _Requirements: 2.2, 3.1, 3.5_

- [ ] 23. Implement resource management and optimization
  - Add image caching system with size limits for flower images
  - Implement proper memory management for ViewModels and coroutines
  - Create resource cleanup mechanisms in platform implementations
  - Add lazy loading for non-critical components during app startup
  - Optimize database queries with proper indexing and batch operations
  - _Requirements: 2.1, 9.4_

- [ ] 24. Final integration and testing
  - Test complete user flows from opening video to diary saving
  - Verify birth flower unlocking and collection functionality
  - Test BGM system across all screens and app states
  - Validate formatting persistence and restoration in diary entries
  - Perform cross-platform compatibility testing
  - Run full Detekt analysis and fix any remaining violations
  - _Requirements: All requirements integration testing_