package com.flowerdiary.ui.android.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.flowerdiary.feature.diary.mapper.FlowerUIModel
import com.flowerdiary.feature.diary.state.CollectionIntent
import com.flowerdiary.feature.diary.viewmodel.CollectionViewModel
import com.flowerdiary.ui.android.components.collection.CollectionContent
import com.flowerdiary.ui.android.components.collection.CollectionTopAppBar
import com.flowerdiary.ui.android.components.collection.FlowerDetailDialog
import org.koin.androidx.compose.koinViewModel

/**
 * 도감 화면
 * SRP: 탄생화 컬렉션 UI만 담당
 * 365일 탄생화를 그리드로 표시
 */
@Composable
fun CollectionScreen(
    onNavigateBack: () -> Unit,
    viewModel: CollectionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var selectedFlower by remember { mutableStateOf<FlowerUIModel?>(null) }
    
    LaunchedEffect(Unit) {
        viewModel.handleIntent(CollectionIntent.LoadCollection)
    }
    
    Scaffold(
        topBar = {
            CollectionTopAppBar(
                unlockedCount = state.unlockedCount,
                isLoading = state.isLoading,
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CollectionContent(
                state = state,
                onFlowerClick = { flower ->
                    if (flower.isUnlocked) {
                        selectedFlower = flower
                    }
                }
            )
            
            // 탄생화 상세 다이얼로그
            selectedFlower?.let { flower ->
                FlowerDetailDialog(
                    flower = flower,
                    onDismiss = { selectedFlower = null },
                    onShare = { /* 공유 기능 구현 예정 */ }
                )
            }
        }
    }
}

