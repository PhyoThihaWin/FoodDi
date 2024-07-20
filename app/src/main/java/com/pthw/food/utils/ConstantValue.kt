package com.pthw.food.utils

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.pthw.food.R
import com.pthw.food.data.model.FilterType

object ConstantValue {
    var lang = "english"

    val filterList = listOf(
        FilterType(R.drawable.ic_letter_a, R.string.allE, null),
        FilterType(R.drawable.ic_dish, R.string.foodE, "food"),
        FilterType(R.drawable.ic_apple, R.string.fruitE, "fruit"),
        FilterType(R.drawable.ic_vegetable, R.string.vegetableE, "vegetable"),
        FilterType(R.drawable.ic_meat, R.string.meatE, "meat"),
        FilterType(R.drawable.ic_sandwich, R.string.snackE, "snack"),
    )
}