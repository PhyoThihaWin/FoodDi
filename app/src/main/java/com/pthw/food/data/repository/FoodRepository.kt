package com.pthw.food.data.repository

import com.pthw.food.data.database.FoodDao
import com.pthw.food.data.model.Food

class FoodRepository(val foodDao: FoodDao) {
    suspend fun getAllFood(): List<Food> = foodDao.getAllFood()

    suspend fun getSearchFood(word: String): List<Food> = foodDao.getSearchFood(word)

    suspend fun getFoodByType(type: String): List<Food> = foodDao.getFoodByType(type)
}