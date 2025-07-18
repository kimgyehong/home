package com.flowerdiary.ui.android.component.fab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 일기 목록 플로팅 액션 버튼 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 플로팅 액션 버튼 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 외부 의존성 최소화
 * 
 * 극도로 엄격한 품질 규칙:
 * - 100줄 이하 파일 크기
 * - 30줄 이하 메서드 크기
 * - 사이클로매틱 복잡도 10 이하
 */
@Composable
fun DiaryListFAB(
    onNewDiary: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onNewDiary,
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = UiConstants.Strings.NEW_DIARY_BUTTON
        )
    }
}