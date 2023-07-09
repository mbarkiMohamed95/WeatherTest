package com.example.domain.loadWeather.manager.loadWeathersByCityName

import com.example.domain.loadWeather.model.WeatherUiModel

interface LoadWeathersByCityName {
    suspend operator fun invoke(cityName:String):Result<List<WeatherUiModel>>
}