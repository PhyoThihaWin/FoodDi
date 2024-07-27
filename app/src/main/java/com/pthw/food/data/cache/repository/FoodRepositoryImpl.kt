package com.pthw.food.data.cache.repository

import com.pthw.food.data.cache.database.AppDatabase
import com.pthw.food.domain.repository.FoodRepository
import com.pthw.food.utils.ConstantValue
import java.util.*
import javax.inject.Inject

/**
 * Created by P.T.H.W on 19/07/2024.
 */
class FoodRepositoryImpl @Inject constructor(
    private val database: AppDatabase
) : FoodRepository {
    override suspend fun getAllFood() = database.foodDao().getAllFood().map {
        it.copy(
            imgOne = it.imgOne.firebaseImage(),
            imgTwo = it.imgTwo.firebaseImage()
        )
    }

    override suspend fun getSearchFood(word: String) = database.foodDao().getSearchFood(word).map {
        it.copy(
            imgOne = it.imgOne.firebaseImage(),
            imgTwo = it.imgTwo.firebaseImage()
        )
    }

    override suspend fun getFoodByType(type: String) = database.foodDao().getFoodByType(type).map {
        it.copy(
            imgOne = it.imgOne.firebaseImage(),
            imgTwo = it.imgTwo.firebaseImage()
        )
    }

    private fun String.firebaseImage(): String {
        return String.format(Locale.ENGLISH, ConstantValue.IMAGE_PATH, "fooddi_photo%2F$this")
    }
}