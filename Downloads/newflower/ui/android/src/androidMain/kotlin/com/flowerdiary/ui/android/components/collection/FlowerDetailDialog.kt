package com.flowerdiary.ui.android.components.collection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.flowerdiary.common.constants.Dimens
import com.flowerdiary.feature.diary.mapper.FlowerUIModel
import com.flowerdiary.ui.android.theme.toDp

/**
 * 탄생화 상세 다이얼로그
 * SRP: 탄생화 상세 정보 표시만 담당
 */
@Composable
fun FlowerDetailDialog(
    flower: FlowerUIModel,
    onDismiss: () -> Unit,
    onShare: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        title = {
            FlowerDetailTitle(flower)
        },
        text = {
            FlowerDetailContent(flower)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("닫기")
            }
        },
        dismissButton = {
            TextButton(onClick = onShare) {
                Text("공유")
            }
        }
    )
}

/**
 * 다이얼로그 제목 부분
 * SRP: 탄생화 이름 표시만 담당
 */
@Composable
private fun FlowerDetailTitle(
    flower: FlowerUIModel
) {
    Column {
        Text(
            text = flower.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = flower.englishName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * 다이얼로그 본문 내용
 * SRP: 탄생화 상세 정보 레이아웃만 담당
 */
@Composable
private fun FlowerDetailContent(
    flower: FlowerUIModel
) {
    Column {
        FlowerImagePlaceholder(flower)
        
        Spacer(modifier = Modifier.height(Dimens.Padding.Medium.toDp()))
        
        FlowerDetailSection(
            title = "꽃말",
            content = flower.meaning
        )
        
        Spacer(modifier = Modifier.height(Dimens.Padding.Medium.toDp()))
        
        FlowerDetailSection(
            title = "설명",
            content = flower.description
        )
    }
}

/**
 * 탄생화 이미지 플레이스홀더
 * SRP: 이미지 영역 표시만 담당
 */
@Composable
private fun FlowerImagePlaceholder(
    flower: FlowerUIModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.Icon.FlowerDetail.toDp())
            .clip(RoundedCornerShape(Dimens.Radius.Card.toDp()))
            .background(Color(flower.backgroundColor.toInt())),
        contentAlignment = Alignment.Center
    ) {
        // 실제 앱에서는 이미지 로드
        Text(
            text = flower.dateDisplay,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/**
 * 상세 정보 섹션
 * SRP: 제목-내용 쌍 표시만 담당
 */
@Composable
private fun FlowerDetailSection(
    title: String,
    content: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = Dimens.Padding.XSmall.toDp())
        )
    }
}