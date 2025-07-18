package com.flowerdiary.platform.android

import com.flowerdiary.common.platform.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Android 플랫폼 Dispatcher 구현
 */
class AndroidDispatcherProvider : DispatcherProvider {
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}
