package com.pthw.food.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.food.R
import com.pthw.food.data.model.FilterType
import com.pthw.food.data.model.Food
import com.pthw.food.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by P.T.H.W on 19/07/2024.
 */
@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    var pageTitle = mutableStateOf(R.string.app_name)
        private set
    var foods = mutableStateOf<List<Food>>(emptyList())
        private set

    init {
        getAllFood()
    }

    fun getAllFood() {
        pageTitle.value = R.string.app_name
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
        pageTitle.value = filterType.title
        viewModelScope.launch {
            foods.value = repository.getFoodByType(filterType.type.toString())
        }
    }

}