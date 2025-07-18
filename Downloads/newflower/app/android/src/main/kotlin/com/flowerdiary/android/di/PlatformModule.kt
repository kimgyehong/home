package com.flowerdiary.android.di

import com.flowerdiary.common.platform.AudioManager
import com.flowerdiary.common.platform.DatabaseDriverProvider
import com.flowerdiary.common.platform.FileStore
import com.flowerdiary.common.platform.PreferencesStore
import com.flowerdiary.common.platform.VideoPlayer
import com.flowerdiary.platform.android.AndroidAudioManager
import com.flowerdiary.platform.android.AndroidDatabaseDriverProvider
import com.flowerdiary.platform.android.AndroidFileStore
import com.flowerdiary.platform.android.AndroidPreferencesStore
import com.flowerdiary.platform.android.AndroidVideoPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * 플랫폼 의존성 DI 모듈
 * SRP: Android 플랫폼 구현체 제공만 담당
 * expect/actual 패턴의 actual 구현체들을 주입
 */
val platformModule = module {
    // Database Driver Provider
    single<DatabaseDriverProvider> {
        AndroidDatabaseDriverProvider(androidContext())
    }

    // File Store
    single<FileStore> {
        AndroidFileStore(androidContext())
    }

    // Preferences Store
    single<PreferencesStore> {
        AndroidPreferencesStore(androidContext())
    }

    // Audio Manager
    single<AudioManager> {
        AndroidAudioManager(androidContext())
    }

    // Video Player
    single<VideoPlayer> {
        AndroidVideoPlayer()
    }

    // DateTimeUtil과 Logger는 object이므로 직접 사용
    // DI로 주입할 필요 없음
}
