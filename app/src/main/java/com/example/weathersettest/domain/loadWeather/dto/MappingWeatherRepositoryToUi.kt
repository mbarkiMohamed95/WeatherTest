package com.example.weathersettest.domain.loadWeather.dto

import com.example.data.base.DomainDTOMappingService
import com.example.data.repository.model.WeatherRepositoryModel
import com.example.weathersettest.domain.loadWeather.model.WeatherUiModel
import javax.inject.Inject

class MappingWeatherRepositoryToUi @Inject constructor() :
    DomainDTOMappingService<WeatherRepositoryModel, WeatherUiModel> {

    override fun mapDomainToDTO(domain: WeatherRepositoryModel): WeatherUiModel = domain.run{
        WeatherUiModel(name,weatherLocal[0].main,dt,"${mainRepModel.temp_min} ~ ${mainRepModel.temp_max}",weatherLocal[0].icon)
    }

}
