package com.flowerdiary.ui.ios.bridge

import platform.Foundation.NSObject

/**
 * 앱 상태 브릿지
 * SRP: 앱 전체 상태 관리만 담당
 */
class AppStateBridge : NSObject {
    
    companion object {
        private const val DEFAULT_SCREEN = "opening"
    }
    
    private var _isLoading: Boolean = false
    private var _currentScreen: String = DEFAULT_SCREEN
    private var _showSkipButton: Boolean = false
    
    /**
     * 로딩 상태
     */
    val isLoading: Boolean
        get() = _isLoading
    
    /**
     * 현재 화면
     */
    val currentScreen: String
        get() = _currentScreen
    
    /**
     * 스킵 버튼 표시 여부
     */
    val showSkipButton: Boolean
        get() = _showSkipButton
    
    /**
     * 로딩 상태 설정
     */
    fun setLoading(loading: Boolean) {
        _isLoading = loading
    }
    
    /**
     * 현재 화면 설정
     */
    fun setCurrentScreen(screen: String) {
        _currentScreen = screen
    }
    
    /**
     * 스킵 버튼 표시 설정
     */
    fun setShowSkipButton(show: Boolean) {
        _showSkipButton = show
    }
}
