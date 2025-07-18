package com.flowerdiary.platform.android

import android.media.MediaPlayer
import com.flowerdiary.common.utils.Logger

/**
 * Android MediaPlayer 제어 클래스
 * SRP: MediaPlayer 기본 재생 제어만 담당
 */
internal class AndroidMediaPlayerController {

    private var mediaPlayer: MediaPlayer? = null
    private var currentTrackIndex: Int = -1
    private val helper = AndroidMediaPlayerHelper()

    private val bgmResources = listOf<Int>(
        // BGM 리소스는 별도로 관리
    )

    fun play(trackIndex: Int): Boolean {
        if (trackIndex !in bgmResources.indices) {
            Logger.error(TAG, "Invalid track index: $trackIndex")
            return false
        }

        return try {
            stop()
            mediaPlayer = helper.createAndConfigurePlayer().apply {
                start()
            }
            currentTrackIndex = trackIndex
            Logger.info(TAG, "BGM started: track $trackIndex")
            true
        } catch (e: IllegalStateException) {
            helper.handlePlaybackError(e)
            false
        } catch (e: IllegalArgumentException) {
            helper.handlePlaybackError(e)
            false
        } catch (e: SecurityException) {
            helper.handlePlaybackError(e)
            false
        }
    }

    fun stop() {
        val player = mediaPlayer ?: return

        try {
            if (player.isPlaying) {
                player.stop()
            }
            player.release()
        } catch (e: IllegalStateException) {
            helper.handleStopError(e)
        }

        mediaPlayer = null
        currentTrackIndex = -1
        Logger.debug(TAG, "BGM stopped")
    }

    fun pause() {
        mediaPlayer?.apply {
            if (isPlaying) {
                pause()
                Logger.debug(TAG, "BGM paused")
            }
        }
    }

    fun resume() {
        mediaPlayer?.apply {
            if (!isPlaying) {
                start()
                Logger.debug(TAG, "BGM resumed")
            }
        }
    }

    fun setVolume(volume: Float) {
        val clampedVolume = helper.validateVolume(volume)
        mediaPlayer?.setVolume(clampedVolume, clampedVolume)
        Logger.debug(TAG, "BGM volume set to: $clampedVolume")
    }

    fun isPlaying(): Boolean = mediaPlayer?.isPlaying ?: false

    fun getCurrentTrackIndex(): Int = currentTrackIndex

    companion object {
        private const val TAG = "AndroidMediaPlayerController"
    }
}
