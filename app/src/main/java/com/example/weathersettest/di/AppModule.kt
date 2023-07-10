package com.example.weathersettest.di

import com.example.weatherapptest.tools.location.LocationManagerInteraction
import com.example.domain.tools.location.LocationManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun provideLocationManager(locationManager: LocationManager): LocationManagerInteraction

}