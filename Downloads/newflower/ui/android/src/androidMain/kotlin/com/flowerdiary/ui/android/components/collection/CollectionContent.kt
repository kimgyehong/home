package com.flowerdiary.ui.android.components.collection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.flowerdiary.common.constants.Dimens
import com.flowerdiary.feature.diary.mapper.FlowerUIModel
import com.flowerdiary.feature.diary.state.CollectionState
import com.flowerdiary.ui.android.theme.toDp

/**
 * 도감 컨텐츠 컴포넌트
 * SRP: 도감의 상태별 UI 표시만 담당
 * - 로딩 상태
 * - 에러 상태
 * - 컨텐츠 그리드
 */
@Composable
internal fun CollectionContent(
    state: CollectionState,
    onFlowerClick: (FlowerUIModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }
            state.error != null -> {
                Text(
                    text = state.error,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {
                CollectionGrid(
                    flowers = state.flowers,
                    onFlowerClick = onFlowerClick
                )
            }
        }
    }
}

/**
 * 탄생화 그리드 레이아웃
 * SRP: 그리드 레이아웃 렌더링만 담당
 */
@Composable
private fun CollectionGrid(
    flowers: List<FlowerUIModel>,
    onFlowerClick: (FlowerUIModel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(Dimens.Grid.ColumnCount),
        contentPadding = PaddingValues(Dimens.Grid.ContentPadding.toDp()),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Grid.Spacing.toDp()),
        verticalArrangement = Arrangement.spacedBy(Dimens.Grid.Spacing.toDp())
    ) {
        items(
            items = flowers,
            key = { it.id }
        ) { flower ->
            FlowerGridItem(
                flower = flower,
                onClick = { onFlowerClick(flower) }
            )
        }
    }
}