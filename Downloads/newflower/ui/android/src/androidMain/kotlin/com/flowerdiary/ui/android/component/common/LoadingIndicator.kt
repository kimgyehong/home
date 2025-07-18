package com.flowerdiary.ui.android.component.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 로딩 상태 표시 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 로딩 상태 UI 표시만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 외부 의존성 없음
 * 
 * Context7 가이드 적용:
 * - 재사용 가능한 Modifier 패턴
 * - 플랫폼 중립적 코드
 * - 단일 책임 원칙
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(UiConstants.Spacing.CONTENT_PADDING.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}