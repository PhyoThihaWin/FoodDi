package com.pthw.food.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by P.T.H.W on 22/07/2024.
 */
@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    init {
        splashShowFlow.value = true
        viewModelScope.launch {
            delay(3000)
            splashShowFlow.value = false
        }
    }

}