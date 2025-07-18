package com.flowerdiary.ui.android.component.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.feature.diary.state.SettingsIntent
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 알림 설정 섹션 컴포넌트
 * SRP: 알림 관련 설정 UI만 담당
 * - 일일 알림 설정
 * - 자동 해금 설정
 */
@Composable
fun NotificationSettingsSection(
    notificationsEnabled: Boolean,
    autoUnlockEnabled: Boolean,
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
                text = UiConstants.Strings.NOTIFICATION_SETTINGS_TITLE,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = UiConstants.Spacing.SMALL_SPACING.dp)
            )
            
            // 알림 활성화 스위치
            NotificationToggle(
                enabled = notificationsEnabled,
                onToggle = {
                    onIntent(SettingsIntent.ToggleNotifications)
                }
            )
            
            // 자동 해금 스위치
            AutoUnlockToggle(
                enabled = autoUnlockEnabled,
                onToggle = {
                    onIntent(SettingsIntent.ToggleAutoUnlock)
                }
            )
        }
    }
}

/**
 * 알림 활성화 토글
 * SRP: 알림 on/off 스위치 UI만 담당
 */
@Composable
private fun NotificationToggle(
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
            text = UiConstants.Strings.DAILY_NOTIFICATION,
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
 * 자동 해금 토글
 * SRP: 자동 해금 on/off 스위치 UI만 담당
 */
@Composable
private fun AutoUnlockToggle(
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
            text = UiConstants.Strings.AUTO_UNLOCK,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = enabled,
            onCheckedChange = { onToggle() }
        )
    }
}