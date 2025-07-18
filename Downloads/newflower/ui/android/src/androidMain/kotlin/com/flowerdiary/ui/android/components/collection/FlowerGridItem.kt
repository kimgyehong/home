package com.flowerdiary.ui.android.components.collection

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.flowerdiary.common.constants.ColorPalette
import com.flowerdiary.common.constants.Dimens
import com.flowerdiary.feature.diary.mapper.FlowerUIModel
import com.flowerdiary.ui.android.theme.toDp

/**
 * 탄생화 그리드 아이템 컴포넌트
 * SRP: 개별 탄생화 카드 UI만 담당
 * - 잠김/해제 상태 표시
 * - 카드 스타일링
 */
@Composable
internal fun FlowerGridItem(
    flower: FlowerUIModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(Dimens.Grid.CardAspectRatio),
        colors = CardDefaults.cardColors(
            containerColor = if (flower.isUnlocked) {
                Color(flower.backgroundColor.toInt())
            } else {
                Color(ColorPalette.Utility.Locked.toInt())
            }
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (flower.isUnlocked) {
                UnlockedFlowerContent(flower)
            } else {
                LockedFlowerContent(flower)
            }
        }
    }
}

/**
 * 해제된 탄생화 내용
 * SRP: 해제된 상태의 UI만 담당
 */
@Composable
private fun UnlockedFlowerContent(
    flower: FlowerUIModel
) {
    Column(
        modifier = Modifier.padding(Dimens.Padding.Small.toDp()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = flower.dateDisplay,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(Dimens.Padding.XSmall.toDp()))
        Text(
            text = flower.name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * 잠긴 탄생화 내용
 * SRP: 잠긴 상태의 UI만 담당
 */
@Composable
private fun LockedFlowerContent(
    flower: FlowerUIModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = "잠김",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(Dimens.Icon.Lock.toDp())
        )
        Spacer(modifier = Modifier.height(Dimens.Padding.XSmall.toDp()))
        Text(
            text = flower.dateDisplay,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}