package com.pthw.food.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class PrefManager(private val context: Context) {
    var preference: SharedPreferences
    var editor: Editor

    // shared pref mode
    var PRIVATE_MODE = 0

    // Shared preferences file name
    private val PREF_NAME = "pthw-foodi"
    private val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    private val TOKEN = "token"
    private val LANG = "Lang"

    init {
        preference = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = preference.edit()
    }

    fun setFirstTimeLaunch(isFirstTime: Boolean) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
        editor.commit()
    }

    fun isFirstTimeLaunch(): Boolean {
        return preference.getBoolean(IS_FIRST_TIME_LAUNCH, true)
    }

    fun setToken(token: String) {
        editor.putString(TOKEN, token)
        editor.commit()
    }

    fun getToken(): String {
        return preference.getString(TOKEN, "")!!
    }

    fun setLanguage(lang: String) {
        editor.putString(LANG, lang)
        editor.commit()
    }

    fun getLanguage(): String {
        return preference.getString(LANG, "en")!!
    }


    fun autoConvert(unicodeInput: String): String {
        return when (getLanguage()) {
            "en", "my" -> unicodeInput
            else -> Rabbit.uni2zg(unicodeInput)
        }
    }

}