package com.pthw.food

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.integration.IntegrationHelper
import com.pthw.food.data.model.AppThemeMode
import com.pthw.food.ui.home.HomePage
import com.pthw.food.ui.theme.FoodDiAppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

private const val APP_KEY = "1f12b87cd"


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.isSplashShow.value
        }
        setupIronSourceSdk()
        super.onCreate(savedInstanceState)

        setContent {
            FoodDiAppTheme(
                darkTheme = AppThemeMode.isDarkMode(viewModel.appThemeMode.value),
            ) {
                HomePage()
            }
        }
    }

    private fun setupIronSourceSdk() {
        // The integrationHelper is used to validate the integration.
        // Remove the integrationHelper before going live!
        if (BuildConfig.DEBUG) {
            IntegrationHelper.validateIntegration(this)
        }

        // To enable the test suite in your app, call the setMetaData API before setting the init:
        // IronSource.setMetaData("is_test_suite", "enable")
        // Launch textSuite after init
        // IronSource.launchTestSuite(this)

        IronSource.init(this, APP_KEY) {
            Timber.i("IronSource initialized!")
        }
    }

    override fun onResume() {
        super.onResume()
        IronSource.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        IronSource.onPause(this)
    }
}