package com.flowerdiary.android.di

import org.koin.dsl.module

/**
 * 앱 레벨 DI 모듈
 * SRP: 앱 전역에서 사용되는 의존성만 정의
 */
val appModule = module {
    // 앱 전역 의존성들
    // 예: Analytics, Crash Reporter 등
}
