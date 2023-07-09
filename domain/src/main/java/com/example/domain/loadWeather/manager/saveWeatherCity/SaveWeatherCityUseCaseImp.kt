package com.example.domain.loadWeather.manager.saveWeatherCity

import com.example.data.repository.manager.WeatherRepository
import javax.inject.Inject

class SaveWeatherCityUseCaseImp @Inject constructor(private val weatherRepository: WeatherRepository) :
    SaveWeatherCityUseCase {
    override suspend fun invoke() {
        weatherRepository.saveWeatherCity()
    }

}