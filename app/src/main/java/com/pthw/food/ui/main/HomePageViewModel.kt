package com.pthw.food.ui.main

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.food.R
import com.pthw.food.data.model.FilterType
import com.pthw.food.data.model.Food
import com.pthw.food.data.model.Localization
import com.pthw.food.data.repository.FoodRepository
import com.pthw.food.data.repository.LanguageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by P.T.H.W on 19/07/2024.
 */
@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val languageRepository: LanguageRepository
) : ViewModel() {

    var pageTitle = mutableIntStateOf(R.string.app_name)
        private set
    var foods = mutableStateOf<List<Food>>(emptyList())
        private set
    var currentLanguage = mutableStateOf(Localization.ENGLISH)
        private set

    init {
        getLanguageCache()
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
            languageRepository.putLanguage(localeCode)
            getLanguageCache()
        }
    }

    private fun getLanguageCache() {
        viewModelScope.launch {
            currentLanguage.value = languageRepository.getLanguage()
        }
    }

}