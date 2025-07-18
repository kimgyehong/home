package com.flowerdiary.ui.android.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flowerdiary.common.constants.ColorPalette
import com.flowerdiary.ui.android.component.BGMPlayer

/**
 * 메인 화면
 * SRP: 메인 메뉴 네비게이션과 전체 레이아웃만 담당
 */
@Composable
fun MainScreen(
    onNavigateToDiary: () -> Unit,
    onNavigateToCollection: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    var bgmPlaying by remember { mutableStateOf(false) }
    var bgmTrack by remember { mutableStateOf(0) }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(ColorPalette.Background.Primary.toInt()))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MainScreenConstants.CONTENT_PADDING.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            TodayFlowerCard()
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MainMenuButton(
                    icon = Icons.Default.Book,
                    label = "일기장",
                    backgroundColor = Color(ColorPalette.Primary.Light.toInt()),
                    onClick = onNavigateToDiary
                )
                
                MainMenuButton(
                    icon = Icons.Default.LocalFlorist,
                    label = "도감",
                    backgroundColor = Color(ColorPalette.Secondary.Light.toInt()),
                    onClick = onNavigateToCollection
                )
                
                MainMenuButton(
                    icon = Icons.Default.Settings,
                    label = "설정",
                    backgroundColor = Color(ColorPalette.Background.Tertiary.toInt()),
                    onClick = onNavigateToSettings
                )
            }
        }
        
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(MainScreenConstants.FLOATING_BUTTON_PADDING.dp)
        ) {
            BGMPlayer(
                isPlaying = bgmPlaying,
                currentTrack = bgmTrack,
                onTogglePlay = { bgmPlaying = !bgmPlaying },
                onTrackChange = { track -> 
                    bgmTrack = track
                    bgmPlaying = true
                }
            )
        }
    }
}
