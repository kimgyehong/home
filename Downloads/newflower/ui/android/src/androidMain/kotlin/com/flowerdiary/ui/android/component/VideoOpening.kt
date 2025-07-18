package com.flowerdiary.ui.android.component

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.flowerdiary.common.constants.ColorPalette

/**
 * 비디오 오프닝 화면
 * SRP: 오프닝 영상 재생만 담당
 * 스킵 가능한 인트로 영상
 */
@Composable
fun VideoOpening(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    // ExoPlayer 인스턴스
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            // 비디오 파일 설정
            val mediaItem = MediaItem.fromUri("asset:///opening.mp4")
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            
            // 재생 완료 리스너
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        onComplete()
                    }
                }
            })
        }
    }
    
    // 생명주기 관리
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // 비디오 플레이어
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false // 컨트롤러 숨김
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        
        // 스킵 버튼
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    enabled = true,
                    onClick = {
                        exoPlayer.stop()
                        onComplete()
                    }
                ),
            contentAlignment = Alignment.TopEnd
        ) {
            TextButton(
                onClick = {
                    exoPlayer.stop()
                    onComplete()
                },
                modifier = Modifier
                    .padding(SKIP_BUTTON_PADDING.dp)
                    .background(
                        Color(ColorPalette.Background.Overlay.toInt()),
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Text(
                    text = "스킵 >>",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

// UI 상수들
private const val SKIP_BUTTON_PADDING = 16