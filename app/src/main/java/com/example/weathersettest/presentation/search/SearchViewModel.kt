package com.example.weathersettest.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.DataState
import com.example.weathersettest.domain.loadWeather.manager.LoadWeatherUsesCase
import com.example.weathersettest.domain.loadWeather.model.WeatherUiModel
import com.example.weathersettest.presentation.search.action.SearchViewActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val loadWeatherUsesCase: LoadWeatherUsesCase) :
    ViewModel() {
    private val _dataState: MutableLiveData<DataState<List<WeatherUiModel>>> =
        MutableLiveData(
            DataState.Idle
        )

    val dataState: LiveData<DataState<List<WeatherUiModel>>> get() = _dataState

    suspend fun handleAction(action: SearchViewActions) {
        when (action) {
            is SearchViewActions.LoadSearchedCityWeather -> {
                action.apply {
                    loadWeatherByCityName(action.cityName)
                }
            }
            is  SearchViewActions.SavePlace->{
                saveWeatherCity()
            }
        }

    }

    private suspend fun loadWeatherByCityName(cityName: String) {
        loadWeatherUsesCase.loadWeathersByCityName(cityName).onEach {
            _dataState.value = it
        }.launchIn(viewModelScope)
    }

    private suspend fun saveWeatherCity() {
        loadWeatherUsesCase.saveWeatherCity()
    }
}