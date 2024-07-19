package com.pthw.food.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Created by P.T.H.W on 19/07/2024.
 */
data class FilterType(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
)
