package com.flowerdiary.ui.android.component.selector

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 선택된 꽃 카드 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 선택된 꽃 정보 표시만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 도메인 모델에 의존
 * 
 * Context7 가이드 적용:
 * - 재사용 가능한 카드 컴포넌트 패턴
 * - 플랫폼 중립적 코드
 * - 적응형 UI 레이아웃
 */
@Composable
fun SelectedFlowerCard(
    flower: BirthFlower,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UiConstants.Spacing.CARD_PADDING.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = flower.emoji,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(end = UiConstants.Spacing.MEDIUM_SPACING.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = flower.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = flower.meaning,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}