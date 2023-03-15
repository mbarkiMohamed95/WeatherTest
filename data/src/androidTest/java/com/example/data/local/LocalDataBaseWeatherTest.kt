package com.example.data.local

import com.example.data.local.dao.weatherDao.WeatherDao
import com.example.data.local.entitys.WeatherLocalModel


class LocalDataBaseWeatherTest (private val dao: WeatherDao) {
    suspend fun insetWeather(weatherLocalModel: WeatherLocalModel){
        dao.insert(weatherLocalModel)
    }
    fun getAllWeather():List<WeatherLocalModel> = dao.loadAllWeathers()

    fun deletAllWeather() {
        dao.deleteAllWeather()
    }
}