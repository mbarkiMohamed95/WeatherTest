package com.example.weathersettest.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapptest.presentation.detailWeather.action.DetailWeatherActions
import com.example.domain.detail.manager.DetailWeatherUsesCase
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


    private suspend fun loadDetailWeatherView(cityName: String) = viewModelScope.launch {
        state = state.copy(
            isLoading = true,
            error = null
        )
        detailWeatherUsesCase(cityName).onSuccess {
            state = state.copy(
                weatherInfo = it,
                isLoading = false,
                error = null
            )
        }.onFailure {
            state = state.copy(
                weatherInfo = null,
                isLoading = false,
                error = it?.message
            )
        }
    }
}


