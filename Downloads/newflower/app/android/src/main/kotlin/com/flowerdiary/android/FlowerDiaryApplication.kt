package com.flowerdiary.android

import android.app.Application
import com.flowerdiary.android.di.appModule
import com.flowerdiary.android.di.dataModule
import com.flowerdiary.android.di.domainModule
import com.flowerdiary.android.di.featureModule
import com.flowerdiary.android.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * 꽃 다이어리 애플리케이션
 * SRP: 앱 초기화와 DI 설정만 담당
 * Koin을 사용한 의존성 주입 설정
 */
class FlowerDiaryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Koin DI 초기화
        startKoin {
            androidLogger()
            androidContext(this@FlowerDiaryApplication)
            modules(
                appModule,
                platformModule,
                dataModule,
                domainModule,
                featureModule
            )
        }

        // 탄생화 데이터 초기화는 MainActivity에서 처리
    }
}
