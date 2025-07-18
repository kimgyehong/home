package com.flowerdiary.ui.android.component

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.flowerdiary.common.constants.Config

/**
 * BGM 플레이어 플로팅 버튼
 * SRP: BGM 재생 컨트롤 UI만 담당
 * 4가지 BGM 선택 가능
 */
@Composable
fun BGMPlayer(
    isPlaying: Boolean,
    currentTrack: Int,
    onTogglePlay: () -> Unit,
    onTrackChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    Box(
        modifier = modifier
    ) {
        // BGM 트랙 선택 메뉴
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Card(
                modifier = Modifier
                    .padding(bottom = FAB_SIZE.dp + SPACING.dp)
                    .width(MENU_WIDTH.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = ELEVATION.dp)
            ) {
                Column(
                    modifier = Modifier.padding(MENU_PADDING.dp)
                ) {
                    Text(
                        text = "BGM 선택",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(bottom = SMALL_SPACING.dp)
                    )
                    
                    BGM_TRACKS.forEachIndexed { index, trackName ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.small)
                                .clickable {
                                    onTrackChange(index)
                                    expanded = false
                                }
                                .background(
                                    if (index == currentTrack) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surface
                                    }
                                )
                                .padding(ITEM_PADDING.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = index == currentTrack,
                                onClick = null,
                                modifier = Modifier.size(RADIO_SIZE.dp)
                            )
                            Spacer(modifier = Modifier.width(SMALL_SPACING.dp))
                            Text(
                                text = trackName,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
        
        // BGM 재생/정지 FAB
        FloatingActionButton(
            onClick = {
                if (isPlaying) {
                    onTogglePlay()
                } else {
                    expanded = !expanded
                    if (!isPlaying) onTogglePlay()
                }
            },
            modifier = Modifier.size(FAB_SIZE.dp),
            containerColor = if (isPlaying) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ) {
            AnimatedContent(
                targetState = isPlaying,
                transitionSpec = {
                    fadeIn() + scaleIn() with fadeOut() + scaleOut()
                },
                label = "bgm_icon"
            ) { playing ->
                Icon(
                    imageVector = if (playing) Icons.Default.MusicNote else Icons.Default.MusicOff,
                    contentDescription = if (playing) "BGM 재생 중" else "BGM 정지",
                    tint = if (playing) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}

// BGM 트랙 목록
private val BGM_TRACKS = listOf(
    "봄의 왈츠",
    "여름 바람",
    "가을 낙엽",
    "겨울 눈꽃"
)

// UI 상수들
private const val FAB_SIZE = 56
private const val MENU_WIDTH = 200
private const val SPACING = 8
private const val MENU_PADDING = 16
private const val ITEM_PADDING = 12
private const val SMALL_SPACING = 8
private const val RADIO_SIZE = 20
private const val ELEVATION = 8