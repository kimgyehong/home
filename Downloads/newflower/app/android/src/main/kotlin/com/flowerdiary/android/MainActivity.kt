package com.flowerdiary.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.data.database.BirthFlowerDatabaseInitializer
import com.flowerdiary.ui.android.component.LoadingAnimation
import com.flowerdiary.ui.android.navigation.FlowerDiaryNavHost
import com.flowerdiary.ui.android.theme.FlowerDiaryTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * 메인 액티비티
 * SRP: 앱 진입점과 Compose UI 설정만 담당
 * 나머지 로직은 각 레이어에 위임
 */
class MainActivity : ComponentActivity() {

    private val birthFlowerInitializer: BirthFlowerDatabaseInitializer by inject()
    private val preferencesStore: PreferencesStore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindow()
        setupCompose()
    }

    private fun setupWindow() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun setupCompose() {
        setContent {
            FlowerDiaryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    appContent()
                }
            }
        }
    }

    @Composable
    private fun appContent() {
        var isInitialized by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            initializeAppData { isInitialized = true }
        }

        if (isInitialized) {
            FlowerDiaryNavHost()
        } else {
            loadingContent()
        }
    }

    private suspend fun initializeAppData(onComplete: () -> Unit) {
        lifecycleScope.launch {
            val initialized = preferencesStore.getBoolean(KEY_BIRTH_FLOWER_INITIALIZED, false)
            if (!initialized) {
                birthFlowerInitializer.initialize()
                preferencesStore.putBoolean(KEY_BIRTH_FLOWER_INITIALIZED, true)
            }
            onComplete()
        }
    }

    @Composable
    private fun loadingContent() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingAnimation(loadingText = "탄생화 데이터를 준비하고 있어요...")
        }
    }

    companion object {
        private const val KEY_BIRTH_FLOWER_INITIALIZED = "birth_flower_initialized"
    }
}
