package com.flowerdiary.ui.android.component.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.feature.diary.state.SettingsIntent
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 초기화 설정 섹션 컴포넌트
 * SRP: 설정 초기화 관련 UI만 담당
 * - 모든 설정 초기화 버튼
 * - 초기화 설명
 */
@Composable
fun ResetSettingsSection(
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
                .padding(UiConstants.Spacing.CARD_PADDING.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = UiConstants.Strings.RESET_SETTINGS_TITLE,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = UiConstants.Spacing.SMALL_SPACING.dp)
            )
            
            Text(
                text = UiConstants.Strings.RESET_DESCRIPTION,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = UiConstants.Spacing.MEDIUM_SPACING.dp)
            )
            
            ResetButton(
                onClick = {
                    onIntent(SettingsIntent.ResetData)
                }
            )
        }
    }
}

/**
 * 초기화 버튼
 * SRP: 초기화 버튼 UI만 담당
 */
@Composable
private fun ResetButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = UiConstants.Strings.RESET_ALL_SETTINGS,
            color = MaterialTheme.colorScheme.error
        )
    }
}