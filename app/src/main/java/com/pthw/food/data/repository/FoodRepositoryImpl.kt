package com.pthw.food.data.repository

import com.pthw.food.data.database.AppDatabase
import com.pthw.food.data.database.FoodDao
import javax.inject.Inject

/**
 * Created by P.T.H.W on 19/07/2024.
 */
class FoodRepositoryImpl @Inject constructor(
    private val database: AppDatabase
) : FoodRepository {
    override suspend fun getAllFood() = database.foodDao().getAllFood()
    override suspend fun getSearchFood(word: String) = database.foodDao().getSearchFood(word)
    override suspend fun getFoodByType(type: String) = database.foodDao().getFoodByType(type)
}