package com.example.data.di

import com.example.data.repository.manager.WeatherRepository
import com.example.data.repository.manager.WeatherRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindWeatherRepository (WeatherRepository: WeatherRepositoryImp): WeatherRepository
}