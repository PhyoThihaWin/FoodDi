package com.pthw.food.data.repository

import android.content.Context
import androidx.core.content.edit
import com.pthw.food.data.model.Localization
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : LanguageRepository {
    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "pref.user"
        private const val PREF_KEY_LANGUAGE = "language.key"
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

}
