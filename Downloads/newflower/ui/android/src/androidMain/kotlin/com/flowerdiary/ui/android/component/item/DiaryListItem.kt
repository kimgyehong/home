package com.flowerdiary.ui.android.component.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.feature.diary.mapper.DiaryUIModel
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 일기 목록 아이템 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 일기 아이템 조립만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 하위 컴포넌트에 의존
 * 
 * 극도로 엄격한 품질 규칙:
 * - 100줄 이하 파일 크기
 * - 30줄 이하 메서드 크기
 * - 사이클로매틱 복잡도 10 이하
 */
@Composable
fun DiaryListItem(
    diary: DiaryUIModel,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UiConstants.Spacing.CARD_PADDING.dp)
        ) {
            DiaryItemHeader(
                title = diary.title,
                moodEmoji = diary.moodEmoji
            )
            
            Spacer(modifier = Modifier.height(UiConstants.Spacing.SMALL_SPACING.dp))
            
            DiaryItemContent(text = diary.preview)
            
            Spacer(modifier = Modifier.height(UiConstants.Spacing.MEDIUM_SPACING.dp))
            
            DiaryItemFooter(
                weatherIcon = diary.weatherIcon,
                formattedDate = diary.formattedDate,
                relativeTime = diary.relativeTime
            )
        }
    }
}