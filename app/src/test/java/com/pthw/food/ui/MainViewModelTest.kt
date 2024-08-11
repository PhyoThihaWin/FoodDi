package com.pthw.food.ui

import com.pthw.food.MainCoroutineRule
import com.pthw.food.data.FakeCacheRepository
import com.pthw.food.domain.model.AppThemeMode
import com.pthw.food.domain.repository.CacheRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by P.T.H.W on 11/08/2024.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var cacheRepository: CacheRepository

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        cacheRepository = FakeCacheRepository()
        viewModel = MainViewModel(cacheRepository)
    }


    @Test
    fun getAppThemeMode() = runTest {
        val newTheme = AppThemeMode.SYSTEM_DEFAULT
        cacheRepository.putThemeMode(newTheme)

        advanceTimeBy(100)

        Assert.assertEquals(newTheme, viewModel.appThemeMode.value)
    }

    @Test
    fun isSplashShow() = runTest {

        Assert.assertEquals(true, viewModel.isSplashShow.value)

        advanceTimeBy(3000)

        Assert.assertEquals(false, viewModel.isSplashShow.value)
    }
}