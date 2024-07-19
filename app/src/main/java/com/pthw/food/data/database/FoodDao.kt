package com.pthw.food.data.database

import androidx.room.Dao
import androidx.room.Query
import com.pthw.food.data.model.Food


@Dao
interface FoodDao {
    @Query("SELECT * from Food where oneMM LIKE '%'||:word||'%' or twoMM LIKE '%'||:word||'%' or oneEN LIKE '%'||:word||'%' or twoEN LIKE '%'||:word||'%'")
    suspend fun getSearchFood(word: String): List<Food>

    @Query("SELECT * FROM Food WHERE type LIKE '%'||:type||'%'")
    suspend fun getFoodByType(type: String): List<Food>

    @Query("SELECT * FROM Food")
    suspend fun getAllFood(): List<Food>
}