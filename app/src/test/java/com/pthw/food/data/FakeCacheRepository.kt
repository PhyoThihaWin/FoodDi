package com.pthw.food.data

import com.pthw.food.domain.model.AppThemeMode
import com.pthw.food.domain.model.Localization
import com.pthw.food.domain.repository.CacheRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by P.T.H.W on 10/08/2024.
 */
class FakeCacheRepository : CacheRepository {

    var language = Localization.ENGLISH
    var appThemeMode = AppThemeMode.LIGHT_MODE

    override fun getLanguageNormal(): String {
        return appThemeMode
    }

    override fun getLanguage(): Flow<String> {
        return flow {
            emit(language)
        }
    }

    override suspend fun putLanguage(localeCode: String) {
        language = localeCode
    }

    override fun getThemeModeNormal(): String {
        return language
    }

    override fun getThemeMode(): Flow<String> {
        return flow {
            emit(appThemeMode)
        }
    }

    override suspend fun putThemeMode(theme: String) {
        appThemeMode = theme
    }
}