package com.pthw.food

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by P.T.H.W on 18/07/2024.
 */

@HiltAndroidApp
class FoodDiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }
}

private fun setupTimber() {
    if (BuildConfig.DEBUG) {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                val prefix = super.createStackElementTag(element)?.substringBefore("$") ?: "Timber"
                return String.format("C:%s, L:%s", prefix, element.lineNumber, element.methodName)
            }
        })
    }
}