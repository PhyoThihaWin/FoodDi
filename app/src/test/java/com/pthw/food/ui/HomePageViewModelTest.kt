package com.pthw.food.ui

import app.cash.turbine.test
import com.pthw.food.MainCoroutineRule
import com.pthw.food.data.FakeCacheRepository
import com.pthw.food.data.FakeFoodRepository
import com.pthw.food.domain.model.AppThemeMode
import com.pthw.food.domain.model.Localization
import com.pthw.food.ui.home.HomePageViewModel
import com.pthw.food.utils.ConstantValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by P.T.H.W on 10/08/2024.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class HomePageViewModelTest {

    private lateinit var viewModel: HomePageViewModel

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        // fake repositories
        val foodRepository = FakeFoodRepository()
        val cacheRepository = FakeCacheRepository()

        viewModel = HomePageViewModel(foodRepository, cacheRepository)
    }


    @Test
    fun getAllFoods_insertAndRetrieve() = runTest {
        viewModel.getAllFoods()

        Assert.assertTrue(viewModel.foods.value.isNotEmpty())
    }

    @Test
    fun updateSearchQuery_verifyQueryAndFoodList() {
        val query = "Deli"
        viewModel.updateSearchQuery(query)


        runTest {
            viewModel.searchQuery.test {
                Assert.assertEquals(query, awaitItem())
            }

            advanceTimeBy(400)
            Assert.assertEquals(1, viewModel.foods.value.size)
            Assert.assertEquals("Banana", viewModel.foods.value.firstOrNull()?.dieEN)
        }
    }

    @Test
    fun getFoodsByType_verifyFoodList() {
        val filterType = ConstantValue.filterList[2]
        viewModel.getFoodsByType(filterType)

        runTest {
            advanceTimeBy(100)
            Assert.assertEquals(1, viewModel.foods.value.size)
        }
    }

    @Test
    fun updateLanguageCache_verifyCurrentLanguage() {
        val newLanguage = Localization.MYANMAR
        viewModel.updateLanguageCache(newLanguage)

        runTest {
            advanceTimeBy(400)
            Assert.assertEquals(newLanguage, viewModel.currentLanguage.value)
        }
    }

    @Test
    fun updateCachedThemeMode_verifyAppThemeMode() {
        val newTheme = AppThemeMode.DARK_MODE
        viewModel.updateCachedThemeMode(newTheme)

        runTest {
            advanceTimeBy(100)
            Assert.assertEquals(newTheme, viewModel.appThemeMode.value)
        }
    }

    @Test
    fun updateClickCountAd_verifyClickCount() {
        val isReset = false

        viewModel.updateClickCountAd(isReset)

        Assert.assertEquals(1, viewModel.clickCountForAd.intValue)
    }
}