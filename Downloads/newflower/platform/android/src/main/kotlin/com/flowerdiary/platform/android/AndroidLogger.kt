package com.flowerdiary.platform.android

import android.util.Log
import com.flowerdiary.common.utils.Logger

/**
 * Android 플랫폼 로거 구현
 * Android Log 시스템 사용
 */
object AndroidLogger : Logger {

    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun info(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun warning(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.e(tag, message, throwable)
        } else {
            Log.e(tag, message)
        }
    }
}
