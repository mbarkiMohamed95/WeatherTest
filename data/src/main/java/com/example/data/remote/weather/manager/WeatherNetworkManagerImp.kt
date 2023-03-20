package com.example.data.remote.weather.manager

import com.example.data.remote.weather.model.WeatherModel
import com.example.data.remote.services.ApiServices
import com.example.data.remote.weather.manager.WeatherNetworkManager.Companion.WeatherApi
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.http.Query
import javax.inject.Inject

class WeatherNetworkManagerImp @Inject constructor(private val apiServices: ApiServices,private val client: HttpClient) :
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

    override suspend fun loadWeatherWithKtor(
        apiKey: String,
        latitude: Double?,
        longitude: Double?,
        language: String?,
        currentTime: Long?,
        cityName: String?
    ): Flow<WeatherModel> = callbackFlow {
        send(client.get {
            url(WeatherApi)
            parameter("APPID",apiKey)
            parameter("lat",latitude)
            parameter("lon",longitude)
            parameter("lang",language)
            parameter("dt",currentTime)
            parameter("q",cityName)
        })
        awaitClose()
    }

}