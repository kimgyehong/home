package com.flowerdiary.ui.android.component.selector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.flowerdiary.domain.model.Weather
import com.flowerdiary.ui.android.constants.UiConstants

/**
 * 날씨 선택 컴포넌트
 * 
 * SOLID 원칙 준수:
 * - SRP: 날씨 선택 UI만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 도메인 모델에 의존
 * 
 * Context7 가이드 적용:
 * - 재사용 가능한 선택 컴포넌트 패턴
 * - 플랫폼 중립적 코드
 * - 적응형 UI 레이아웃
 */
@Composable
fun WeatherSelector(
    selectedWeather: Weather?,
    onWeatherSelect: (Weather) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = UiConstants.Strings.WEATHER_SELECTOR_TITLE,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = UiConstants.Spacing.SMALL_SPACING.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Weather.values().forEach { weather ->
                Card(
                    modifier = Modifier
                        .padding(UiConstants.Spacing.SMALL_SPACING.dp)
                        .clickable { onWeatherSelect(weather) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedWeather == weather) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    )
                ) {
                    Text(
                        text = weather.icon,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(UiConstants.Spacing.MEDIUM_SPACING.dp)
                    )
                }
            }
        }
    }
}