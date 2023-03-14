package com.example.weathersettest.domain.detail.manager

import com.example.data.utils.DataState
import com.example.weathersettest.domain.detail.model.DetailWeatherUiModel
import kotlinx.coroutines.flow.Flow

interface DetailWeatherUsesCase {
    suspend fun loadDetailWeather(cityName:String): Flow<DataState<DetailWeatherUiModel>>
}