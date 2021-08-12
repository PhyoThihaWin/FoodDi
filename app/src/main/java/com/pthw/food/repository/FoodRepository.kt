package com.pthw.food.repository

import com.pthw.food.database.FoodDao
import com.pthw.food.model.Food

class FoodRepository(val foodDao: FoodDao) {
    suspend fun getAllFood(): List<Food> = foodDao.getAllFood()

    suspend fun getSearchFood(word: String): List<Food> = foodDao.getSearchFood(word)

    suspend fun getFoodByType(type: String): List<Food> = foodDao.getFoodByType(type)
}