package com.pthw.food.ui.home

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pthw.food.R
import com.pthw.food.domain.model.FilterType
import com.pthw.food.domain.model.Food
import com.pthw.food.domain.repository.CacheRepository
import com.pthw.food.domain.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by P.T.H.W on 19/07/2024.
 */
@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val repository: FoodRepository,
    private val cacheRepository: CacheRepository
) : ViewModel() {
    val appThemeMode = mutableStateOf(cacheRepository.getThemeModeNormal())
    val currentLanguage = mutableStateOf(cacheRepository.getLanguageNormal())

    var pageTitle = mutableIntStateOf(R.string.app_name)
        private set
    var foods = mutableStateOf<List<Food>>(emptyList())
        private set
    var searchQuery = MutableStateFlow("")
        private set
    var clickCountForAd = mutableIntStateOf(0)
        private set

    init {
        getThemeMode()
        getLanguageCache()
        getAllFoods()
        getSearchFoods()
    }

    /**
     * Get all foods from database
     */
    fun getAllFoods() {
        pageTitle.intValue = R.string.app_name
        viewModelScope.launch {
            foods.value = repository.getAllFood()
        }
    }

    /**
     * Update search query from UI
     * Handle to prevent continuous request search query
     */
    fun updateSearchQuery(word: String) {
        viewModelScope.launch {
            searchQuery.value = word
        }
    }

    /**
     * Get foods by category type from database
     */
    fun getFoodsByType(filterType: FilterType) {
        pageTitle.intValue = filterType.title
        viewModelScope.launch {
            foods.value = repository.getFoodByType(filterType.type.toString())
        }
    }

    /**
     * Update app chose language to PreferenceDataStore
     */
    fun updateLanguageCache(localeCode: String) {
        viewModelScope.launch {
            cacheRepository.putLanguage(localeCode)
        }
    }

    /**
     * Update app chose theme to PreferenceDataStore
     */
    fun updateCachedThemeMode(theme: String) {
        viewModelScope.launch {
            cacheRepository.putThemeMode(theme)
        }
    }

    /**
     * Update click count for remembering user actions,
     * if it reaches, load Meta Interstitial and show
     */
    fun updateClickCountAd(isReset: Boolean = false) {
        if (isReset) clickCountForAd.intValue = 0
        else clickCountForAd.intValue++
    }

    /**
     * Private search function to database after query flow changes
     */
    @OptIn(FlowPreview::class)
    private fun getSearchFoods() {
        viewModelScope.launch {
            searchQuery.debounce(300).collectLatest {
                val data = repository.getSearchFood(it)
                Timber.i("search: ${data.count()}")
                foods.value = data
            }
        }
    }

    private fun getLanguageCache() {
        viewModelScope.launch {
            cacheRepository.getLanguage().collectLatest {
                currentLanguage.value = it
            }
        }
    }

    /**
     * Private get theme function for silently update theme variable
     */
    private fun getThemeMode() {
        viewModelScope.launch {
            cacheRepository.getThemeMode().collectLatest {
                appThemeMode.value = it
            }
        }
    }


}