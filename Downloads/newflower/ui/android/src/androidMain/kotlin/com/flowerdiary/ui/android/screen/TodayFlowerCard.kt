package com.flowerdiary.ui.android.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flowerdiary.common.constants.ColorPalette
import com.flowerdiary.common.platform.DateTimeUtil
import kotlinx.datetime.Clock

/**
 * 오늘의 탄생화 카드
 * SRP: 오늘의 탄생화 정보 표시만 담당
 */
@Composable
internal fun TodayFlowerCard() {
    val today = remember { 
        DateTimeUtil.toDateInfo(Clock.System.now().toEpochMilliseconds()) 
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MainScreenConstants.CARD_HORIZONTAL_PADDING.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(ColorPalette.Background.Card.toInt())
                .copy(alpha = MainScreenConstants.CARD_ALPHA)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MainScreenConstants.CARD_CONTENT_PADDING.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "오늘의 탄생화",
                style = MaterialTheme.typography.titleMedium,
                color = Color(ColorPalette.Text.Primary.toInt())
            )
            
            Spacer(modifier = Modifier.height(MainScreenConstants.SMALL_SPACING.dp))
            
            Text(
                text = "${today.month}월 ${today.day}일",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(ColorPalette.Primary.Default.toInt())
            )
            
            Spacer(modifier = Modifier.height(MainScreenConstants.SMALL_SPACING.dp))
            
            Text(
                text = "일기를 작성하고\n오늘의 꽃을 확인하세요",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(ColorPalette.Text.Secondary.toInt()),
                textAlign = TextAlign.Center
            )
        }
    }
}
