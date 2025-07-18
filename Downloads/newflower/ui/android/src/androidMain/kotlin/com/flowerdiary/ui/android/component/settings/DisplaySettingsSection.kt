package com.flowerdiary.ui.android.component.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.feature.diary.state.FontSizeLevel
import com.flowerdiary.feature.diary.state.SettingsIntent
import com.flowerdiary.feature.diary.state.ThemeMode
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 화면 설정 섹션 컴포넌트
 * SRP: 화면 관련 설정 UI 레이아웃만 담당
 */
@Composable
fun DisplaySettingsSection(
    themeMode: ThemeMode,
    fontSizeScale: Float,
    onIntent: (SettingsIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = UiConstants.Spacing.CARD_ELEVATION.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UiConstants.Spacing.CARD_PADDING.dp)
        ) {
            Text(
                text = UiConstants.Strings.DISPLAY_SETTINGS_TITLE,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = UiConstants.Spacing.SMALL_SPACING.dp)
            )
            
            ThemeSelector(
                currentTheme = themeMode,
                onThemeSelected = { mode ->
                    onIntent(SettingsIntent.ChangeTheme(mode))
                }
            )
            
            FontSizeSelector(
                currentScale = fontSizeScale,
                onLevelSelected = { level ->
                    onIntent(SettingsIntent.ChangeFontSize(level))
                }
            )
        }
    }
}
