package com.pthw.food.data.cache

import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.pthw.food.data.cache.database.AppDatabase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by P.T.H.W on 10/08/2024.
 */

@RunWith(AndroidJUnit4::class)
@SmallTest
class FoodDaoTest {
    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database = inMemoryDatabaseBuilder(getApplicationContext(), AppDatabase::class.java).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun verify_getAllFood() = runTest {
        // insert
        database.foodDao().insertForTestingPurpose(SampleData.testFoods)

        // retrieve
        val foods = database.foodDao().getAllFood()

        Assert.assertTrue(foods.size > 1)
        Assert.assertEquals(SampleData.testFoods[1].oneMM, foods[1].oneMM)
    }

    @Test
    fun verify_getSearchFoods() = runBlocking {
        val word = "Jui"

        // insert
        database.foodDao().insertForTestingPurpose(SampleData.testFoods)

        val foods = database.foodDao().getSearchFood(word)

        Assert.assertEquals(2, foods.size)
    }

    @Test
    fun verify_getFoodByType() = runBlocking {
        val type = "meat"
        database.foodDao().insertForTestingPurpose(SampleData.testFoods)

        val foodsByType = database.foodDao().getFoodByType(type)

        Assert.assertEquals("Pork", foodsByType[0].dieEN)
    }

}