package com.pthw.food.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pthw.food.domain.model.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}