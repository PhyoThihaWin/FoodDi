package com.pthw.food.ui.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.food.R
import com.pthw.food.data.model.FilterType
import com.pthw.food.data.model.Food
import com.pthw.food.data.repository.FoodRepository
import com.pthw.food.data.repository.CacheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by P.T.H.W on 19/07/2024.
 */
@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val cacheRepository: CacheRepository
) : ViewModel() {
    val appThemeMode = mutableStateOf(cacheRepository.getThemeModeNormal())
    val currentLanguage = cacheRepository.getLanguage()

    var pageTitle = mutableIntStateOf(R.string.app_name)
        private set
    var foods = mutableStateOf<List<Food>>(emptyList())
        private set

    init {
        getThemeMode()
        getAllFood()
    }

    fun getAllFood() {
        pageTitle.intValue = R.string.app_name
        viewModelScope.launch {
            foods.value = repository.getAllFood()
        }
    }

    fun getSearchFoods(word: String) {
        viewModelScope.launch {
            val data = repository.getSearchFood(word)
            Timber.i("search: ${data.count()}")
            foods.value = data
        }
    }

    fun getFoodsByType(filterType: FilterType) {
        pageTitle.intValue = filterType.title
        viewModelScope.launch {
            foods.value = repository.getFoodByType(filterType.type.toString())
        }
    }

    fun updateLanguageCache(localeCode: String) {
        viewModelScope.launch {
            cacheRepository.putLanguage(localeCode)
        }
    }

    fun updateCachedThemeMode(theme: String) {
        viewModelScope.launch {
            cacheRepository.putThemeMode(theme)
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