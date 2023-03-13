package com.example.data.di

import com.example.data.local.localManager.LocalWeatherManager
import com.example.data.local.localManager.LocalWeatherManagerImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {
    @Binds
    abstract fun provideLocalWeatherManager(localWeatherManagerImp: LocalWeatherManagerImp): LocalWeatherManager
}