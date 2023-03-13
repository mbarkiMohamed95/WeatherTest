package com.example.data.remote.weather.manager

import com.example.data.remote.weather.model.WeatherModel
import com.example.data.remote.services.ApiServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class WeatherNetworkManagerImp @Inject constructor(private val apiServices: ApiServices) :
    WeatherNetworkManager {
    override suspend fun loadWeather(
        apiKey: String,
        latitude: Double?,
        longitude: Double?,
        language: String?,
        currentTime: Long?,
        cityName: String?
    ): Flow<WeatherModel?> = callbackFlow {
        var res = apiServices.loadWeather(
            apiKey,
            latitude = latitude,
            longitude = longitude,
            language = language,
            currentTime = currentTime,
            cityName = cityName
        )
        if (res.isSuccessful) {
            send(res.body())
        } else {
            send(null)
        }
        awaitClose()
    }

}