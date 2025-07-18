package com.flowerdiary.ui.android.component.selector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.domain.model.BirthFlower
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 꽃 선택 액션 버튼들
 * 
 * SOLID 원칙 준수:
 * - SRP: 꽃 액션 버튼 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 도메인 모델에 의존
 * 
 * Context7 가이드 적용:
 * - 재사용 가능한 버튼 컴포넌트 패턴
 * - 플랫폼 중립적 코드
 * - 적응형 UI 레이아웃
 */
@Composable
fun FlowerActionButtons(
    todayFlower: BirthFlower?,
    onUseTodayFlower: () -> Unit,
    onRequestRecommendation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(UiConstants.Spacing.SMALL_SPACING.dp)
    ) {
        todayFlower?.let {
            OutlinedButton(
                onClick = onUseTodayFlower,
                modifier = Modifier.weight(1f)
            ) {
                Text(UiConstants.Strings.USE_TODAY_FLOWER)
            }
        }
        OutlinedButton(
            onClick = onRequestRecommendation,
            modifier = Modifier.weight(1f)
        ) {
            Text(UiConstants.Strings.RECOMMEND_FLOWER)
        }
    }
}