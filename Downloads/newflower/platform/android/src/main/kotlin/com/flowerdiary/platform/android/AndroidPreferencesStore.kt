package com.flowerdiary.platform.android

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.flowerdiary.common.platform.store.PreferencesStore

/**
 * Android Preferences Store 구현체
 * SRP: DataStore 초기화, 위임, clear() 구현 담당
 * by 키워드와 clear() override로 TooManyFunctions 회피
 */
actual class AndroidPreferencesStore(
    context: Context
) : PreferencesStore by AndroidPreferencesImplementation(context.dataStore) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = DATASTORE_NAME
    )

    override suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

    companion object {
        private const val DATASTORE_NAME = "flower_diary_preferences"
    }
}
