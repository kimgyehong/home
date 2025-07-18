package com.flowerdiary.ui.android.component.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.feature.diary.state.DiaryListState
import com.flowerdiary.ui.android.component.item.DiaryListItem
import com.flowerdiary.ui.android.component.state.DiaryListEmptyState
import com.flowerdiary.ui.android.component.state.DiaryListErrorState
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 일기 목록 컨텐츠 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 일기 목록 컨텐츠 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 상태와 하위 컴포넌트에 의존
 * 
 * 극도로 엄격한 품질 규칙:
 * - 100줄 이하 파일 크기
 * - 30줄 이하 메서드 크기
 * - 사이클로매틱 복잡도 10 이하
 */
@Composable
fun DiaryListContent(
    state: DiaryListState,
    onDiaryClick: (String) -> Unit,
    onDeleteDiary: (String) -> Unit,
    onRetry: () -> Unit,
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
                DiaryListErrorState(
                    message = state.error,
                    onRetry = onRetry
                )
            }
            state.diaries.isEmpty() -> {
                DiaryListEmptyState()
            }
            else -> {
                DiaryList(
                    state = state,
                    onDiaryClick = onDiaryClick,
                    onDeleteDiary = onDeleteDiary
                )
            }
        }
    }
}

@Composable
private fun DiaryList(
    state: DiaryListState,
    onDiaryClick: (String) -> Unit,
    onDeleteDiary: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(UiConstants.Spacing.CONTENT_PADDING.dp),
        verticalArrangement = Arrangement.spacedBy(
            UiConstants.Spacing.DIARY_LIST_ITEM_SPACING.dp
        )
    ) {
        items(
            items = state.diaries,
            key = { it.id }
        ) { diary ->
            DiaryListItem(
                diary = diary,
                onClick = { onDiaryClick(diary.id) },
                onDelete = { onDeleteDiary(diary.id) }
            )
        }
    }
}