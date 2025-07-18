package com.flowerdiary.app.di

import com.flowerdiary.domain.usecase.diary.*
import com.flowerdiary.domain.usecase.flower.*
import com.flowerdiary.domain.repository.DiaryRepository
import com.flowerdiary.domain.repository.BirthFlowerRepository
import com.flowerdiary.domain.repository.SettingsRepository
import com.flowerdiary.data.repository.SqlDelightDiaryRepository
import com.flowerdiary.data.repository.SqlDelightBirthFlowerRepository
import com.flowerdiary.data.repository.DataStoreSettingsRepository
import com.flowerdiary.data.FlowerDiaryDatabase
import com.flowerdiary.common.data.UnitOfWork
import com.flowerdiary.data.SqlDelightUnitOfWork
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * 플랫폼 중립적인 앱 DI 설정
 * SRP: 의존성 주입 설정만 담당
 * 플랫폼 의존성 없음
 */
object SharedAppModule {
    
    /**
     * Domain 계층 모듈
     */
    val domainModule = module {
        // Diary UseCases
        single { SaveDiaryUseCase(get(), get()) }
        single { GetDiariesUseCase(get()) }
        single { GetDiariesUseCaseRefactored(get()) }
        single { GetDiaryByIdUseCase(get()) }
        single { DeleteDiaryUseCase(get(), get()) }
        single { SearchDiariesUseCase(get()) }
        
        // Flower UseCases
        single { GetBirthFlowerUseCase(get()) }
        single { GetTodayFlowerUseCase(get()) }
        single { UnlockBirthFlowerUseCase(get(), get()) }
        single { GetUnlockedFlowersUseCase(get()) }
        single { RecommendFlowerUseCase(get()) }
    }
    
    /**
     * Data 계층 모듈
     */
    val dataModule = module {
        // Repositories
        single<DiaryRepository> { SqlDelightDiaryRepository(get(), get()) }
        single<BirthFlowerRepository> { SqlDelightBirthFlowerRepository(get(), get()) }
        single<SettingsRepository> { DataStoreSettingsRepository(get()) }
        
        // UnitOfWork
        single<UnitOfWork> { SqlDelightUnitOfWork(get()) }
    }
    
    /**
     * 모든 모듈 조합
     */
    fun getAllModules(): List<Module> = listOf(
        domainModule,
        dataModule
    )
}