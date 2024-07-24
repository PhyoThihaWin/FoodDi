package com.pthw.food.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

/**
 * Created by P.T.H.W on 24/07/2024.
 */

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }

    return null
}