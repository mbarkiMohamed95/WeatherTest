package com.example.weathersettest.di

import com.example.data.base.DomainDTOMappingService
import com.example.data.repository.model.WeatherRepositoryModel
import com.example.weathersettest.domain.loadWeather.dto.MappingWeatherRepositoryToUi
import com.example.weathersettest.domain.loadWeather.model.WeatherUiModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class appDtoModule {
    @Binds
    abstract fun bindMappingWeatherRepositoryToUi(mappingWeatherRepositoryToUi: MappingWeatherRepositoryToUi): DomainDTOMappingService<WeatherRepositoryModel, WeatherUiModel>
}