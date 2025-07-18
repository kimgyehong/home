package com.flowerdiary.ui.android.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.feature.diary.state.DiaryEditorIntent
import com.flowerdiary.feature.diary.state.DiaryEditorState
import com.flowerdiary.ui.android.component.common.ErrorMessage
import com.flowerdiary.ui.android.component.common.LoadingIndicator
import com.flowerdiary.ui.android.component.input.DiaryContentField
import com.flowerdiary.ui.android.component.input.DiaryTitleField
import com.flowerdiary.ui.android.component.selector.FlowerSelector
import com.flowerdiary.ui.android.component.selector.MoodSelector
import com.flowerdiary.ui.android.component.selector.WeatherSelector
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 일기 편집 컨텐츠 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 일기 편집 폼 레이아웃 구성만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 하위 컴포넌트에 의존
 * 
 * Context7 가이드 적용:
 * - 재사용 가능한 컴포넌트 패턴
 * - 플랫폼 중립적 코드
 * - 적응형 UI 레이아웃
 */
@Composable
fun DiaryEditorContent(
    state: DiaryEditorState,
    onIntent: (DiaryEditorIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        LoadingIndicator(modifier = modifier)
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(UiConstants.Spacing.CONTENT_PADDING.dp),
            verticalArrangement = Arrangement.spacedBy(UiConstants.Spacing.SECTION_SPACING.dp)
        ) {
            if (state.error != null) {
                ErrorMessage(
                    message = state.error,
                    onDismiss = { onIntent(DiaryEditorIntent.ClearError) }
                )
            }
            
            DiaryTitleField(
                title = state.title,
                onTitleChange = { onIntent(DiaryEditorIntent.UpdateTitle(it)) }
            )
            
            DiaryContentField(
                content = state.content,
                onContentChange = { onIntent(DiaryEditorIntent.UpdateContent(it)) }
            )
            
            MoodSelector(
                selectedMood = state.selectedMood,
                onMoodSelect = { onIntent(DiaryEditorIntent.SelectMood(it)) }
            )
            
            WeatherSelector(
                selectedWeather = state.selectedWeather,
                onWeatherSelect = { onIntent(DiaryEditorIntent.SelectWeather(it)) }
            )
            
            FlowerSelector(
                selectedFlower = state.selectedFlower,
                todayFlower = state.todayFlower,
                recommendedFlowers = state.recommendedFlowers,
                onFlowerSelect = { onIntent(DiaryEditorIntent.SelectFlower(it)) },
                onUseTodayFlower = { onIntent(DiaryEditorIntent.UseTodayFlower) },
                onRequestRecommendation = { onIntent(DiaryEditorIntent.RequestFlowerRecommendation) }
            )
        }
    }
}