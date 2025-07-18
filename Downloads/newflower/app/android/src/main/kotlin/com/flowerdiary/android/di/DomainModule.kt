package com.flowerdiary.android.di

import com.flowerdiary.domain.usecase.diary.DeleteDiaryUseCase
import com.flowerdiary.domain.usecase.diary.GetDiariesUseCase
import com.flowerdiary.domain.usecase.diary.GetDiaryUseCase
import com.flowerdiary.domain.usecase.diary.SaveDiaryUseCase
import com.flowerdiary.domain.usecase.diary.SearchDiariesUseCase
import com.flowerdiary.domain.usecase.flower.GetBirthFlowerUseCase
import com.flowerdiary.domain.usecase.flower.GetBirthFlowersUseCase
import com.flowerdiary.domain.usecase.flower.RecommendFlowerUseCase
import com.flowerdiary.domain.usecase.flower.UnlockBirthFlowerUseCase
import org.koin.dsl.module

/**
 * 도메인 레이어 DI 모듈
 * SRP: 유스케이스 인스턴스 제공만 담당
 */
val domainModule = module {
    // Diary Use Cases
    factory { GetDiariesUseCase(get()) }
    factory { GetDiaryUseCase(get()) }
    factory { SaveDiaryUseCase(get()) }
    factory { DeleteDiaryUseCase(get()) }
    factory { SearchDiariesUseCase(get()) }

    // Birth Flower Use Cases
    factory { GetBirthFlowerUseCase(get()) }
    factory { GetBirthFlowersUseCase(get()) }
    factory { UnlockBirthFlowerUseCase(get()) }
    factory { RecommendFlowerUseCase(get()) }
}
