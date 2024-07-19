package com.pthw.food.utils

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.pthw.food.R
import com.pthw.food.data.model.FilterType

object ConstantValue {
    var lang = "english"

    val filterList = listOf(
        FilterType(R.drawable.ic_letter_a, R.string.allE),
        FilterType(R.drawable.ic_dish, R.string.foodE),
        FilterType(R.drawable.ic_apple, R.string.fruitE),
        FilterType(R.drawable.ic_vegetable, R.string.vegetableE),
        FilterType(R.drawable.ic_meat, R.string.meatE),
        FilterType(R.drawable.ic_sandwich, R.string.snackE),
    )
}