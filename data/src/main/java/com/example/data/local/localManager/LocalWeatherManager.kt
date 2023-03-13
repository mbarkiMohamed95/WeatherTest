package com.example.data.local.localManager

import com.example.data.local.entitys.WeatherLocalModel
import kotlinx.coroutines.flow.Flow

interface LocalWeatherManager {
    suspend fun saveWeather(weatherLocalModel: WeatherLocalModel)
    suspend fun saveListWeather(weatherLocalModel: List<WeatherLocalModel>)
    suspend fun getAllWeathersAsFlow(): Flow<List<WeatherLocalModel>>
    suspend fun getAllWeathers(): List<WeatherLocalModel>
    suspend fun loadWeatherByNameCityFromLocal(cityName: String): WeatherLocalModel

}