package com.pthw.food.utils

import com.pthw.food.R
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
        Pair(R.drawable.ic_info, R.string.aboutApp),
        Pair(R.drawable.ic_more_app, R.string.moreApp),
    )

    val languageList = listOf(
        Localization(R.string.locale_english, Localization.ENGLISH),
        Localization(R.string.locale_myanmar, Localization.MYANMAR)
    )

    const val LIGHT_MODE = "light_mode"
    const val DARK_MODE = "dark_mode"
    const val SYSTEM_DEFAULT = "system_default"

    val appThemeModes = listOf(
        Pair(R.string.app_name, SYSTEM_DEFAULT),
        Pair(R.string.all, LIGHT_MODE),
        Pair(R.string.search, DARK_MODE)
    )
}