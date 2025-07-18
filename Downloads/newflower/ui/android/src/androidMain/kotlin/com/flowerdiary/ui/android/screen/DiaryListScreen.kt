package com.flowerdiary.ui.android.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.flowerdiary.feature.diary.state.DiaryListIntent
import com.flowerdiary.feature.diary.viewmodel.DiaryListViewModel
import com.flowerdiary.ui.android.component.content.DiaryListContent
import com.flowerdiary.ui.android.component.fab.DiaryListFAB
import com.flowerdiary.ui.android.component.topbar.DiaryListTopAppBar
import org.koin.androidx.compose.koinViewModel

/**
 * 일기 목록 화면
 * 
 * SOLID 원칙 준수:
 * - SRP: 일기 목록 화면 조립만 담당
 * - OCP: 확장 가능한 구조
 * - DIP: 하위 컴포넌트에 의존
 * 
 * 극도로 엄격한 품질 규칙:
 * - 100줄 이하 파일 크기
 * - 30줄 이하 메서드 크기
 * - 사이클로매틱 복잡도 10 이하
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryListScreen(
    onNavigateToEditor: (String?) -> Unit,
    onNavigateToCollection: () -> Unit,
    viewModel: DiaryListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.handleIntent(DiaryListIntent.LoadDiaries)
    }
    
    Scaffold(
        topBar = {
            DiaryListTopAppBar(
                onNavigateToCollection = onNavigateToCollection
            )
        },
        floatingActionButton = {
            DiaryListFAB(
                onNewDiary = { onNavigateToEditor(null) }
            )
        }
    ) { paddingValues ->
        DiaryListContent(
            state = state,
            onDiaryClick = onNavigateToEditor,
            onDeleteDiary = { diaryId ->
                viewModel.handleIntent(DiaryListIntent.DeleteDiary(diaryId))
            },
            onRetry = {
                viewModel.handleIntent(DiaryListIntent.LoadDiaries)
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}