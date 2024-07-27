package com.pthw.food.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.pthw.food.common.composable.permission.RequestNotificationPermissionDialog
import com.pthw.food.domain.model.AppThemeMode
import com.pthw.food.ui.home.HomePage
import com.pthw.food.ui.theme.FoodDiAppTheme
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
                // Home
                HomePage()

                // notification permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    RequestNotificationPermissionDialog()
                }
            }
        }
    }

}