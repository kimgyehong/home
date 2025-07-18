package com.flowerdiary.ui.ios.bridge

import com.flowerdiary.common.constants.Config
import com.flowerdiary.ui.ios.coordinator.DiaryCoordinator
import platform.Foundation.NSObject

/**
 * BGM 브릿지
 * SRP: BGM 제어 로직만 담당
 */
class BGMBridge(
    private val coordinator: DiaryCoordinator
) : NSObject {
    
    private val bgmTracks = listOf("봄", "여름", "가을", "겨울")
    
    /**
     * BGM 재생
     */
    fun playBGM(trackIndex: Int) {
        coordinator.playBGM(trackIndex)
    }
    
    /**
     * BGM 정지
     */
    fun stopBGM() {
        coordinator.stopBGM()
    }
    
    /**
     * BGM 볼륨 설정
     */
    fun setBGMVolume(volume: Float) {
        coordinator.setBGMVolume(volume)
    }
    
    /**
     * BGM 트랙 목록
     */
    fun getBGMTracks(): List<String> {
        return bgmTracks
    }
    
    /**
     * BGM 트랙 개수
     */
    fun getBGMTrackCount(): Int {
        return Config.BGM_TRACK_COUNT
    }
}
