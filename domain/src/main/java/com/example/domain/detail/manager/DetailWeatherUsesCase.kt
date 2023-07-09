package com.example.domain.detail.manager

import com.example.domain.detail.model.DetailWeatherUiModel
import kotlinx.coroutines.flow.Flow

interface DetailWeatherUsesCase {
    suspend operator fun invoke(cityName:String): Result<DetailWeatherUiModel>
}