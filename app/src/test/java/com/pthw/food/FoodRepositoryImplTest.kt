package com.pthw.food

import com.pthw.food.data.cache.database.AppDatabase
import com.pthw.food.data.cache.repository.FoodRepositoryImpl
import com.pthw.food.domain.model.Food
import com.pthw.food.domain.repository.FoodRepository
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by P.T.H.W on 30/07/2024.
 */
@RunWith(JUnit4::class)
class FoodRepositoryImplTest {

    private lateinit var database: AppDatabase
    private lateinit var foodRepository: FoodRepository

    @Before
    fun setUp() {
        database = mockk()
        foodRepository = FoodRepositoryImpl(database)

        coEvery { database.foodDao().getAllFood() } returns testFoods
        coEvery { database.foodDao().getFoodByType(any()) } answers { type ->
            testFoods.filter { it.type == type.invocation.args[0] }
        }
        coEvery { database.foodDao().getSearchFood(any()) } answers { type ->
            val word = type.invocation.args[0].toStr()
            testFoods.filter {
                it.oneEN.contains(word, false) ||
                        it.oneMM.contains(word, false) ||
                        it.twoEN.contains(word, false) ||
                        it.twoMM.contains(word, false)
            }
        }
    }

    @After
    fun tearDown() {
        clearMocks(database)
    }

    @Test
    fun `verify getAllFoods`() = runBlocking {
        val foods = foodRepository.getAllFood()
        Assert.assertTrue(foods.size > 1)
    }

    @Test
    fun `verify getSearchFoods`() = runBlocking {
        val word = "Jui"
        val foods = foodRepository.getSearchFood(word)
        Assert.assertEquals(2, foods.size)
    }

    @Test
    fun `verify getFoodByType`() = runBlocking {
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