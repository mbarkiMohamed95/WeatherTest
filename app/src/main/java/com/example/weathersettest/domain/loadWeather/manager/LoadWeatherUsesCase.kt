package com.example.weathersettest.domain.loadWeather.manager

import com.example.data.utils.DataState
import com.example.weathersettest.domain.loadWeather.model.WeatherUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface LoadWeatherUsesCase {
  suspend  fun loadWeathers(coroutineScope: CoroutineScope)
  suspend  fun loadWeathersByCityName(cityName:String):Flow<DataState<List<WeatherUiModel>>>
  suspend fun loadWeatherFromLocalAsFlow():Flow<DataState<List<WeatherUiModel>>>
  fun checkLocationPermissions(): Boolean
  suspend fun saveWeatherCity()
}