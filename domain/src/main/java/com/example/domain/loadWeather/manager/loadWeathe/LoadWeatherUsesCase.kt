package com.example.domain.loadWeather.manager.loadWeathe

import com.example.domain.loadWeather.model.WeatherUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface LoadWeatherUsesCase {
  suspend operator fun invoke(coroutineScope: CoroutineScope):Int
}