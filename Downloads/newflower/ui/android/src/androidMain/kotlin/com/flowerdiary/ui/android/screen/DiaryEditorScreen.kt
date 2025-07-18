package com.flowerdiary.ui.android.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.flowerdiary.feature.diary.state.DiaryEditorIntent
import com.flowerdiary.feature.diary.viewmodel.DiaryEditorViewModel
import com.flowerdiary.ui.android.component.DiaryEditorContent
import com.flowerdiary.ui.android.constants.UiConstants
import org.koin.androidx.compose.koinViewModel

/**
 * 일기 편집 화면
 * SRP: 일기 작성/수정 UI 화면 구성만 담당
 * - 상태 관리는 ViewModel에 위임
 * - 컨텐츠 렌더링은 DiaryEditorContent에 위임
 * - 네비게이션은 외부에서 주입받음
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryEditorScreen(
    diaryId: String?,
    onNavigateBack: () -> Unit,
    viewModel: DiaryEditorViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    
    LaunchedEffect(diaryId) {
        if (diaryId != null) {
            viewModel.handleIntent(DiaryEditorIntent.LoadDiary(diaryId))
        }
    }
    
    Scaffold(
        topBar = {
            DiaryEditorTopBar(
                isNewDiary = diaryId == null,
                isLoading = state.isLoading,
                onNavigateBack = onNavigateBack,
                onSave = {
                    viewModel.handleIntent(DiaryEditorIntent.SaveDiary)
                }
            )
        }
    ) { paddingValues ->
        DiaryEditorContent(
            state = state,
            onIntent = viewModel::handleIntent,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

/**
 * 일기 편집 화면 상단 앱바
 * SRP: 상단 앱바 UI만 담당
 */
@Composable
private fun DiaryEditorTopBar(
    isNewDiary: Boolean,
    isLoading: Boolean,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit
) {
    TopAppBar(
        title = { 
            Text(
                text = if (isNewDiary) {
                    UiConstants.Strings.NEW_DIARY_TITLE
                } else {
                    UiConstants.Strings.EDIT_DIARY_TITLE
                },
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = UiConstants.Strings.BACK_BUTTON_DESCRIPTION
                )
            }
        },
        actions = {
            if (!isLoading) {
                IconButton(onClick = onSave) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = UiConstants.Strings.SAVE_BUTTON_DESCRIPTION
                    )
                }
            }
        }
    )
}