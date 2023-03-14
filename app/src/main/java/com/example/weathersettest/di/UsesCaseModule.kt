package com.example.weathersettest.di


import com.example.weathersettest.domain.loadWeather.manager.LoadWeatherUsesCase
import com.example.weathersettest.domain.loadWeather.manager.LoadWeatherUsesCaseImp

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UsesCaseModule {
    @Binds
    abstract fun provideLoadWeatherUsesCaseImp(loadWeatherUsesCaseImp: LoadWeatherUsesCaseImp): LoadWeatherUsesCase

}