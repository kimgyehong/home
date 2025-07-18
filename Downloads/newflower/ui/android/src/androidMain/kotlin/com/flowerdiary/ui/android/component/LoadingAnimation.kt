package com.flowerdiary.ui.android.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.flowerdiary.common.constants.Config
import kotlin.random.Random

/**
 * 로딩 애니메이션 화면
 * SRP: 로딩 화면 표시만 담당
 * 7개 이미지 중 랜덤 + 톱니바퀴 회전 애니메이션
 */
@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    loadingText: String = "불러오는 중..."
) {
    // 7개 이미지 중 랜덤 선택 (세션 중 고정)
    val randomImageIndex = remember { 
        Random.nextInt(1, LOADING_IMAGE_COUNT + 1) 
    }
    
    // 톱니바퀴 회전 애니메이션
    val infiniteTransition = rememberInfiniteTransition(label = "gear_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = Config.LOADING_ANIMATION_DURATION_MS,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "gear_rotation_value"
    )
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 랜덤 배경 이미지
            Box(
                modifier = Modifier
                    .size(LOADING_IMAGE_SIZE.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                // 실제 앱에서는 drawable 리소스 사용
                // Image(
                //     painter = painterResource(id = getLoadingImage(randomImageIndex)),
                //     contentDescription = null,
                //     modifier = Modifier.fillMaxSize(),
                //     contentScale = ContentScale.Crop
                // )
                
                // 임시 텍스트 표시
                Text(
                    text = "Loading $randomImageIndex",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(SPACING.dp))
            
            // 톱니바퀴 아이콘 (회전)
            Box(
                modifier = Modifier
                    .size(GEAR_SIZE.dp)
                    .rotate(rotation),
                contentAlignment = Alignment.Center
            ) {
                // 실제 앱에서는 톱니바퀴 아이콘 사용
                // Icon(
                //     imageVector = Icons.Default.Settings,
                //     contentDescription = null,
                //     tint = MaterialTheme.colorScheme.primary,
                //     modifier = Modifier.fillMaxSize()
                // )
                
                // 임시 표시
                Text(
                    text = "⚙",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(SMALL_SPACING.dp))
            
            // 로딩 텍스트
            Text(
                text = loadingText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * 로딩 이미지 리소스 ID 반환
 */
private fun getLoadingImage(index: Int): Int {
    // 실제 앱에서는 drawable 리소스 ID 반환
    // return when (index) {
    //     1 -> R.drawable.loading_1
    //     2 -> R.drawable.loading_2
    //     3 -> R.drawable.loading_3
    //     4 -> R.drawable.loading_4
    //     5 -> R.drawable.loading_5
    //     6 -> R.drawable.loading_6
    //     7 -> R.drawable.loading_7
    //     else -> R.drawable.loading_1
    // }
    return 0 // 임시
}

// UI 상수들
private const val LOADING_IMAGE_COUNT = 7
private const val LOADING_IMAGE_SIZE = 200
private const val GEAR_SIZE = 48
private const val SPACING = 32
private const val SMALL_SPACING = 16