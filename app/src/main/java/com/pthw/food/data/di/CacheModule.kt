package com.pthw.food.data.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.pthw.food.data.cache.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by P.T.H.W on 03/04/2024.
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "fooddi.db")
            .createFromAsset("database/fooddi.db")
            .fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun providePreferenceDataStore(@ApplicationContext context: Context) = context.dataStore
    private val Context.dataStore by preferencesDataStore("pref.foodDi")

}