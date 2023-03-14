package com.example.weathersettest.presentation.detail.uiState

import com.example.weathersettest.domain.detail.model.DetailWeatherUiModel


data class WeatherState(
    val weatherInfo: DetailWeatherUiModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
