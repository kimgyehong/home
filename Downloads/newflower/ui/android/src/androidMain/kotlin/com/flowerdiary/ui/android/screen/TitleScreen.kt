package com.flowerdiary.ui.android.screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flowerdiary.common.constants.ColorPalette
import com.flowerdiary.common.constants.Config
import kotlinx.coroutines.delay

/**
 * 타이틀 화면
 * SRP: 앱 타이틀 화면 표시만 담당
 * 클릭하면 메인으로 이동
 */
@Composable
fun TitleScreen(
    onNavigateToMain: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 페이드인 애니메이션
    var titleAlpha by remember { mutableStateOf(0f) }
    var subtitleAlpha by remember { mutableStateOf(0f) }
    var clickable by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        // 타이틀 페이드인
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = Config.FADE_ANIMATION_DURATION_MS,
                easing = LinearEasing
            )
        ) { value, _ ->
            titleAlpha = value
        }
        
        // 서브타이틀 페이드인
        delay(SUBTITLE_DELAY_MS.toLong())
        animate(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = Config.FADE_ANIMATION_DURATION_MS,
                easing = LinearEasing
            )
        ) { value, _ ->
            subtitleAlpha = value
        }
        
        // 클릭 가능하게 설정
        delay(CLICK_ENABLE_DELAY_MS.toLong())
        clickable = true
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(ColorPalette.Background.Primary.toInt()))
            .clickable(enabled = clickable) { onNavigateToMain() },
        contentAlignment = Alignment.Center
    ) {
        // 타이틀 배경 이미지
        // 실제 앱에서는 title2 이미지 사용
        // Image(
        //     painter = painterResource(id = R.drawable.title2),
        //     contentDescription = null,
        //     modifier = Modifier.fillMaxSize(),
        //     contentScale = ContentScale.Crop
        // )
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(CONTENT_PADDING.dp)
        ) {
            // 메인 타이틀
            Text(
                text = "꽃 다이어리",
                style = MaterialTheme.typography.displayLarge,
                color = Color(ColorPalette.Primary.Default.toInt()),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(titleAlpha)
            )
            
            Spacer(modifier = Modifier.height(TITLE_SPACING.dp))
            
            // 서브 타이틀
            Text(
                text = "매일매일 피어나는 당신의 이야기",
                style = MaterialTheme.typography.titleMedium,
                color = Color(ColorPalette.Text.Secondary.toInt()),
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(subtitleAlpha)
            )
            
            // 클릭 안내 텍스트
            if (clickable) {
                Spacer(modifier = Modifier.height(CLICK_GUIDE_SPACING.dp))
                
                val infiniteTransition = rememberInfiniteTransition(label = "click_guide")
                val alpha by infiniteTransition.animateFloat(
                    initialValue = CLICK_GUIDE_MIN_ALPHA,
                    targetValue = CLICK_GUIDE_MAX_ALPHA,
                    animationSpec = infiniteRepeatable(
                        animation = tween(CLICK_GUIDE_DURATION_MS),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "click_guide_alpha"
                )
                
                Text(
                    text = "화면을 터치하세요",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(ColorPalette.Text.Tertiary.toInt()),
                    modifier = Modifier.alpha(alpha)
                )
            }
        }
    }
}

// UI 상수들
private const val CONTENT_PADDING = 32
private const val TITLE_SPACING = 16
private const val CLICK_GUIDE_SPACING = 48
private const val SUBTITLE_DELAY_MS = 500
private const val CLICK_ENABLE_DELAY_MS = 1500
private const val CLICK_GUIDE_MIN_ALPHA = 0.3f
private const val CLICK_GUIDE_MAX_ALPHA = 1f
private const val CLICK_GUIDE_DURATION_MS = 1500