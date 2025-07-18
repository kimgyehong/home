package com.flowerdiary.ui.android.component.selector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 꽃 카드 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 꽃 카드 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 도메인 모델에 의존
 * 
 * Context7 가이드 적용:
 * - 재사용 가능한 카드 컴포넌트 패턴
 * - 플랫폼 중립적 코드
 * - 적응형 UI 레이아웃
 */
@Composable
fun FlowerCard(
    flower: BirthFlower,
    isSelected: Boolean,
    onFlowerSelect: (BirthFlower) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onFlowerSelect(flower) }
            .width(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(UiConstants.Spacing.CARD_PADDING.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = flower.emoji,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = flower.name,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}