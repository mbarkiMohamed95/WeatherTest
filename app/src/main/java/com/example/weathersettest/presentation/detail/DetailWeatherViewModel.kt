package com.example.weathersettest.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.utils.DataState
import com.example.weatherapptest.presentation.detailWeather.action.DetailWeatherActions
import com.example.weathersettest.domain.detail.manager.DetailWeatherUsesCase
import com.example.weathersettest.presentation.detail.uiState.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailWeatherViewModel @Inject constructor(private val detailWeatherUsesCase: DetailWeatherUsesCase) :
    ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set


    fun handleAction(action: DetailWeatherActions) {
        viewModelScope.launch {
            when (action) {
                is DetailWeatherActions.LoadDetail -> {
                    loadDetailWeatherView(action.cityName)
                }
            }
        }
    }


    private suspend fun loadDetailWeatherView(cityName: String) {

        detailWeatherUsesCase.loadDetailWeather(cityName).collect {
            when (it) {
                is DataState.Success -> {
                    state = state.copy(
                        weatherInfo = it.data,
                        isLoading = false,
                        error = null
                    )
                }
                is DataState.Error -> {
                    state = state.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = it.exception?.message
                    )
                }
                DataState.Idle -> {
                    state = state.copy(
                        isLoading = true,
                        error = null
                    )
                }
            }
        }
    }


}