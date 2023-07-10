package com.example.domain.detail.manager

import com.example.data.repository.manager.WeatherRepository
import com.example.data.repository.model.WeatherRepositoryModel
import com.example.domain.detail.model.DetailWeatherUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailWeatherUsesCaseImp @Inject constructor(private val repository: WeatherRepository) :
    DetailWeatherUsesCase {
    override suspend fun invoke(cityName: String): Result<DetailWeatherUiModel> =
        Result.success(createDetailModel(repository.loadWeatherByNameCityFromLocal(cityName)))


    private fun createDetailModel(weatherRepositoryModel: WeatherRepositoryModel): DetailWeatherUiModel =
        weatherRepositoryModel.run {
            DetailWeatherUiModel(
                name,
                weatherLocal[0].icon,
                mainRepModel.temp,
                weatherLocal[0].main,
                weatherLocal[0].description,
                dt,
                mainRepModel.pressure,
                mainRepModel.humidity,
                visibility,
                wind.deg,
                wind.speed
            )
        }

}
