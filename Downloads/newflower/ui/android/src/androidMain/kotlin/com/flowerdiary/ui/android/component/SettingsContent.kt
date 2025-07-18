package com.flowerdiary.ui.android.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.feature.diary.state.SettingsIntent
import com.flowerdiary.feature.diary.state.SettingsState
import com.flowerdiary.ui.android.component.settings.BGMSettingsSection
import com.flowerdiary.ui.android.component.settings.DisplaySettingsSection
import com.flowerdiary.ui.android.component.settings.NotificationSettingsSection
import com.flowerdiary.ui.android.component.settings.ResetSettingsSection
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 설정 컨텐츠 컴포넌트
 * SRP: 설정 페이지 레이아웃 구성만 담당
 * - 각 설정 섹션을 수직 배치
 * - 하위 컴포넌트들에 상태와 이벤트 전달
 */
@Composable
fun SettingsContent(
    state: SettingsState,
    showResetDialog: Boolean,
    onIntent: (SettingsIntent) -> Unit,
    onDismissResetDialog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(UiConstants.Spacing.CONTENT_PADDING.dp),
        verticalArrangement = Arrangement.spacedBy(UiConstants.Spacing.SECTION_SPACING.dp)
    ) {
        // 화면 설정 섹션
        DisplaySettingsSection(
            themeMode = state.themeMode,
            fontSizeScale = state.fontSizeScale,
            onIntent = onIntent
        )
        
        // 알림 설정 섹션
        NotificationSettingsSection(
            notificationsEnabled = state.notificationsEnabled,
            autoUnlockEnabled = state.autoUnlockEnabled,
            onIntent = onIntent
        )
        
        // 배경음 설정 섹션
        BGMSettingsSection(
            bgmEnabled = state.bgmEnabled,
            bgmVolume = state.bgmVolume,
            bgmTrackIndex = state.bgmTrackIndex,
            onIntent = onIntent
        )
        
        // 초기화 섹션
        ResetSettingsSection(
            onIntent = onIntent
        )
    }
    
    // 초기화 확인 다이얼로그
    if (showResetDialog) {
        ResetConfirmationDialog(
            onConfirm = {
                onIntent(SettingsIntent.ResetData)
                onDismissResetDialog()
            },
            onDismiss = onDismissResetDialog
        )
    }
}

/**
 * 초기화 확인 다이얼로그
 * SRP: 초기화 확인 다이얼로그 UI만 담당
 */
@Composable
private fun ResetConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(UiConstants.Strings.RESET_DIALOG_TITLE)
        },
        text = {
            Text(UiConstants.Strings.RESET_DIALOG_MESSAGE)
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(UiConstants.Strings.CONFIRM_BUTTON)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(UiConstants.Strings.CANCEL_BUTTON)
            }
        }
    )
}