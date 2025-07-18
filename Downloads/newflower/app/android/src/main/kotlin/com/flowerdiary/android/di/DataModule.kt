package com.flowerdiary.android.di

import com.flowerdiary.common.data.UnitOfWork
import com.flowerdiary.data.SqlDelightUnitOfWork
import com.flowerdiary.data.database.BirthFlowerDatabaseInitializer
import com.flowerdiary.data.database.init.BirthFlowerColorProvider
import com.flowerdiary.data.database.init.BirthFlowerDataParser
import com.flowerdiary.data.database.init.BirthFlowerEntryMapper
import com.flowerdiary.data.database.init.BirthFlowerImagePathProvider
import com.flowerdiary.data.database.init.BirthFlowerPersistenceService
import com.flowerdiary.data.database.init.BirthFlowerValidator
import com.flowerdiary.data.repository.SqlDelightBirthFlowerRepository
import com.flowerdiary.data.repository.SqlDelightDiaryRepository
import com.flowerdiary.domain.repository.BirthFlowerRepository
import com.flowerdiary.domain.repository.DiaryRepository
import org.koin.dsl.module

/**
 * 데이터 레이어 DI 모듈
 * SRP: 데이터 소스와 레포지토리 구현체 제공만 담당
 */
val dataModule = module {
    // Unit of Work
    single<UnitOfWork> {
        SqlDelightUnitOfWork(get())
    }

    // Repositories
    single<DiaryRepository> {
        SqlDelightDiaryRepository(get())
    }

    single<BirthFlowerRepository> {
        SqlDelightBirthFlowerRepository(get())
    }

    // 탄생화 초기화 관련
    single { BirthFlowerDataParser() }
    single { BirthFlowerValidator() }
    single { BirthFlowerColorProvider() }
    single { BirthFlowerImagePathProvider() }
    single { BirthFlowerEntryMapper(get(), get()) }
    single { BirthFlowerPersistenceService(get()) }
    single {
        BirthFlowerDatabaseInitializer(
            parser = get(),
            validator = get(),
            mapper = get(),
            persistenceService = get()
        )
    }
}
