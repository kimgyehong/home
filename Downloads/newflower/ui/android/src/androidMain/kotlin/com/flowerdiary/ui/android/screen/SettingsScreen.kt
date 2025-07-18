package com.flowerdiary.ui.android.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.flowerdiary.feature.diary.state.SettingsEffect
import com.flowerdiary.feature.diary.state.SettingsIntent
import com.flowerdiary.feature.diary.viewmodel.SettingsViewModel
import com.flowerdiary.ui.android.component.SettingsContent
import com.flowerdiary.ui.android.constants.UiConstants
import org.koin.androidx.compose.koinViewModel

/**
 * 설정 화면
 * SRP: 설정 화면 구성만 담당
 * - 상태 관리는 ViewModel에 위임
 * - 컨텐츠 렌더링은 SettingsContent에 위임
 * - 네비게이션은 외부에서 주입받음
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showResetDialog by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        viewModel.handleIntent(SettingsIntent.LoadSettings)
    }
    
    // 부수효과 처리
    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SettingsEffect.ShowResetConfirmation -> {
                    showResetDialog = true
                }
                is SettingsEffect.RequireRestart -> {
                    // 앱 재시작 필요 안내
                }
                else -> {}
            }
        }
    }
    
    Scaffold(
        topBar = {
            SettingsTopBar(
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        SettingsContent(
            state = state,
            showResetDialog = showResetDialog,
            onIntent = viewModel::handleIntent,
            onDismissResetDialog = {
                showResetDialog = false
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

/**
 * 설정 화면 상단 앱바
 * SRP: 설정 화면 상단바 UI만 담당
 */
@Composable
private fun SettingsTopBar(
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = UiConstants.Strings.SETTINGS_TITLE,
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
        }
    )
}