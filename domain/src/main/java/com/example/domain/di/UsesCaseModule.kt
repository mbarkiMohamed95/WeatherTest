package com.example.domain.di


import com.example.domain.tools.workmangers.update.SetUpUploadManagerWorker
import com.example.domain.detail.manager.DetailWeatherUsesCase
import com.example.domain.detail.manager.DetailWeatherUsesCaseImp
import com.example.domain.loadWeather.manager.loadWeathe.LoadWeatherUsesCase
import com.example.domain.loadWeather.manager.loadWeathe.LoadWeatherUsesCaseImp
import com.example.domain.loadWeather.manager.loadWeatherFromLocalAsFlow.LoadWeatherFromLocalAsFlowUseCase
import com.example.domain.loadWeather.manager.loadWeatherFromLocalAsFlow.LoadWeatherFromLocalAsFlowUseCaseImp
import com.example.domain.loadWeather.manager.loadWeathersByCityName.LoadWeathersByCityName
import com.example.domain.loadWeather.manager.loadWeathersByCityName.LoadWeathersByCityNameImp
import com.example.domain.loadWeather.manager.saveWeatherCity.SaveWeatherCityUseCase
import com.example.domain.loadWeather.manager.saveWeatherCity.SaveWeatherCityUseCaseImp
import com.example.domain.tools.workmangers.update.SetUpUpdateWeatherManagerWorkerImp

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UsesCaseModule {
    @Binds
    abstract fun provideLoadWeatherUsesCase(loadWeatherUsesCaseImp: LoadWeatherUsesCaseImp): LoadWeatherUsesCase

    @Binds
    abstract fun provideSetUpUpdateWeatherManagerWorker(setUpUpdateWeatherManagerWorkerImp: SetUpUpdateWeatherManagerWorkerImp): SetUpUploadManagerWorker

    @Binds
    abstract fun provideSetDetailWeatherUsesCase(detailWeatherUsesCaseImp: DetailWeatherUsesCaseImp): DetailWeatherUsesCase

    @Binds
    abstract fun provideLoadWeatherFromLocalAsFlowUseCase(loadWeatherFromLocalAsFlowUseCaseImp: LoadWeatherFromLocalAsFlowUseCaseImp): LoadWeatherFromLocalAsFlowUseCase
    @Binds
    abstract fun provideLoadWeathersByCityName(loadWeathersByCityNameImp: LoadWeathersByCityNameImp): LoadWeathersByCityName

    @Binds
    abstract fun provideSaveWeatherCityUseCase(SaveWeatherCityUseCaseImp: SaveWeatherCityUseCaseImp): SaveWeatherCityUseCase
}