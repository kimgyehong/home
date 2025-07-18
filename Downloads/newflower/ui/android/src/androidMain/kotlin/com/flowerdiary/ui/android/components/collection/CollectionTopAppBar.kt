package com.flowerdiary.ui.android.components.collection

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flowerdiary.common.constants.Config
import com.flowerdiary.common.constants.Dimens
import com.flowerdiary.ui.android.theme.toDp

/**
 * 도감 화면 상단바
 * SRP: 도감 화면의 TopAppBar만 담당
 * - 제목 표시
 * - 뒤로가기 버튼
 * - 잠금 해제 수 표시
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CollectionTopAppBar(
    unlockedCount: Int,
    isLoading: Boolean,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = { 
            TopAppBarTitle()
        },
        navigationIcon = {
            NavigationIcon(onNavigateBack)
        },
        actions = {
            if (!isLoading) {
                UnlockedCountText(unlockedCount)
            }
        }
    )
}

/**
 * TopAppBar 제목
 * SRP: 제목 텍스트만 담당
 */
@Composable
private fun TopAppBarTitle() {
    Text(
        text = "탄생화 도감",
        style = MaterialTheme.typography.titleLarge
    )
}

/**
 * 네비게이션 아이콘
 * SRP: 뒤로가기 버튼만 담당
 */
@Composable
private fun NavigationIcon(
    onNavigateBack: () -> Unit
) {
    IconButton(onClick = onNavigateBack) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "뒤로가기"
        )
    }
}

/**
 * 잠금 해제 수 텍스트
 * SRP: 진행 상태 표시만 담당
 */
@Composable
private fun UnlockedCountText(
    unlockedCount: Int
) {
    Text(
        text = "$unlockedCount/${Config.TOTAL_DAYS}",
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(end = Dimens.Padding.Medium.toDp())
    )
}