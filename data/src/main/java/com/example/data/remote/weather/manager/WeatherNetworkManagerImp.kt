package com.example.data.remote.weather.manager

import com.example.data.remote.weather.model.WeatherModel
import com.example.data.remote.services.ApiServices
import com.example.data.remote.weather.manager.WeatherNetworkManager.Companion.WeatherApi
import com.example.data.utils.runCatchingResponse
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import javax.inject.Inject

class WeatherNetworkManagerImp @Inject constructor(
    private val apiServices: ApiServices,
    private val client: HttpClient
) :
    WeatherNetworkManager {
    /**
     * load the weather using the Retrofit
     */
    override suspend fun loadWeather(
        apiKey: String,
        latitude: Double?,
        longitude: Double?,
        language: String?,
        currentTime: Long?,
        cityName: String?
    ): Result<WeatherModel> = Result.runCatchingResponse {
        apiServices.loadWeather(
            apiKey,
            latitude = latitude,
            longitude = longitude,
            language = language,
            currentTime = currentTime,
            cityName = cityName
        )
    }
    /**
     * load the weather using the Ktor
     */
    override suspend fun loadWeatherWithKtor(
        apiKey: String,
        latitude: Double?,
        longitude: Double?,
        language: String?,
        currentTime: Long?,
        cityName: String?
    ): WeatherModel =
        client.get {
            url(WeatherApi)
            parameter("APPID", apiKey)
            parameter("units", "metric")
            parameter("lat", latitude)
            parameter("lon", longitude)
            parameter("lang", language)
            parameter("dt", currentTime)
            parameter("q", cityName)

        }

}