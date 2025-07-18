package com.flowerdiary.ui.android.component.selector

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 꽃 선택 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 꽃 선택 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 도메인 모델에 의존
 * 
 * Context7 가이드 적용:
 * - 재사용 가능한 선택 컴포넌트 패턴
 * - 플랫폼 중립적 코드
 * - 적응형 UI 레이아웃
 */
@Composable
fun FlowerSelector(
    selectedFlower: BirthFlower?,
    todayFlower: BirthFlower?,
    recommendedFlowers: List<BirthFlower>,
    onFlowerSelect: (BirthFlower) -> Unit,
    onUseTodayFlower: () -> Unit,
    onRequestRecommendation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = UiConstants.Strings.FLOWER_SELECTOR_TITLE,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = UiConstants.Spacing.SMALL_SPACING.dp)
        )
        
        selectedFlower?.let { flower ->
            SelectedFlowerCard(
                flower = flower,
                modifier = Modifier.padding(bottom = UiConstants.Spacing.MEDIUM_SPACING.dp)
            )
        }
        
        FlowerActionButtons(
            todayFlower = todayFlower,
            onUseTodayFlower = onUseTodayFlower,
            onRequestRecommendation = onRequestRecommendation
        )
        
        RecommendedFlowersList(
            flowers = recommendedFlowers,
            selectedFlower = selectedFlower,
            onFlowerSelect = onFlowerSelect
        )
    }
}