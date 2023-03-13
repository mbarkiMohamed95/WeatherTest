package com.example.data.local.dbManager

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.dao.weatherDao.WeatherDao
import com.example.data.local.entitys.WeatherLocalModel
import com.example.data.local.typeCoverter.WeatherTypeConverter

@Database(
    entities = [WeatherLocalModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(WeatherTypeConverter::class)

abstract class AppDatabase: RoomDatabase() {
  abstract fun weatherDao(): WeatherDao
    companion object {
        val DATABASE_NAME: String = "WeatherDataBase"
    }
}