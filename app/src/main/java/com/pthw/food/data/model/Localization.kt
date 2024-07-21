package com.pthw.food.data.model

import androidx.annotation.StringRes

/**
 * Created by P.T.H.W on 21/07/2024.
 */

data class Localization(
    @StringRes val title: Int,
    val localeCode: String
) {
    companion object {
        const val ENGLISH = "en"
        const val MYANMAR = "my"
    }
}

