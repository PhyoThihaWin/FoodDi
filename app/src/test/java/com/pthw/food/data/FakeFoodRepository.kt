package com.pthw.food.data

import com.pthw.food.domain.model.Food
import com.pthw.food.domain.repository.FoodRepository

/**
 * Created by P.T.H.W on 10/08/2024.
 */
class FakeFoodRepository : FoodRepository {
    override suspend fun getAllFood(): List<Food> {
        return SampleData.testFoods
    }

    override suspend fun getSearchFood(word: String): List<Food> {
        return SampleData.testFoods.filter {
            it.oneEN.contains(word, true) ||
                    it.twoEN.contains(word, true) ||
                    it.oneMM.contains(word, true) ||
                    it.twoMM.contains(word, true)
        }
    }

    override suspend fun getFoodByType(type: String): List<Food> {
        return SampleData.testFoods.filter {
            it.type == type
        }
    }
}