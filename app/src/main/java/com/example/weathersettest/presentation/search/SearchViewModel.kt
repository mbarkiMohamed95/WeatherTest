package com.example.weathersettest.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.loadWeather.manager.loadWeathersByCityName.LoadWeathersByCityName
import com.example.domain.loadWeather.manager.saveWeatherCity.SaveWeatherCityUseCase
import com.example.domain.loadWeather.model.WeatherUiModel
import com.example.weathersettest.presentation.home.HomeUiModel
import com.example.weathersettest.presentation.search.action.SearchViewActions
import com.example.weathersettest.tools.ui.AsyncState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiModel(var list: AsyncState<List<WeatherUiModel>>? = null)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val loadWeathersByCityName: LoadWeathersByCityName,
    private val saveWeatherCityUseCase: SaveWeatherCityUseCase
) :
    ViewModel() {
    private val _dataState: MutableStateFlow<SearchUiModel> =
        MutableStateFlow(
            SearchUiModel()
        )

    val dataState: StateFlow<SearchUiModel> get() = _dataState
    suspend fun handleAction(action: SearchViewActions) {
        when (action) {
            is SearchViewActions.LoadSearchedCityWeather -> {
                action.apply {
                    loadWeatherByCityName(action.cityName)
                }
            }
            is SearchViewActions.SavePlace -> {
                saveWeatherCity()
            }
        }

    }

    private fun loadWeatherByCityName(cityName: String) = viewModelScope.launch {
        loadWeathersByCityName(cityName).onSuccess {
            _dataState.update { uiState -> uiState.copy(AsyncState.Success(it)) }
        }.onFailure {
            _dataState.update { uiState -> uiState.copy(AsyncState.Failure(it as Exception)) }
        }
    }

    private suspend fun saveWeatherCity() {
        saveWeatherCityUseCase()
    }
}