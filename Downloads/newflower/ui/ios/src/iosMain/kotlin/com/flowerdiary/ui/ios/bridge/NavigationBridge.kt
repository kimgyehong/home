package com.flowerdiary.ui.ios.bridge

import com.flowerdiary.ui.ios.coordinator.DiaryCoordinator
import platform.Foundation.NSObject

/**
 * 네비게이션 브릿지
 * SRP: SwiftUI 네비게이션을 위한 라우팅 로직만 담당
 */
class NavigationBridge(
    private val coordinator: DiaryCoordinator
) : NSObject {
    
    /**
     * 메인 화면으로 이동
     */
    fun navigateToMain() {
        coordinator.navigateToMain()
    }
    
    /**
     * 일기 작성 화면으로 이동
     */
    fun navigateToNewDiary() {
        coordinator.navigateToNewDiary()
    }
    
    /**
     * 일기 편집 화면으로 이동
     */
    fun navigateToEditDiary(diaryId: String) {
        coordinator.navigateToEditDiary(diaryId)
    }
    
    /**
     * 도감 화면으로 이동
     */
    fun navigateToCollection() {
        coordinator.navigateToCollection()
    }
    
    /**
     * 설정 화면으로 이동
     */
    fun navigateToSettings() {
        coordinator.navigateToSettings()
    }
    
    /**
     * 뒤로 가기
     */
    fun navigateBack() {
        coordinator.navigateBack()
    }
    
    /**
     * 오프닝 화면 스킵
     */
    fun skipOpening() {
        coordinator.skipOpening()
    }
}
