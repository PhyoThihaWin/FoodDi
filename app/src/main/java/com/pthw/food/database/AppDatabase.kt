package com.pthw.food.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pthw.food.model.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext, AppDatabase::class.java,
//                    "thousand_database.db"
//                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "fooddi.db"
                )
                    .createFromAsset("database/fooddi.db")
                    .fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}