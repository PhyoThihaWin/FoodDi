package com.pthw.food.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.pthw.food.data.cache.database.AppDatabase
import com.pthw.food.data.cache.repository.FoodRepositoryImpl
import com.pthw.food.domain.model.Food
import com.pthw.food.domain.repository.FoodRepository
import kotlinx.coroutines.runBlocking
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

        runBlocking {
            database.foodDao().insertForTestingPurpose(testFoods)
        }
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun verify_getAllFood() = runBlocking {
        val foods = foodRepository.getAllFood()
        Assert.assertTrue(foods.size > 1)
    }

    @Test
    fun verify_getSearchFoods() = runBlocking {
        val word = "Jui"
        val foods = foodRepository.getSearchFood(word)
        Assert.assertEquals(2, foods.size)
    }

    @Test
    fun verify_getFoodByType() = runBlocking {
        val type = "meat"
        val foodsByType = foodRepository.getFoodByType(type)
        Assert.assertEquals("Pork", foodsByType[0].dieEN)
    }


    companion object {
        val testFoods = listOf(
            Food(
                id = 1,
                oneMM = "Delicious Apple",
                twoMM = "Juicy Orange",
                dieMM = "Fresh Banana",
                oneEN = "Apple",
                twoEN = "Orange",
                dieEN = "Banana",
                type = "fruit",
                imgOne = "http://example.com/images/apple.jpg",
                imgTwo = "http://example.com/images/orange.jpg"
            ),
            Food(
                id = 2,
                oneMM = "Crunchy Carrot",
                twoMM = "Spicy Pepper",
                dieMM = "Sweet Corn",
                oneEN = "Carrot",
                twoEN = "Pepper",
                dieEN = "Corn",
                type = "vegetable",
                imgOne = "http://example.com/images/carrot.jpg",
                imgTwo = "http://example.com/images/pepper.jpg"
            ),
            Food(
                id = 3,
                oneMM = "Savory Chicken",
                twoMM = "Juicy Tender Beef",
                dieMM = "Juicy Pork",
                oneEN = "Chicken",
                twoEN = "Beef",
                dieEN = "Pork",
                type = "meat",
                imgOne = "http://example.com/images/chicken.jpg",
                imgTwo = "http://example.com/images/beef.jpg"
            )
        )
    }

}