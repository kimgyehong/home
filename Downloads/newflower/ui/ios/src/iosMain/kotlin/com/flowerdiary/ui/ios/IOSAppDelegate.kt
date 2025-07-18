package com.flowerdiary.ui.ios

import com.flowerdiary.ui.ios.coordinator.DiaryCoordinator
import platform.Foundation.NSObject

/**
 * iOS 앱 델리게이트 브릿지
 * SRP: iOS 앱 라이프사이클과 Kotlin 초기화 연결만 담당
 */
class IOSAppDelegate : NSObject {
    
    private val coordinator = DiaryCoordinator()
    
    /**
     * 앱 초기화
     */
    fun initialize() {
        // DI 초기화는 app/ios 모듈에서 처리
        // 여기서는 코디네이터만 초기화
    }
    
    /**
     * 코디네이터 반환
     */
    fun getCoordinator(): DiaryCoordinator = coordinator
    
    /**
     * 리소스 해제
     */
    fun dispose() {
        coordinator.dispose()
    }
}