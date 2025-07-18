package com.flowerdiary.ui.ios.coordinator

import com.flowerdiary.feature.diary.state.*
import com.flowerdiary.feature.diary.viewmodel.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import platform.Foundation.NSObject

/**
 * iOS 일기 UI 코디네이터
 * SRP: SwiftUI View와 Kotlin ViewModel 연결만 담당
 * ObservableObject 프로토콜을 준수하여 SwiftUI와 통신
 */
class DiaryCoordinator : NSObject {
    
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    // ViewModels
    private var diaryListViewModel: DiaryListViewModel? = null
    private var diaryEditorViewModel: DiaryEditorViewModel? = null
    private var collectionViewModel: CollectionViewModel? = null
    private var settingsViewModel: SettingsViewModel? = null
    
    // State observers
    private val _diaryListState = MutableStateFlow(DiaryListState())
    val diaryListState: StateFlow<DiaryListState> = _diaryListState.asStateFlow()
    
    private val _diaryEditorState = MutableStateFlow(DiaryEditorState())
    val diaryEditorState: StateFlow<DiaryEditorState> = _diaryEditorState.asStateFlow()
    
    private val _collectionState = MutableStateFlow(CollectionState())
    val collectionState: StateFlow<CollectionState> = _collectionState.asStateFlow()
    
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()
    
    /**
     * ViewModels 초기화
     */
    fun initializeViewModels(
        diaryListVM: DiaryListViewModel,
        diaryEditorVM: DiaryEditorViewModel,
        collectionVM: CollectionViewModel,
        settingsVM: SettingsViewModel
    ) {
        this.diaryListViewModel = diaryListVM
        this.diaryEditorViewModel = diaryEditorVM
        this.collectionViewModel = collectionVM
        this.settingsViewModel = settingsVM
        
        // State 관찰 시작
        observeStates()
    }
    
    /**
     * 상태 관찰 시작
     */
    private fun observeStates() {
        // Diary List State
        diaryListViewModel?.state?.let { state ->
            scope.launch {
                state.collect { _diaryListState.value = it }
            }
        }
        
        // Diary Editor State
        diaryEditorViewModel?.state?.let { state ->
            scope.launch {
                state.collect { _diaryEditorState.value = it }
            }
        }
        
        // Collection State
        collectionViewModel?.state?.let { state ->
            scope.launch {
                state.collect { _collectionState.value = it }
            }
        }
        
        // Settings State
        settingsViewModel?.state?.let { state ->
            scope.launch {
                state.collect { _settingsState.value = it }
            }
        }
    }
    
    /**
     * 일기 목록 관련 액션
     */
    fun handleDiaryListIntent(intent: DiaryListIntent) {
        diaryListViewModel?.handleIntent(intent)
    }
    
    /**
     * 일기 편집 관련 액션
     */
    fun handleDiaryEditorIntent(intent: DiaryEditorIntent) {
        diaryEditorViewModel?.handleIntent(intent)
    }
    
    /**
     * 도감 관련 액션
     */
    fun handleCollectionIntent(intent: CollectionIntent) {
        collectionViewModel?.handleIntent(intent)
    }
    
    /**
     * 설정 관련 액션
     */
    fun handleSettingsIntent(intent: SettingsIntent) {
        settingsViewModel?.handleIntent(intent)
    }
    
    /**
     * 리소스 해제
     */
    fun dispose() {
        scope.cancel()
        diaryListViewModel = null
        diaryEditorViewModel = null
        collectionViewModel = null
        settingsViewModel = null
    }
}