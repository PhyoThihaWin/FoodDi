package com.pthw.food.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pthw.food.database.AppDatabase
import com.pthw.food.model.Food
import com.pthw.food.repository.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository: FoodRepository
    private val _allFood = MutableLiveData<List<Food>>()
    val allFood: LiveData<List<Food>> get() = _allFood

    init {
        val dreamDao = AppDatabase.getDatabase(application).foodDao()
        mRepository = FoodRepository(dreamDao)
    }

    fun getAllFood() {
        viewModelScope.launch {
            _allFood.postValue(mRepository.getAllFood())
        }
    }

    fun getSearchFood(word: String) {
        viewModelScope.launch {
            _allFood.postValue(mRepository.getSearchFood(word))
        }
    }

    fun getFoodByType(type: String) {
        viewModelScope.launch {
            _allFood.postValue(mRepository.getFoodByType(type))
        }
    }
}