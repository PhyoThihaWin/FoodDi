package com.pthw.food.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.Preferences.Pair
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pthw.food.data.cache.repository.CacheRepositoryImpl
import com.pthw.food.domain.model.Localization
import com.pthw.food.domain.repository.CacheRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by P.T.H.W on 31/07/2024.
 */

@RunWith(JUnit4::class)
class CacheRepositoryImplTest {
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var cacheRepository: CacheRepository

    @Before
    fun setup() {
        dataStore = mockk()
        cacheRepository = CacheRepositoryImpl(dataStore)

        every { dataStore.data } returns flow {
            emit(
                preferencesOf(
                    stringPreferencesKey("english") to Localization.ENGLISH
                )
            )
        }

    }

    @Test
    fun `verify getLanguageNormal`() {
        val data = cacheRepository.getLanguageNormal()
        Assert.assertEquals("en", data)
    }

    @Test
    fun `verify getLanguage`() = runBlocking {
        val data = cacheRepository.getLanguage()
        Assert.assertEquals("en", data.first())
    }

    @Test
    fun `verify putLanguage` () = runBlocking {
        every { dataStore.data } returns flow {
            emit(
                preferencesOf(
                    stringPreferencesKey("english") to Localization.ENGLISH
                )
            )
        }
        val locale = Localization.MYANMAR
        val data = cacheRepository.putLanguage(locale)
    }
}