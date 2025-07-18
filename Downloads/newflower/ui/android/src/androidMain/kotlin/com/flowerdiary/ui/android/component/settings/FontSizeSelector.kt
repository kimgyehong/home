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
import com.flowerdiary.feature.diary.state.FontSizeLevel
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 폰트 크기 선택기
 * SRP: 폰트 크기 선택 UI만 담당
 */
@Composable
internal fun FontSizeSelector(
    currentScale: Float,
    onLevelSelected: (FontSizeLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentLevel = determineFontSizeLevel(currentScale)
    
    Column(modifier = modifier) {
        Text(
            text = UiConstants.Strings.FONT_SIZE,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = UiConstants.Spacing.SMALL_SPACING.dp)
        )
        
        FontSizeLevel.values().forEach { level ->
            FontSizeOption(
                level = level,
                isSelected = currentLevel == level,
                onSelected = onLevelSelected
            )
        }
    }
}

/**
 * 폰트 크기 옵션 항목
 * SRP: 개별 폰트 크기 옵션 UI만 담당
 */
@Composable
private fun FontSizeOption(
    level: FontSizeLevel,
    isSelected: Boolean,
    onSelected: (FontSizeLevel) -> Unit,
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
            onClick = { onSelected(level) }
        )
        Text(
            text = level.displayName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = UiConstants.Spacing.SMALL_SPACING.dp)
        )
    }
}

/**
 * 스케일 값으로 폰트 크기 레벨 결정
 * SRP: 폰트 크기 로직만 담당
 */
private fun determineFontSizeLevel(scale: Float): FontSizeLevel {
    return when {
        scale < FontSizeLevel.SMALL.scale -> FontSizeLevel.SMALL
        scale > FontSizeLevel.LARGE.scale -> FontSizeLevel.LARGE
        else -> FontSizeLevel.MEDIUM
    }
}
