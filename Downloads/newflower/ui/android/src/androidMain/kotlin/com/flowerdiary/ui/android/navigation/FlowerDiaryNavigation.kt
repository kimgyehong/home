package com.flowerdiary.ui.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flowerdiary.ui.android.component.VideoOpening
import com.flowerdiary.ui.android.screen.*

/**
 * 꽃 다이어리 네비게이션 호스트
 * SRP: 화면 간 네비게이션만 담당
 * Compose Navigation으로 화면 전환 관리
 */
@Composable
fun FlowerDiaryNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = FlowerDiaryDestination.Opening.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 오프닝 화면
        composable(FlowerDiaryDestination.Opening.route) {
            VideoOpening(
                onComplete = {
                    navController.navigate(FlowerDiaryDestination.Title.route) {
                        popUpTo(FlowerDiaryDestination.Opening.route) { inclusive = true }
                    }
                }
            )
        }
        
        // 타이틀 화면
        composable(FlowerDiaryDestination.Title.route) {
            TitleScreen(
                onNavigateToMain = {
                    navController.navigate(FlowerDiaryDestination.Main.route) {
                        popUpTo(FlowerDiaryDestination.Title.route) { inclusive = true }
                    }
                }
            )
        }
        
        // 메인 화면
        composable(FlowerDiaryDestination.Main.route) {
            MainScreen(
                onNavigateToDiary = {
                    navController.navigate(FlowerDiaryDestination.DiaryList.route)
                },
                onNavigateToCollection = {
                    navController.navigate(FlowerDiaryDestination.Collection.route)
                },
                onNavigateToSettings = {
                    navController.navigate(FlowerDiaryDestination.Settings.route)
                }
            )
        }
        
        // 일기 목록 화면
        composable(FlowerDiaryDestination.DiaryList.route) {
            DiaryListScreen(
                onNavigateToEditor = { diaryId ->
                    val route = if (diaryId != null) {
                        "${FlowerDiaryDestination.DiaryEditor.route}?${DiaryEditorArgs.DIARY_ID}=$diaryId"
                    } else {
                        FlowerDiaryDestination.DiaryEditor.route
                    }
                    navController.navigate(route)
                },
                onNavigateToCollection = {
                    navController.navigate(FlowerDiaryDestination.Collection.route)
                }
            )
        }
        
        // 일기 편집 화면
        composable(
            route = "${FlowerDiaryDestination.DiaryEditor.route}?${DiaryEditorArgs.DIARY_ID}={${DiaryEditorArgs.DIARY_ID}}",
        ) { backStackEntry ->
            val diaryId = backStackEntry.arguments?.getString(DiaryEditorArgs.DIARY_ID)
            DiaryEditorScreen(
                diaryId = diaryId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // 도감 화면
        composable(FlowerDiaryDestination.Collection.route) {
            CollectionScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // 설정 화면
        composable(FlowerDiaryDestination.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

/**
 * 네비게이션 목적지
 * SRP: 각 화면의 라우트 정의만 담당
 */
sealed class FlowerDiaryDestination(val route: String) {
    object Opening : FlowerDiaryDestination("opening")
    object Title : FlowerDiaryDestination("title")
    object Main : FlowerDiaryDestination("main")
    object DiaryList : FlowerDiaryDestination("diary_list")
    object DiaryEditor : FlowerDiaryDestination("diary_editor")
    object Collection : FlowerDiaryDestination("collection")
    object Settings : FlowerDiaryDestination("settings")
}

/**
 * 일기 편집 화면 인자
 */
object DiaryEditorArgs {
    const val DIARY_ID = "diaryId"
}