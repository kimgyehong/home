package com.flowerdiary.ui.android.component.selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 추천 꽃 목록 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 추천 꽃 목록 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 도메인 모델에 의존
 * 
 * Context7 가이드 적용:
 * - 재사용 가능한 리스트 컴포넌트 패턴
 * - 플랫폼 중립적 코드
 * - 적응형 UI 레이아웃
 */
@Composable
fun RecommendedFlowersList(
    flowers: List<BirthFlower>,
    selectedFlower: BirthFlower?,
    onFlowerSelect: (BirthFlower) -> Unit,
    modifier: Modifier = Modifier
) {
    if (flowers.isNotEmpty()) {
        Text(
            text = UiConstants.Strings.RECOMMENDED_FLOWERS,
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(
                top = UiConstants.Spacing.MEDIUM_SPACING.dp,
                bottom = UiConstants.Spacing.SMALL_SPACING.dp
            )
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(UiConstants.Spacing.SMALL_SPACING.dp)
        ) {
            items(flowers) { flower ->
                FlowerCard(
                    flower = flower,
                    isSelected = selectedFlower == flower,
                    onFlowerSelect = onFlowerSelect
                )
            }
        }
    }
}