package com.pthw.food.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.food.data.model.Food
import com.pthw.food.data.repository.FoodRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoodViewModel @Inject constructor(
    private val repository: FoodRepository
) : ViewModel() {

    private val _allFood = MutableLiveData<List<Food>>()
    val allFood: LiveData<List<Food>> get() = _allFood

    fun getAllFood() {
        viewModelScope.launch {
//            _allFood.postValue(repository.getAllFood())
        }
    }

    fun getSearchFood(word: String) {
        viewModelScope.launch {
//            _allFood.postValue(repository.getSearchFood(word))
        }
    }

    fun getFoodByType(type: String) {
        viewModelScope.launch {
//            _allFood.postValue(repository.getFoodByType(type))
        }
    }
}