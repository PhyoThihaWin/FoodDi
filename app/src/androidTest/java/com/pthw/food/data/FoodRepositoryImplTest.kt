package com.pthw.food.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.pthw.food.data.cache.SampleData
import com.pthw.food.data.cache.database.AppDatabase
import com.pthw.food.data.cache.repository.FoodRepositoryImpl
import com.pthw.food.domain.repository.FoodRepository
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class FoodRepositoryImplTest {

    private lateinit var context: Context
    private lateinit var database: AppDatabase
    private lateinit var foodRepository: FoodRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()
        foodRepository = FoodRepositoryImpl(database)

        runTest {
            database.foodDao().insertForTestingPurpose(SampleData.testFoods)
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun verify_getAllFood() = runTest {
        val foods = foodRepository.getAllFood()
        Assert.assertTrue(foods.size > 1)
    }

    @Test
    fun verify_getSearchFoods() = runTest {
        val word = "Jui"
        val foods = foodRepository.getSearchFood(word)
        Assert.assertEquals(2, foods.size)
    }

    @Test
    fun verify_getFoodByType() = runTest {
        val type = "meat"
        val foodsByType = foodRepository.getFoodByType(type)
        Assert.assertEquals("Pork", foodsByType[0].dieEN)
    }

}