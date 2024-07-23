package com.pthw.food.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pthw.food.data.model.AppThemeMode
import com.pthw.food.theme.FoodDiAppTheme
import com.pthw.food.ui.main.HomePage
import com.pthw.food.utils.ConstantValue
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.isSplashShow.value
        }

        super.onCreate(savedInstanceState)

        setContent {
            FoodDiAppTheme(
                darkTheme = AppThemeMode.isDarkMode(viewModel.appThemeMode.collectAsState(initial = AppThemeMode.SYSTEM_DEFAULT).value),
            ) {
                HomePage()
            }
        }
    }
}