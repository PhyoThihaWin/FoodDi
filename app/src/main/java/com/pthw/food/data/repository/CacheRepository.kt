package com.pthw.food.data.repository

import kotlinx.coroutines.flow.Flow

interface CacheRepository {
    fun getLanguage(): Flow<String>
    suspend fun putLanguage(localeCode: String)

    fun getThemeMode(): Flow<String>
    suspend fun putThemeMode(theme: String)
}