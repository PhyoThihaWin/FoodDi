package com.pthw.food.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.food.data.model.Food
import com.pthw.food.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by P.T.H.W on 19/07/2024.
 */
@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    var foods = mutableStateOf<List<Food>>(emptyList())
        private set

    init {
        getAllFood()
    }

    fun getAllFood() {
        viewModelScope.launch {
            foods.value = repository.getAllFood()
        }
    }

    fun getSearchFood(word: String) {
        viewModelScope.launch {
            foods.value = repository.getSearchFood(word)
        }
    }

    fun getFoodByType(type: String) {
        viewModelScope.launch {
            foods.value = repository.getFoodByType(type)
        }
    }
}