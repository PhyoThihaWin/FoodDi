package com.pthw.food.utils

import com.pthw.food.R
import com.pthw.food.data.model.AppThemeMode
import com.pthw.food.data.model.FilterType
import com.pthw.food.data.model.Localization

object ConstantValue {
    var lang = "english"

    const val IMAGE_PATH =
        "https://firebasestorage.googleapis.com/v0/b/fooddi-3ca51.appspot.com/o/%s?alt=media"

    val filterList = listOf(
        FilterType(R.drawable.ic_letter_a, R.string.all, null),
        FilterType(R.drawable.ic_dish, R.string.food, "food"),
        FilterType(R.drawable.ic_apple, R.string.fruit, "fruit"),
        FilterType(R.drawable.ic_vegetable, R.string.vegetable, "vegetable"),
        FilterType(R.drawable.ic_meat, R.string.meat, "meat"),
        FilterType(R.drawable.ic_sandwich, R.string.snack, "snack"),
    )

    val settingList = listOf(
        Pair(R.drawable.ic_settings, R.string.chooseLanguage),
        Pair(R.drawable.ic_round_dark_mode, R.string.chooseThemeMode),
        Pair(R.drawable.ic_info, R.string.aboutApp),
        Pair(R.drawable.ic_more_app, R.string.moreApp),
    )

    val languageList = listOf(
        Localization(R.string.locale_english, Localization.ENGLISH),
        Localization(R.string.locale_myanmar, Localization.MYANMAR)
    )

    val appThemeModes = listOf(
        AppThemeMode(R.string.system_default_mode, AppThemeMode.SYSTEM_DEFAULT),
        AppThemeMode(R.string.light_mode, AppThemeMode.LIGHT_MODE),
        AppThemeMode(R.string.dark_mode, AppThemeMode.DARK_MODE)
    )
}