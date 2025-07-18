package com.flowerdiary.platform.android

import android.content.Context
import com.flowerdiary.common.platform.AudioManager
import com.flowerdiary.common.utils.Logger

/**
 * Android MediaPlayer 기반 오디오 관리자
 * SRP: 오디오 관리 인터페이스 구현 및 하위 컴포넌트 조정만 담당
 */
class AndroidAudioManager(
    @Suppress("UnusedPrivateProperty")
    private val context: Context
) : AudioManager {

    private val mediaPlayerController = AndroidMediaPlayerController()
    private val audioFader = AndroidAudioFader()

    override fun playBGM(trackIndex: Int) {
        mediaPlayerController.play(trackIndex)
    }

    override fun stopBGM() {
        audioFader.cancelFade()
        mediaPlayerController.stop()
    }

    override fun pauseBGM() {
        mediaPlayerController.pause()
    }

    override fun resumeBGM() {
        mediaPlayerController.resume()
    }

    override fun setBGMVolume(volume: Float) {
        mediaPlayerController.setVolume(volume)
    }

    override fun fadeIn(durationMs: Long) {
        audioFader.fadeIn(mediaPlayerController.getMediaPlayer(), durationMs)
    }

    override fun fadeOut(durationMs: Long) {
        audioFader.fadeOut(mediaPlayerController.getMediaPlayer(), durationMs)
    }

    override fun isPlaying(): Boolean {
        return mediaPlayerController.isPlaying()
    }

    override fun dispose() {
        audioFader.dispose()
        mediaPlayerController.stop()
        Logger.debug(TAG, "AudioManager disposed")
    }

    companion object {
        private const val TAG = "AndroidAudioManager"
    }
}
