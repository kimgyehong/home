package com.flowerdiary.ui.android.component.item

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 일기 아이템 컨텐츠 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 일기 아이템 컨텐츠 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 외부 의존성 최소화
 * 
 * 극도로 엄격한 품질 규칙:
 * - 100줄 이하 파일 크기
 * - 30줄 이하 메서드 크기
 * - 사이클로매틱 복잡도 10 이하
 */
@Composable
fun DiaryItemContent(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = UiConstants.Limits.DIARY_PREVIEW_MAX_LINES,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}