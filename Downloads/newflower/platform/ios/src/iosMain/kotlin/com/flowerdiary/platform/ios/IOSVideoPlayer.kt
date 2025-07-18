package com.flowerdiary.platform.ios

import com.flowerdiary.common.platform.VideoPlayer
import com.flowerdiary.common.utils.ExceptionUtil
import kotlinx.cinterop.*
import platform.AVFoundation.*
import platform.Foundation.*
import platform.darwin.NSObject

/**
 * iOS 비디오 플레이어 구현
 * SRP: iOS 플랫폼의 비디오 재생/제어만 담당
 * AVPlayer를 사용하여 오프닝 비디오 재생
 */
actual class IOSVideoPlayer : VideoPlayer {
    
    private var avPlayer: AVPlayer? = null
    private var playerLayer: AVPlayerLayer? = null
    private var isPlaying: Boolean = false
    
    override suspend fun prepare(videoPath: String): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            stop()
            
            val bundle = NSBundle.mainBundle
            val videoUrl = if (videoPath.startsWith("asset://")) {
                // 앱 번들에서 파일 로드
                val fileName = videoPath.removePrefix("asset://").removeSuffix(".mp4")
                val filePath = bundle.pathForResource(fileName, ofType = "mp4")
                    ?: throw IOSVideoPlayerException("Video file not found: $fileName")
                NSURL.fileURLWithPath(filePath)
            } else {
                // 외부 URL
                NSURL.URLWithString(videoPath)
                    ?: throw IOSVideoPlayerException("Invalid video URL: $videoPath")
            }
            
            val playerItem = AVPlayerItem.playerItemWithURL(videoUrl)
            val player = AVPlayer.playerWithPlayerItem(playerItem)
            
            avPlayer = player
            playerLayer = AVPlayerLayer.playerLayerWithPlayer(player)
        }
    
    override suspend fun play(): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val player = avPlayer ?: throw IOSVideoPlayerException("Player not prepared")
            player.play()
            isPlaying = true
        }
    
    override suspend fun pause(): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            avPlayer?.pause()
            isPlaying = false
        }
    
    override suspend fun stop(): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            avPlayer?.pause()
            avPlayer?.seekToTime(kCMTimeZero)
            avPlayer = null
            playerLayer = null
            isPlaying = false
        }
    
    override suspend fun seekTo(position: Long): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val player = avPlayer ?: throw IOSVideoPlayerException("Player not prepared")
            val timeInSeconds = position.toDouble() / MILLIS_PER_SECOND
            val cmTime = CMTimeMakeWithSeconds(timeInSeconds, timescale = 1000)
            player.seekToTime(cmTime)
        }
    
    override suspend fun getCurrentPosition(): Result<Long> =
        ExceptionUtil.runCatchingSuspend {
            val player = avPlayer ?: throw IOSVideoPlayerException("Player not prepared")
            val currentTime = CMTimeGetSeconds(player.currentTime())
            (currentTime * MILLIS_PER_SECOND).toLong()
        }
    
    override suspend fun getDuration(): Result<Long> =
        ExceptionUtil.runCatchingSuspend {
            val player = avPlayer ?: throw IOSVideoPlayerException("Player not prepared")
            val playerItem = player.currentItem
                ?: throw IOSVideoPlayerException("No current item")
            
            val duration = CMTimeGetSeconds(playerItem.duration)
            (duration * MILLIS_PER_SECOND).toLong()
        }
    
    override suspend fun isPlaying(): Result<Boolean> =
        ExceptionUtil.runCatchingSuspend {
            isPlaying && (avPlayer?.rate ?: 0.0f) > 0.0f
        }
    
    override suspend fun setVolume(volume: Float): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val player = avPlayer ?: throw IOSVideoPlayerException("Player not prepared")
            player.volume = volume.coerceIn(0.0f, 1.0f)
        }
    
    override suspend fun getVolume(): Result<Float> =
        ExceptionUtil.runCatchingSuspend {
            val player = avPlayer ?: throw IOSVideoPlayerException("Player not prepared")
            player.volume
        }
    
    override suspend fun setPlaybackSpeed(speed: Float): Result<Unit> =
        ExceptionUtil.runCatchingSuspend {
            val player = avPlayer ?: throw IOSVideoPlayerException("Player not prepared")
            player.rate = speed
        }
    
    override suspend fun getPlaybackSpeed(): Result<Float> =
        ExceptionUtil.runCatchingSuspend {
            val player = avPlayer ?: throw IOSVideoPlayerException("Player not prepared")
            player.rate
        }
    
    /**
     * AVPlayerLayer 반환 (SwiftUI 통합용)
     */
    fun getPlayerLayer(): AVPlayerLayer? = playerLayer
    
    companion object {
        private const val MILLIS_PER_SECOND = 1000.0
    }
}

/**
 * iOS 비디오 플레이어 예외
 */
class IOSVideoPlayerException(message: String) : Exception(message)