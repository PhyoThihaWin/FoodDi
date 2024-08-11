package com.pthw.food.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.pthw.food.data.cache.repository.CacheRepositoryImpl
import com.pthw.food.domain.model.AppThemeMode
import com.pthw.food.domain.model.Localization
import com.pthw.food.domain.repository.CacheRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by P.T.H.W on 01/08/2024.
 */

@RunWith(AndroidJUnit4::class)
@SmallTest
class CacheRepositoryImplTest {

    private lateinit var context: Context
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var cacheRepository: CacheRepository

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        dataStore = context.dataStore
        cacheRepository = CacheRepositoryImpl(context.dataStore)
    }

    @Test
    fun verify_put_en_Language() = runTest {
        val locale = Localization.ENGLISH
        cacheRepository.putLanguage(locale)

        val dbLocale = cacheRepository.getLanguage()

        Assert.assertEquals(dbLocale.first(), locale)
    }

    @Test
    fun verify_put_my_Language() = runTest {
        val locale = Localization.MYANMAR
        cacheRepository.putLanguage(locale)

        val cachedLocale = cacheRepository.getLanguage()

        Assert.assertEquals(cachedLocale.first(), locale)
    }

    @Test
    fun verify_getLanguage() = runTest {
        val locale = cacheRepository.getLanguage()
        Assert.assertEquals(Localization.MYANMAR, locale.first())
    }

    @Test
    fun verify_getLanguageNormal() {
        val locale = cacheRepository.getLanguageNormal()
        Assert.assertEquals(Localization.MYANMAR, locale)
    }

    @Test
    fun verify_putTheme() = runTest {
        val theme = AppThemeMode.LIGHT_MODE
        cacheRepository.putThemeMode(theme)

        val cacheTheme = cacheRepository.getThemeMode()

        Assert.assertEquals(cacheTheme.first(), theme)
    }

    @Test
    fun verify_getTheme() = runTest {
        val theme = cacheRepository.getThemeMode()
        Assert.assertEquals(theme.first(), AppThemeMode.LIGHT_MODE)
    }

    @Test
    fun verify_getThemeNormal() {
        val locale = cacheRepository.getThemeModeNormal()
        Assert.assertEquals(locale, AppThemeMode.LIGHT_MODE)
    }


    companion object {
        private val Context.dataStore by preferencesDataStore("pref.foodDi")
    }

}