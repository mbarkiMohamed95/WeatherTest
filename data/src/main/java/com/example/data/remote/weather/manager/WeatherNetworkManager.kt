package com.example.data.remote.weather.manager

import com.example.data.remote.weather.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherNetworkManager {
    suspend fun loadWeather(
        apiKey: String,
        latitude: Double? = null,
        longitude: Double? = null,
        language: String? = null,
        currentTime: Long? = null,
        cityName: String? = null

    ): Flow<WeatherModel?>
}