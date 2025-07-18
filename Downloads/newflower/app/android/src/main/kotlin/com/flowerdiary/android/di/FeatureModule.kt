package com.flowerdiary.android.di

import com.flowerdiary.feature.diary.facade.DiaryFacade
import com.flowerdiary.feature.diary.viewmodel.CollectionViewModel
import com.flowerdiary.feature.diary.viewmodel.DiaryEditorViewModel
import com.flowerdiary.feature.diary.viewmodel.DiaryListViewModel
import com.flowerdiary.feature.diary.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Feature 레이어 DI 모듈
 * SRP: ViewModel과 Facade 인스턴스 제공만 담당
 */
val featureModule = module {
    // Facade
    single {
        DiaryFacade(
            unitOfWork = get(),
            diaryRepository = get(),
            birthFlowerRepository = get(),
            saveDiaryUseCase = get(),
            unlockBirthFlowerUseCase = get()
        )
    }

    // ViewModels
    viewModel {
        DiaryListViewModel(
            getDiariesUseCase = get(),
            deleteDiaryUseCase = get()
        )
    }

    viewModel {
        DiaryEditorViewModel(
            getDiaryUseCase = get(),
            saveDiaryUseCase = get(),
            getBirthFlowerUseCase = get(),
            recommendFlowerUseCase = get()
        )
    }

    viewModel {
        CollectionViewModel(
            getBirthFlowerUseCase = get(),
            unlockBirthFlowerUseCase = get(),
            birthFlowerRepository = get()
        )
    }

    viewModel {
        SettingsViewModel(
            preferencesStore = get(),
            audioManager = get(),
            fileStore = get()
        )
    }
}
