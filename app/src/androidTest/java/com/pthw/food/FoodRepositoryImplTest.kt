package com.pthw.food

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.pthw.food.data.cache.database.AppDatabase
import com.pthw.food.data.cache.repository.FoodRepositoryImpl
import com.pthw.food.domain.repository.FoodRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
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
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun getAllFood() = runBlocking {
        val foods = foodRepository.getAllFood()
        assert(foods.isEmpty())
    }

//    fun getSearchFood(word: String) = database.foodDao().getSearchFood(word).map {
//        it.copy(
//            imgOne = it.imgOne.firebaseImage(),
//            imgTwo = it.imgTwo.firebaseImage()
//        )
//    }
//
//    fun getFoodByType(type: String) = database.foodDao().getFoodByType(type).map {
//        it.copy(
//            imgOne = it.imgOne.firebaseImage(),
//            imgTwo = it.imgTwo.firebaseImage()
//        )
//    }
}