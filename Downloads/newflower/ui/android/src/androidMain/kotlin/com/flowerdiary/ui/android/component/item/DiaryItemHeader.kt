package com.flowerdiary.ui.android.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

/**
 * 일기 아이템 헤더 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 일기 아이템 헤더 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 외부 의존성 최소화
 * 
 * 극도로 엄격한 품질 규칙:
 * - 100줄 이하 파일 크기
 * - 30줄 이하 메서드 크기
 * - 사이클로매틱 복잡도 10 이하
 */
@Composable
fun DiaryItemHeader(
    title: String,
    moodEmoji: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        
        Text(
            text = moodEmoji,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}