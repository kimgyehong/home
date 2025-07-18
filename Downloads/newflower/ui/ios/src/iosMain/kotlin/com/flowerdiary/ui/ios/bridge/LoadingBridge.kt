package com.flowerdiary.ui.ios.bridge

import com.flowerdiary.common.constants.Config
import platform.Foundation.NSObject

/**
 * 로딩 브릿지
 * SRP: 로딩 화면 관련 로직만 담당
 */
class LoadingBridge : NSObject {
    
    private val loadingImages = listOf(
        "loading_01.png",
        "loading_02.png",
        "loading_03.png",
        "loading_04.png",
        "loading_05.png",
        "loading_06.png",
        "loading_07.png"
    )
    
    /**
     * 랜덤 로딩 이미지 가져오기
     */
    fun getRandomLoadingImage(): String {
        return loadingImages.random()
    }
    
    /**
     * 로딩 시간 (밀리초)
     */
    fun getLoadingDuration(): Long {
        return Config.LOADING_DELAY_MS
    }
    
    /**
     * 스킵 버튼 표시 시간 (밀리초)
     */
    fun getSkipButtonDelay(): Long {
        return Config.SKIP_BUTTON_DELAY_MS
    }
}
