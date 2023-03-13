package com.example.data.local.localManager

import com.example.data.local.dao.weatherDao.WeatherDao
import com.example.data.local.entitys.WeatherLocalModel
import com.example.data.local.localManager.LocalWeatherManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalWeatherManagerImp @Inject constructor(private val weatherDao: WeatherDao) :
    LocalWeatherManager {
    override suspend fun saveWeather(weatherLocalModel: WeatherLocalModel) {
        weatherDao.insert(weatherLocalModel)
    }

    override suspend fun saveListWeather(weatherLocalModel: List<WeatherLocalModel>) {
        weatherDao.insertList(weatherLocalModel)
    }

    override suspend fun getAllWeathersAsFlow(): Flow<List<WeatherLocalModel>> {
        return weatherDao.loadAllWeathersAsFlow()
    }

    override suspend fun getAllWeathers(): List<WeatherLocalModel> = weatherDao.loadAllWeathers()
    override suspend fun loadWeatherByNameCityFromLocal(cityName: String): WeatherLocalModel =
        weatherDao.loadWeatherByNameCity(cityName)
}