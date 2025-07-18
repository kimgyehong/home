package com.flowerdiary.ui.android.component.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.feature.diary.state.ThemeMode
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 테마 선택기
 * SRP: 테마 선택 UI만 담당
 */
@Composable
internal fun ThemeSelector(
    currentTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ThemeOption(
            theme = ThemeMode.LIGHT,
            isSelected = currentTheme == ThemeMode.LIGHT,
            onSelected = onThemeSelected
        )
        ThemeOption(
            theme = ThemeMode.DARK,
            isSelected = currentTheme == ThemeMode.DARK,
            onSelected = onThemeSelected
        )
        ThemeOption(
            theme = ThemeMode.SYSTEM,
            isSelected = currentTheme == ThemeMode.SYSTEM,
            onSelected = onThemeSelected
        )
    }
}

/**
 * 테마 옵션 항목
 * SRP: 개별 테마 옵션 UI만 담당
 */
@Composable
private fun ThemeOption(
    theme: ThemeMode,
    isSelected: Boolean,
    onSelected: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = UiConstants.Spacing.SMALL_SPACING.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onSelected(theme) }
        )
        Text(
            text = theme.displayName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = UiConstants.Spacing.SMALL_SPACING.dp)
        )
    }
}
