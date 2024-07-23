package com.pthw.food

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pthw.food.data.model.AppThemeMode
import com.pthw.food.ui.MainViewModel
import com.pthw.food.ui.theme.FoodDiAppTheme
import com.pthw.food.ui.home.HomePage
import dagger.hilt.android.AndroidEntryPoint

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
                darkTheme = AppThemeMode.isDarkMode(viewModel.appThemeMode.value),
            ) {
                HomePage()
            }
        }
    }
}