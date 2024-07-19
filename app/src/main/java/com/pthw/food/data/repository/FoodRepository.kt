package com.pthw.food.data.repository

import com.pthw.food.data.model.Food

interface FoodRepository {
    suspend fun getAllFood(): List<Food>
    suspend fun getSearchFood(word: String): List<Food>
    suspend fun getFoodByType(type: String): List<Food>
}