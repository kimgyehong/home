package com.flowerdiary.ios.di

import com.flowerdiary.common.data.UnitOfWork
import com.flowerdiary.common.platform.*
import com.flowerdiary.common.utils.Logger
import com.flowerdiary.data.database.BirthFlowerDatabaseInitializer
import com.flowerdiary.data.database.init.*
import com.flowerdiary.data.repository.SqlDelightBirthFlowerRepository
import com.flowerdiary.data.repository.SqlDelightDiaryRepository
import com.flowerdiary.data.SqlDelightUnitOfWork
import com.flowerdiary.domain.repository.BirthFlowerRepository
import com.flowerdiary.domain.repository.DiaryRepository
import com.flowerdiary.domain.usecase.diary.*
import com.flowerdiary.domain.usecase.flower.*
import com.flowerdiary.feature.diary.facade.DiaryFacade
import com.flowerdiary.feature.diary.viewmodel.*
import com.flowerdiary.platform.ios.*
import com.flowerdiary.ui.ios.coordinator.DiaryCoordinator
import platform.Foundation.NSObject

/**
 * iOS 앱 DI 컨테이너
 * SRP: iOS 앱의 의존성 그래프 관리만 담당
 * 모든 의존성을 수동으로 생성하고 관리
 */
class IOSAppModule : NSObject {
    
    // Platform implementations
    private val databaseDriverProvider: DatabaseDriverProvider = IOSDatabaseDriverProvider()
    private val fileStore: FileStore = IOSFileStore()
    private val preferencesStore: PreferencesStore = IOSPreferencesStore()
    private val audioManager: AudioManager = IOSAudioManager()
    private val videoPlayer: VideoPlayer = IOSVideoPlayer()
    
    // Data layer
    private val unitOfWork: UnitOfWork = SqlDelightUnitOfWork(databaseDriverProvider)
    private val diaryRepository: DiaryRepository = SqlDelightDiaryRepository(databaseDriverProvider)
    private val birthFlowerRepository: BirthFlowerRepository = SqlDelightBirthFlowerRepository(databaseDriverProvider)
    
    // Use cases
    private val getDiariesUseCase = GetDiariesUseCase(diaryRepository)
    private val getDiaryUseCase = GetDiaryUseCase(diaryRepository)
    private val saveDiaryUseCase = SaveDiaryUseCase(diaryRepository)
    private val deleteDiaryUseCase = DeleteDiaryUseCase(diaryRepository)
    private val searchDiariesUseCase = SearchDiariesUseCase(diaryRepository)
    
    private val getBirthFlowerUseCase = GetBirthFlowerUseCase(birthFlowerRepository)
    private val getBirthFlowersUseCase = GetBirthFlowersUseCase(birthFlowerRepository)
    private val unlockBirthFlowerUseCase = UnlockBirthFlowerUseCase(birthFlowerRepository)
    private val recommendFlowerUseCase = RecommendFlowerUseCase(birthFlowerRepository)
    
    // Facade
    private val diaryFacade = DiaryFacade(
        unitOfWork = unitOfWork,
        diaryRepository = diaryRepository,
        birthFlowerRepository = birthFlowerRepository,
        saveDiaryUseCase = saveDiaryUseCase,
        unlockBirthFlowerUseCase = unlockBirthFlowerUseCase
    )
    
    // ViewModels
    private val diaryListViewModel = DiaryListViewModel(
        getDiariesUseCase = getDiariesUseCase,
        deleteDiaryUseCase = deleteDiaryUseCase
    )
    
    private val diaryEditorViewModel = DiaryEditorViewModel(
        getDiaryUseCase = getDiaryUseCase,
        saveDiaryUseCase = saveDiaryUseCase,
        getBirthFlowerUseCase = getBirthFlowerUseCase,
        recommendFlowerUseCase = recommendFlowerUseCase
    )
    
    private val collectionViewModel = CollectionViewModel(
        getBirthFlowerUseCase = getBirthFlowerUseCase,
        unlockBirthFlowerUseCase = unlockBirthFlowerUseCase,
        birthFlowerRepository = birthFlowerRepository
    )
    
    private val settingsViewModel = SettingsViewModel(
        preferencesStore = preferencesStore,
        audioManager = audioManager,
        fileStore = fileStore
    )
    
    // Database initializer
    private val birthFlowerInitializer = BirthFlowerDatabaseInitializer(
        parser = BirthFlowerDataParser(),
        validator = BirthFlowerValidator(),
        mapper = BirthFlowerEntryMapper(
            colorProvider = BirthFlowerColorProvider(),
            imagePathProvider = BirthFlowerImagePathProvider()
        ),
        persistenceService = BirthFlowerPersistenceService(birthFlowerRepository)
    )
    
    // UI Coordinator
    private val coordinator = DiaryCoordinator()
    
    /**
     * 앱 초기화
     */
    fun initialize() {
        // 코디네이터에 ViewModels 설정
        coordinator.initializeViewModels(
            diaryListVM = diaryListViewModel,
            diaryEditorVM = diaryEditorViewModel,
            collectionVM = collectionViewModel,
            settingsVM = settingsViewModel
        )
        
        Logger.info("IOSAppModule", "iOS app initialized successfully")
    }
    
    /**
     * 코디네이터 반환
     */
    fun getCoordinator(): DiaryCoordinator = coordinator
    
    /**
     * 탄생화 데이터 초기화
     */
    suspend fun initializeBirthFlowerData() {
        val isInitialized = preferencesStore.getBoolean(KEY_BIRTH_FLOWER_INITIALIZED, false)
            .getOrDefault(false)
        
        if (!isInitialized) {
            birthFlowerInitializer.initialize()
            preferencesStore.putBoolean(KEY_BIRTH_FLOWER_INITIALIZED, true)
        }
    }
    
    /**
     * 리소스 해제
     */
    fun dispose() {
        coordinator.dispose()
        Logger.info("IOSAppModule", "iOS app disposed")
    }
    
    companion object {
        private const val KEY_BIRTH_FLOWER_INITIALIZED = "birth_flower_initialized"
    }
}