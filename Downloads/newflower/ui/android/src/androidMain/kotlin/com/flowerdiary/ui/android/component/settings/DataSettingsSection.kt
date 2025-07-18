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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.feature.diary.state.DataSettingsState
import com.flowerdiary.feature.diary.state.SettingsIntent
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 데이터 설정 섹션 컴포넌트
 * SRP: 데이터 관련 설정 UI만 담당
 * - 자동 백업 설정
 * - 데이터 내보내기/가져오기
 * - 데이터 삭제
 */
@Composable
fun DataSettingsSection(
    dataSettings: DataSettingsState,
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
                text = UiConstants.Strings.DATA_SETTINGS_TITLE,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = UiConstants.Spacing.SMALL_SPACING.dp)
            )
            
            // 자동 백업 스위치
            AutoBackupToggle(
                enabled = dataSettings.autoBackup,
                onToggle = { enabled ->
                    onIntent(SettingsIntent.ToggleAutoBackup(enabled))
                }
            )
            
            // 백업 정보
            if (dataSettings.lastBackupDate != null) {
                BackupInfo(
                    lastBackupDate = dataSettings.lastBackupDate
                )
            }
            
            // 데이터 관리 버튼들
            DataManagementButtons(
                onExport = {
                    onIntent(SettingsIntent.ExportData)
                },
                onImport = {
                    onIntent(SettingsIntent.ImportData)
                }
            )
        }
    }
}

/**
 * 자동 백업 토글
 * SRP: 자동 백업 on/off 스위치 UI만 담당
 */
@Composable
private fun AutoBackupToggle(
    enabled: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = UiConstants.Spacing.SMALL_SPACING.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = UiConstants.Strings.AUTO_BACKUP,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = enabled,
            onCheckedChange = onToggle
        )
    }
}

/**
 * 백업 정보 표시
 * SRP: 마지막 백업 정보 표시만 담당
 */
@Composable
private fun BackupInfo(
    lastBackupDate: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "${UiConstants.Strings.LAST_BACKUP}: $lastBackupDate",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(vertical = UiConstants.Spacing.SMALL_SPACING.dp)
    )
}

/**
 * 데이터 관리 버튼들
 * SRP: 데이터 내보내기/가져오기 버튼 UI만 담당
 */
@Composable
private fun DataManagementButtons(
    onExport: () -> Unit,
    onImport: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = UiConstants.Spacing.MEDIUM_SPACING.dp)
    ) {
        TextButton(
            onClick = onExport,
            modifier = Modifier.weight(1f)
        ) {
            Text(UiConstants.Strings.EXPORT_DATA)
        }
        TextButton(
            onClick = onImport,
            modifier = Modifier.weight(1f)
        ) {
            Text(UiConstants.Strings.IMPORT_DATA)
        }
    }
}