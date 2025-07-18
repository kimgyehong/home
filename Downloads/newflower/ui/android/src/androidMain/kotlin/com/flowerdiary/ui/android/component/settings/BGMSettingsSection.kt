package com.flowerdiary.ui.android.component.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.feature.diary.state.SettingsIntent
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 배경음 설정 섹션 컴포넌트
 * SRP: 배경음 관련 설정 UI만 담당
 * - 배경음 활성화/비활성화
 * - 볼륨 조절
 * - 트랙 선택
 */
@Composable
fun BGMSettingsSection(
    bgmEnabled: Boolean,
    bgmVolume: Float,
    bgmTrackIndex: Int,
    onIntent: (SettingsIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UiConstants.Spacing.CARD_PADDING.dp)
        ) {
            Text(
                text = UiConstants.Strings.BGM_SETTINGS_TITLE,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = UiConstants.Spacing.SMALL_SPACING.dp)
            )
            
            // 배경음 활성화 스위치
            BGMToggle(
                enabled = bgmEnabled,
                onToggle = {
                    onIntent(SettingsIntent.ToggleBGM)
                }
            )
            
            // 볼륨 조절
            if (bgmEnabled) {
                VolumeControl(
                    volume = bgmVolume,
                    onVolumeChange = { volume ->
                        onIntent(SettingsIntent.ChangeBGMVolume(volume))
                    }
                )
            }
        }
    }
}

/**
 * 배경음 활성화 토글
 * SRP: 배경음 on/off 스위치 UI만 담당
 */
@Composable
private fun BGMToggle(
    enabled: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = UiConstants.Spacing.SMALL_SPACING.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = UiConstants.Strings.BGM_PLAYBACK,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = enabled,
            onCheckedChange = { onToggle() }
        )
    }
}

/**
 * 볼륨 조절 컨트롤
 * SRP: 볼륨 슬라이더 UI만 담당
 */
@Composable
private fun VolumeControl(
    volume: Float,
    onVolumeChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = UiConstants.Spacing.SMALL_SPACING.dp)
    ) {
        Text(
            text = "${UiConstants.Strings.VOLUME}: ${(volume * 100).toInt()}%",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Slider(
            value = volume,
            onValueChange = onVolumeChange,
            valueRange = 0f..1f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}