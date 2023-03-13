package com.example.data.local.typeCoverter

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.data.local.entitys.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@TypeConverters
class WeatherTypeConverter {
    @TypeConverter
    fun stringToWeatherLocal(json: String?): WeatherLocal? {
        val gson = Gson()
        val type: Type? = object : TypeToken<WeatherLocal?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun weatherLocalToString(list: WeatherLocal?): String? {
        val gson = Gson()
        val type: Type? = object : TypeToken<WeatherLocal?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToListWeatherLocal(json: String?): List<WeatherLocal?>? {
        val gson = Gson()
        val type: Type? = object : TypeToken<List<WeatherLocal?>?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun listWeatherLocalToString(list: List<WeatherLocal?>?): String? {
        val gson = Gson()
        val type: Type? = object : TypeToken<List<WeatherLocal?>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToMainLocal(json: String?): MainLocal? {
        val gson = Gson()
        val type: Type? = object : TypeToken<MainLocal?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun mainLocalToString(list: MainLocal?): String? {
        val gson = Gson()
        val type: Type? = object : TypeToken<MainLocal?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToWindLocal(json: String?): WindLocal? {
        val gson = Gson()
        val type: Type? = object : TypeToken<WindLocal?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun windLocalToString(list: WindLocal?): String? {
        val gson = Gson()
        val type: Type? = object : TypeToken<WindLocal?>() {}.type
        return gson.toJson(list, type)
    }


    @TypeConverter
    fun stringToCloudsLocal(json: String?): CloudsLocal? {
        val gson = Gson()
        val type: Type? = object : TypeToken<CloudsLocal?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun cloudsLocalToString(list: CloudsLocal?): String? {
        val gson = Gson()
        val type: Type? = object : TypeToken<CloudsLocal?>() {}.type
        return gson.toJson(list, type)
    }


    @TypeConverter
    fun stringToSysLocal(json: String?): SysLocal? {
        val gson = Gson()
        val type: Type? = object : TypeToken<SysLocal?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun sysLocalToString(list: SysLocal?): String? {
        val gson = Gson()
        val type: Type? = object : TypeToken<SysLocal?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun stringToCoordLocal(json: String?): CoordLocal? {
        val gson = Gson()
        val type: Type? = object : TypeToken<CoordLocal?>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun coordLocalToString(list: CoordLocal?): String? {
        val gson = Gson()
        val type: Type? = object : TypeToken<CoordLocal?>() {}.type
        return gson.toJson(list, type)
    }

}