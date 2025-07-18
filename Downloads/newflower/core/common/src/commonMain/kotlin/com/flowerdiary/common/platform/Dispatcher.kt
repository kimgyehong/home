package com.flowerdiary.common.platform

import kotlinx.coroutines.CoroutineDispatcher

/**
 * 플랫폼 중립적 Dispatcher 제공자 인터페이스
 * platform 모듈에서 구현하여 DI로 주입
 */
interface DispatcherProvider {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}
