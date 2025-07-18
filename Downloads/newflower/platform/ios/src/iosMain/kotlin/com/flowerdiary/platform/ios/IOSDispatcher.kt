package com.flowerdiary.platform.ios

import com.flowerdiary.common.platform.DefaultDispatcher
import com.flowerdiary.common.platform.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * iOS 플랫폼 Dispatcher 구현
 * iOS는 Main 스레드가 UI 스레드이므로 백그라운드 작업을 위해 Default 사용
 */
actual val DefaultDispatcher: CoroutineDispatcher = Dispatchers.Default

// iOS는 IO Dispatcher가 없으므로 Default 사용
actual val IODispatcher: CoroutineDispatcher = Dispatchers.Default