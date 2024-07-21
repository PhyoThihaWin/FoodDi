package com.pthw.food.data.repository

interface LanguageRepository {
    suspend fun getLanguage(): String
    suspend fun putLanguage(localeCode: String)
}