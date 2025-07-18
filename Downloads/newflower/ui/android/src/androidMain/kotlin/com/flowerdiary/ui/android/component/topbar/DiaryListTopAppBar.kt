package com.flowerdiary.ui.android.component.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 일기 목록 상단 앱바 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 상단 앱바 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 외부 의존성 최소화
 * 
 * 극도로 엄격한 품질 규칙:
 * - 100줄 이하 파일 크기
 * - 30줄 이하 메서드 크기
 * - 사이클로매틱 복잡도 10 이하
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryListTopAppBar(
    onNavigateToCollection: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { 
            Text(
                text = UiConstants.Strings.DIARY_LIST_TITLE,
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            TextButton(
                onClick = onNavigateToCollection
            ) {
                Text(UiConstants.Strings.COLLECTION_BUTTON)
            }
        },
        modifier = modifier
    )
}