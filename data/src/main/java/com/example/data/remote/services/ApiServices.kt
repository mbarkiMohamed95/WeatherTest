package com.example.data.remote.services

import com.example.data.remote.weather.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("/data/2.5/weather")
   suspend fun loadWeather(
        @Query("APPID") apiKey:String,
        @Query("units") unit:String="metric",
        @Query("lat") latitude:Double?=null,
        @Query("lon") longitude:Double?=null,
        @Query("lang") language: String?=null,
        @Query("dt") currentTime:Long?=null,
        @Query("q") cityName:String?=null
        ):Response<WeatherModel>


}