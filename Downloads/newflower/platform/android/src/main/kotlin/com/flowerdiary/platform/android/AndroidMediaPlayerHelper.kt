package com.flowerdiary.platform.android

import android.media.MediaPlayer
import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.utils.Logger

/**
 * Android MediaPlayer 헬퍼 클래스
 * SRP: MediaPlayer 생성과 에러 처리만 담당
 */
internal class AndroidMediaPlayerHelper {

    private companion object {
        private const val TAG = "AndroidMediaPlayerHelper"
    }

    /**
     * MediaPlayer 생성 및 설정
     */
    fun createAndConfigurePlayer(): MediaPlayer {
        return MediaPlayer().apply {
            isLooping = true
            setVolume(Config.BGM_VOLUME_DEFAULT, Config.BGM_VOLUME_DEFAULT)
        }
    }

    /**
     * 재생 에러 처리
     */
    fun handlePlaybackError(e: Exception) {
        val errorMsg = when (e) {
            is IllegalStateException -> "illegal state"
            is IllegalArgumentException -> "invalid argument"
            is SecurityException -> "security error"
            else -> "unknown error"
        }
        Logger.error(TAG, "Failed to play BGM - $errorMsg", e)
    }

    /**
     * 정지 에러 처리
     */
    fun handleStopError(e: IllegalStateException) {
        Logger.error(TAG, "Error stopping BGM - illegal state", e)
    }

    /**
     * 볼륨 값 검증
     */
    fun validateVolume(volume: Float): Float {
        return volume.coerceIn(0f, 1f)
    }
}
