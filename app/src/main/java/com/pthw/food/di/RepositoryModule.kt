package com.pthw.food.di

import com.pthw.food.data.repository.FoodRepository
import com.pthw.food.data.repository.FoodRepositoryImpl
import com.pthw.food.data.repository.CacheRepository
import com.pthw.food.data.repository.CacheRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by P.T.H.W on 02/04/2024.
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindFoodRepository(repositoryImpl: FoodRepositoryImpl): FoodRepository

    @Binds
    abstract fun bindLanguageRepository(repositoryImpl: CacheRepositoryImpl): CacheRepository
}