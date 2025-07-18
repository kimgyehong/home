package com.flowerdiary.common.platform

/**
 * 플랫폼별 오디오 관리자 인터페이스
 * BGM 재생 및 제어 기능 제공
 * 순수 Kotlin 인터페이스 - platform 모듈에서 구현
 */
interface AudioManager {
    fun playBGM(trackIndex: Int)
    fun stopBGM()
    fun pauseBGM()
    fun resumeBGM()
    fun setBGMVolume(volume: Float)
    fun fadeIn(durationMs: Long = 500L)
    fun fadeOut(durationMs: Long = 500L)
    fun isPlaying(): Boolean
    fun dispose()
}
