package com.example.data.di

import com.example.data.remote.weather.manager.WeatherNetworkManager
import com.example.data.remote.weather.manager.WeatherNetworkManagerImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkingModule {

    @Binds
    abstract fun bindLoadWeatherNetworkManager(loadWeatherNetworkManagerImp: WeatherNetworkManagerImp): WeatherNetworkManager

}