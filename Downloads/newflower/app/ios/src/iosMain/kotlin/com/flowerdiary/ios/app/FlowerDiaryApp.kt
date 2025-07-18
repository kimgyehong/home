package com.flowerdiary.ios.app

import com.flowerdiary.ios.di.IOSAppModule
import com.flowerdiary.ui.ios.coordinator.DiaryCoordinator
import kotlinx.coroutines.*
import platform.Foundation.NSObject

/**
 * iOS 꽃 다이어리 앱
 * SRP: iOS 앱의 생명주기 관리와 초기화만 담당
 * SwiftUI 앱에서 호출하는 메인 진입점
 */
class FlowerDiaryApp : NSObject {
    
    private val appModule = IOSAppModule()
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    private var isInitialized = false
    
    /**
     * 앱 초기화
     */
    fun initialize() {
        if (isInitialized) return
        
        try {
            appModule.initialize()
            
            // 탄생화 데이터 초기화 (비동기)
            appScope.launch {
                appModule.initializeBirthFlowerData()
            }
            
            isInitialized = true
        } catch (e: IllegalStateException) {
            // 이미 초기화된 상태에서 재시도
            println("Failed to initialize FlowerDiary app - illegal state: ${e.message}")
            throw e
        } catch (e: RuntimeException) {
            // 런타임 예외
            println("Failed to initialize FlowerDiary app - runtime error: ${e.message}")
            throw e
        } catch (e: Throwable) {
            // 기타 예외
            println("Failed to initialize FlowerDiary app - unexpected error: ${e.message}")
            throw e
        }
    }
    
    /**
     * 코디네이터 반환
     */
    fun getCoordinator(): DiaryCoordinator {
        if (!isInitialized) {
            throw IllegalStateException("App not initialized. Call initialize() first.")
        }
        return appModule.getCoordinator()
    }
    
    /**
     * 앱 종료
     */
    fun dispose() {
        if (!isInitialized) return
        
        appScope.cancel()
        appModule.dispose()
        isInitialized = false
    }
    
    /**
     * 초기화 상태 확인
     */
    fun isInitialized(): Boolean = isInitialized
}