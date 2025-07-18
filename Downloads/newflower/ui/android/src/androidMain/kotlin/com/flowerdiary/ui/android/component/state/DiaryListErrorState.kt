package com.flowerdiary.ui.android.component.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 일기 목록 에러 상태 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 에러 상태 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 외부 의존성 최소화
 * 
 * 극도로 엄격한 품질 규칙:
 * - 100줄 이하 파일 크기
 * - 30줄 이하 메서드 크기
 * - 사이클로매틱 복잡도 10 이하
 */
@Composable
fun DiaryListErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(UiConstants.Spacing.MEDIUM_SPACING.dp))
        OutlinedButton(onClick = onRetry) {
            Text(UiConstants.Strings.RETRY_BUTTON)
        }
    }
}