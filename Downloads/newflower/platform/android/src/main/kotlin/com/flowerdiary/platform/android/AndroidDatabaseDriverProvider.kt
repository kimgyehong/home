package com.flowerdiary.platform.android

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.flowerdiary.common.platform.DatabaseDriverProvider

/**
 * Android SQLDelight 드라이버 제공자
 * 플랫폼 중립성 유지 - Android 의존성은 이 모듈에만 격리
 */
actual class AndroidDatabaseDriverProvider(
    private val context: Context
) : DatabaseDriverProvider {

    actual override fun createDriver(
        schema: SqlDriver.Schema,
        name: String
    ): SqlDriver = AndroidSqliteDriver(
        schema = schema,
        context = context,
        name = name
    )
}
