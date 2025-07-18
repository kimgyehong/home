package com.flowerdiary.platform.android

import android.media.MediaPlayer
import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.utils.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Android 오디오 페이드 효과 클래스
 * SRP: 오디오 페이드 인/아웃 효과만 담당
 */
internal class AndroidAudioFader {

    private var fadeJob: Job? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    fun fadeIn(mediaPlayer: MediaPlayer?, durationMs: Long) {
        fadeJob?.cancel()
        fadeJob = scope.launch {
            mediaPlayer?.let { player ->
                performFadeIn(player, durationMs)
            }
        }
    }

    private suspend fun performFadeIn(player: MediaPlayer, durationMs: Long) {
        var currentVolume = 0f
        val targetVolume = Config.BGM_VOLUME_DEFAULT
        val steps = Config.BGM_FADE_STEPS
        val stepDuration = durationMs / steps
        val volumeIncrement = targetVolume / steps

        player.setVolume(0f, 0f)
        if (!player.isPlaying) {
            player.start()
        }

        repeat(steps) {
            currentVolume += volumeIncrement
            player.setVolume(currentVolume, currentVolume)
            delay(stepDuration)
        }

        player.setVolume(targetVolume, targetVolume)
        Logger.debug(TAG, "BGM fade in completed")
    }

    fun fadeOut(mediaPlayer: MediaPlayer?, durationMs: Long) {
        fadeJob?.cancel()
        fadeJob = scope.launch {
            mediaPlayer?.let { player ->
                performFadeOut(player, durationMs)
            }
        }
    }

    private suspend fun performFadeOut(player: MediaPlayer, durationMs: Long) {
        var currentVolume = Config.BGM_VOLUME_DEFAULT
        val steps = Config.BGM_FADE_STEPS
        val stepDuration = durationMs / steps
        val volumeDecrement = currentVolume / steps

        repeat(steps) {
            currentVolume -= volumeDecrement
            player.setVolume(currentVolume, currentVolume)
            delay(stepDuration)
        }

        player.setVolume(0f, 0f)
        player.pause()
        Logger.debug(TAG, "BGM fade out completed")
    }

    fun cancelFade() {
        fadeJob?.cancel()
        fadeJob = null
    }

    fun dispose() {
        fadeJob?.cancel()
        fadeJob = null
        scope.cancel()
        Logger.debug(TAG, "AudioFader disposed")
    }

    companion object {
        private const val TAG = "AndroidAudioFader"
    }
}
