package com.example.domain.loadWeather.manager.loadWeatherFromLocalAsFlow

import com.example.domain.loadWeather.model.WeatherUiModel
import kotlinx.coroutines.flow.Flow

interface LoadWeatherFromLocalAsFlowUseCase {
    operator fun invoke( ): Flow<Result<List<WeatherUiModel>>>

}