package com.flowerdiary.platform.ios

import com.flowerdiary.common.platform.DatabaseDriverProvider
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration

/**
 * iOS SQLDelight 드라이버 제공자
 * SRP: iOS 플랫폼의 SQLite 드라이버 생성만 담당
 * 플랫폼 중립적 인터페이스의 iOS actual 구현
 */
actual class IOSDatabaseDriverProvider : DatabaseDriverProvider {
    
    override fun createDriver(schema: SqlDriver.Schema, databaseName: String): SqlDriver {
        return NativeSqliteDriver(
            schema = schema,
            name = databaseName,
            onConfiguration = { config ->
                config.copy(
                    encryptionConfig = DatabaseConfiguration.Encryption.Unencrypted,
                    journalMode = DatabaseConfiguration.JournalMode.WAL,
                    synchronousFlag = DatabaseConfiguration.SynchronousFlag.NORMAL
                )
            }
        )
    }
}