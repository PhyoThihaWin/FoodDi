package com.pthw.food.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.food.data.repository.CacheRepository
import com.pthw.food.utils.ConstantValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by P.T.H.W on 22/07/2024.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val cacheRepository: CacheRepository
) : ViewModel() {

    var isSplashShow = mutableStateOf(true)
        private set

    var appThemeMode = mutableStateOf(ConstantValue.SYSTEM_DEFAULT)
        private set

    init {
        // splash
        startSplashScreen()

        // theme
        getCachedThemeMode()
    }

    private fun startSplashScreen() {
        viewModelScope.launch {
            delay(3000)
            isSplashShow.value = false
        }
    }

    fun updateCachedThemeMode(theme: String) {
        viewModelScope.launch {
            cacheRepository.putThemeMode(theme)
            getCachedThemeMode()
        }
    }

    private fun getCachedThemeMode() {
        viewModelScope.launch {
            appThemeMode.value = cacheRepository.getThemeMode()
        }
    }
}