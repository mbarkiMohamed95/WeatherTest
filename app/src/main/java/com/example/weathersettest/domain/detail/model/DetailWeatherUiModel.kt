package com.example.weathersettest.domain.detail.model

data class DetailWeatherUiModel(
    val name: String,
    val icon:String,
    val temp: Double,
    val main: String,
    val description:String,
    val lastUpdate: Int,
    val pressure: Int,
    val humidity: Int,
    val visibility:Int,
    val windDeg:Int,
    val windSpeed:Double
)