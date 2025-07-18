package com.flowerdiary.ui.android.component.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 일기 제목 입력 필드 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 제목 입력 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 외부 의존성 최소화
 * 
 * Context7 가이드 적용:
 * - 재사용 가능한 입력 컴포넌트 패턴
 * - 플랫폼 중립적 코드
 * - 입력 유효성 검사 통합
 */
@Composable
fun DiaryTitleField(
    title: String,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = title,
        onValueChange = { newTitle ->
            if (newTitle.length <= UiConstants.Limits.TITLE_MAX_LENGTH) {
                onTitleChange(newTitle)
            }
        },
        label = {
            Text(UiConstants.Strings.TITLE_FIELD_PLACEHOLDER)
        },
        modifier = modifier.fillMaxWidth(),
        singleLine = true
    )
}