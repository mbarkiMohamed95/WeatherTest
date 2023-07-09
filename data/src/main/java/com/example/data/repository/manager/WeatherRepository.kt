package com.example.data.repository.manager

import com.example.data.repository.model.WeatherRepositoryModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun loadWeather(
        apiKey: String,
        cityNumber:Int?=null,
        latitude: Double? = null,
        longitude: Double? = null,
        language: String? = null,
        currentTime: Long? = null,
        cityName: String? = null,
        callback:(Boolean)->Unit
    )

    suspend fun loadWeatherByCityName(
        apiKey: String,
        language: String? = null,
        currentTime: Long? = null,
        cityName: String? = null
    ): Result<WeatherRepositoryModel>

    suspend fun loadWeatherFromLocalAsFlow(): Flow<Result<List<WeatherRepositoryModel>>>

    suspend fun loadWeatherFromLocal(): Result<List<WeatherRepositoryModel>>

    suspend fun loadWeatherByNameCityFromLocal(cityName: String): WeatherRepositoryModel
    suspend fun saveWeatherCity()

}
