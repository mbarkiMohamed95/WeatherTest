package com.example.data.repository.manager

import com.example.data.utils.DataState
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
        cityName: String? = null
    ): Flow<DataState<Boolean>>

    suspend fun loadWeatherByCityName(
        apiKey: String,
        language: String? = null,
        currentTime: Long? = null,
        cityName: String? = null
    ): Flow<DataState<WeatherRepositoryModel>>

    suspend fun loadWeatherFromLocalAsFlow(): Flow<DataState<List<WeatherRepositoryModel>>>

    suspend fun loadWeatherFromLocal(): DataState<List<WeatherRepositoryModel>>

    suspend fun loadWeatherByNameCityFromLocal(cityName: String): WeatherRepositoryModel

}
