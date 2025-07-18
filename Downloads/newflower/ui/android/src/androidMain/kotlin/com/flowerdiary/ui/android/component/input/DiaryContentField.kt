package com.flowerdiary.ui.android.component.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 일기 내용 입력 필드 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 내용 입력 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 외부 의존성 최소화
 * 
 * Context7 가이드 적용:
 * - 재사용 가능한 입력 컴포넌트 패턴
 * - 플랫폼 중립적 코드
 * - 멀티라인 입력 최적화
 */
@Composable
fun DiaryContentField(
    content: String,
    onContentChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = content,
        onValueChange = { newContent ->
            if (newContent.length <= UiConstants.Limits.CONTENT_MAX_LENGTH) {
                onContentChange(newContent)
            }
        },
        label = {
            Text(UiConstants.Strings.CONTENT_FIELD_PLACEHOLDER)
        },
        modifier = modifier.fillMaxWidth(),
        minLines = UiConstants.Size.CONTENT_FIELD_HEIGHT / 24
    )
}