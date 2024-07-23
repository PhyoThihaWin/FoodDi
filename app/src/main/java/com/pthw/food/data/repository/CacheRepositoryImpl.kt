package com.pthw.food.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pthw.food.data.model.AppThemeMode
import com.pthw.food.data.model.Localization
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : CacheRepository {

    private val PREF_KEY_LANGUAGE = stringPreferencesKey("language.key")
    private val PREF_KEY_THEME_MODE = stringPreferencesKey("theme_mode.key")

    override fun getLanguage(): Flow<String> {
        return dataStore.data.map {
            it[PREF_KEY_LANGUAGE] ?: Localization.ENGLISH
        }
    }

    override suspend fun putLanguage(localeCode: String) {
        dataStore.edit {
            it[PREF_KEY_LANGUAGE] = localeCode
        }
    }

    override fun getThemeMode(): Flow<String> {
        return dataStore.data.map {
            it[PREF_KEY_THEME_MODE] ?: AppThemeMode.SYSTEM_DEFAULT
        }
    }

    override suspend fun putThemeMode(theme: String) {
        dataStore.edit {
            it[PREF_KEY_THEME_MODE] = theme
        }
    }

}
