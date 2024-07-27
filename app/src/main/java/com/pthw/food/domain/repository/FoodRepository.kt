package com.pthw.food.domain.repository

import com.pthw.food.domain.model.Food

interface FoodRepository {
    suspend fun getAllFood(): List<Food>
    suspend fun getSearchFood(word: String): List<Food>
    suspend fun getFoodByType(type: String): List<Food>
}