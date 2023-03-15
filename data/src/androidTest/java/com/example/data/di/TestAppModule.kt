package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.weatherDao.WeatherDao
import com.example.data.local.dbManager.AppDatabase
import com.example.data.local.LocalDataBaseWeatherTest

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    @Named("test_dao")
    fun provideWeatherDao(@Named("test_db") appDatabase: AppDatabase) = appDatabase.weatherDao()


    @Provides
    @Singleton
    @Named("test_t")
    fun provideLocalDataBaseWeatherTest(@Named("test_dao") weatherDao: WeatherDao) = LocalDataBaseWeatherTest(weatherDao)



}