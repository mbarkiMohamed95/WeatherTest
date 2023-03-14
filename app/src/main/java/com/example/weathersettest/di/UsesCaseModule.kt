package com.example.weathersettest.di


import com.example.weatherapptest.tools.workmangers.update.SetUpUploadManagerWorker
import com.example.weathersettest.domain.detail.manager.DetailWeatherUsesCase
import com.example.weathersettest.domain.detail.manager.DetailWeatherUsesCaseImp
import com.example.weathersettest.domain.loadWeather.manager.LoadWeatherUsesCase
import com.example.weathersettest.domain.loadWeather.manager.LoadWeatherUsesCaseImp
import com.example.weathersettest.tools.workmangers.update.SetUpUpdateWeatherManagerWorkerImp

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UsesCaseModule {
    @Binds
    abstract fun provideLoadWeatherUsesCaseImp(loadWeatherUsesCaseImp: LoadWeatherUsesCaseImp): LoadWeatherUsesCase

    @Binds
    abstract fun provideSetUpUpdateWeatherManagerWorkerImp(setUpUpdateWeatherManagerWorkerImp: SetUpUpdateWeatherManagerWorkerImp): SetUpUploadManagerWorker

    @Binds
    abstract fun provideSetDetailWeatherUsesCaseImp(detailWeatherUsesCaseImp: DetailWeatherUsesCaseImp): DetailWeatherUsesCase
}