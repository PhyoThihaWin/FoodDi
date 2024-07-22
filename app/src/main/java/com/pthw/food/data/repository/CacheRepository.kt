package com.pthw.food.data.repository

interface CacheRepository {
    suspend fun getLanguage(): String
    suspend fun putLanguage(localeCode: String)

    suspend fun getThemeMode(): String
    suspend fun putThemeMode(theme: String)
}