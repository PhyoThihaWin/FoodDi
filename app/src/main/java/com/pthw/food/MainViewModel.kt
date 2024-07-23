package com.pthw.food.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.food.data.repository.CacheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by P.T.H.W on 22/07/2024.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val cacheRepository: CacheRepository
) : ViewModel() {
    val appThemeMode = mutableStateOf(cacheRepository.getThemeModeNormal())
    var isSplashShow = mutableStateOf(false)
        private set

    init {
        startSplashScreen()
        getThemeMode()
    }

    private fun startSplashScreen() {
        viewModelScope.launch {
            delay(2000)
            isSplashShow.value = false
        }
    }

    private fun getThemeMode() {
        viewModelScope.launch {
            cacheRepository.getThemeMode().collectLatest {
                appThemeMode.value = it
            }
        }
    }
}