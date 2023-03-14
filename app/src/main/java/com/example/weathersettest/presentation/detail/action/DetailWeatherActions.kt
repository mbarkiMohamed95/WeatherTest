package com.example.weatherapptest.presentation.detailWeather.action

sealed class DetailWeatherActions{
    data class LoadDetail(val cityName:String):DetailWeatherActions()
}
