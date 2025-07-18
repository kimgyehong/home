package com.flowerdiary.platform.ios

import com.flowerdiary.common.platform.AudioManager
import com.flowerdiary.common.utils.ExceptionUtil
import kotlinx.cinterop.*
import platform.AVFoundation.*
import platform.Foundation.*
import platform.darwin.NSObject

/**
 * iOS 오디오 관리자 구현
 * SRP: iOS 플랫폼의 오디오 재생/제어만 담당
 * AVAudioPlayer를 사용하여 BGM 재생 관리
 */
actual class IOSAudioManager : AudioManager {
    
    private var audioPlayer: AVAudioPlayer? = null
    private var currentVolume: Float = 0.7f
    private var isPlaying: Boolean = false
    
    override suspend fun playBGM(trackIndex: Int): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            stopBGM()
            
            val trackName = BGM_TRACKS[trackIndex % BGM_TRACKS.size]
            val bundle = NSBundle.mainBundle
            val audioPath = bundle.pathForResource(trackName, ofType = "mp3")
                ?: throw IOSAudioException("Audio file not found: $trackName")
            
            val audioUrl = NSURL.fileURLWithPath(audioPath)
            val player = AVAudioPlayer(contentsOfURL = audioUrl, error = null)
                ?: throw IOSAudioException("Failed to create audio player")
            
            player.numberOfLoops = -1 // 무한 반복
            player.volume = currentVolume
            
            val success = player.play()
            if (success) {
                audioPlayer = player
                isPlaying = true
            } else {
                throw IOSAudioException("Failed to play audio")
            }
        }
    
    override suspend fun stopBGM(): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            audioPlayer?.stop()
            audioPlayer = null
            isPlaying = false
        }
    
    override suspend fun pauseBGM(): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            audioPlayer?.pause()
            isPlaying = false
        }
    
    override suspend fun resumeBGM(): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val success = audioPlayer?.play() ?: false
            if (success) {
                isPlaying = true
            } else {
                throw IOSAudioException("Failed to resume audio")
            }
        }
    
    override suspend fun setVolume(volume: Float): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val clampedVolume = volume.coerceIn(0.0f, 1.0f)
            currentVolume = clampedVolume
            audioPlayer?.volume = clampedVolume
        }
    
    override suspend fun getVolume(): Result<Float> =
        ExceptionUtil.runCatchingSuspend {
            currentVolume
        }
    
    override suspend fun isPlaying(): Result<Boolean> =
        ExceptionUtil.runCatchingSuspend {
            isPlaying && (audioPlayer?.isPlaying == true)
        }
    
    override suspend fun getCurrentPosition(): Result<Long> =
        ExceptionUtil.runCatchingSuspend {
            val currentTime = audioPlayer?.currentTime ?: 0.0
            (currentTime * MILLIS_PER_SECOND).toLong()
        }
    
    override suspend fun getDuration(): Result<Long> =
        ExceptionUtil.runCatchingSuspend {
            val duration = audioPlayer?.duration ?: 0.0
            (duration * MILLIS_PER_SECOND).toLong()
        }
    
    override suspend fun seekTo(position: Long): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val timeInSeconds = position.toDouble() / MILLIS_PER_SECOND
            audioPlayer?.currentTime = timeInSeconds
        }
    
    override fun dispose() {
        audioPlayer?.stop()
        audioPlayer = null
        isPlaying = false
    }
    
    companion object {
        private const val MILLIS_PER_SECOND = 1000.0
        
        private val BGM_TRACKS = arrayOf(
            "bgm_spring_waltz",
            "bgm_summer_breeze", 
            "bgm_autumn_leaves",
            "bgm_winter_snowflake"
        )
    }
}

/**
 * iOS 오디오 예외
 */
class IOSAudioException(message: String) : Exception(message)