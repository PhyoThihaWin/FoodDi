package com.pthw.food.common.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

/**
 * Created by P.T.H.W on 27/07/2024.
 */

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }

    return null
}