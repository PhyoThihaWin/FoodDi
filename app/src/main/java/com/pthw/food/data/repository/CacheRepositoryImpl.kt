package com.pthw.food.data.repository

import android.content.Context
import androidx.core.content.edit
import com.pthw.food.data.model.Localization
import com.pthw.food.utils.ConstantValue
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : CacheRepository {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "pref.foodDi"
        private const val PREF_KEY_LANGUAGE = "language.key"
        private const val PREF_KEY_THEME_MODE = "theme_mode.key"
    }

    override suspend fun getLanguage(): String {
        return sharedPreferences.getString(PREF_KEY_LANGUAGE, Localization.ENGLISH)
            ?: Localization.ENGLISH
    }

    override suspend fun putLanguage(localeCode: String) {
        sharedPreferences.edit {
            putString(PREF_KEY_LANGUAGE, localeCode)
        }
    }

    override suspend fun getThemeMode(): String {
        return sharedPreferences.getString(PREF_KEY_THEME_MODE, null)
            ?: ConstantValue.SYSTEM_DEFAULT
    }

    override suspend fun putThemeMode(theme: String) {
        sharedPreferences.edit {
            putString(PREF_KEY_THEME_MODE, theme)
        }
    }

}
