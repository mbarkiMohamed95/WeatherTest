package com.example.data.repository.weatherdto



import com.example.data.base.DomainDTOMappingService
import com.example.data.local.entitys.*
import com.example.data.remote.weather.model.*
import javax.inject.Inject

class MappingWeatherRemoteToLocal @Inject constructor() :
    DomainDTOMappingService<WeatherModel, WeatherLocalModel> {
    override fun mapInputToOutput(domain: WeatherModel): WeatherLocalModel = domain.run {
        WeatherLocalModel(
            cod,
            dt,
            mapCoordToCoordLocal(coord),
            weather.map { mapWeatherToWeatherLocal(it) },
            base,
            mapMainToMainLocal(main),
            visibility,
            mapWindToWindLocal(wind),
            mapCloudsToCloudsLocal(clouds),
            mapSysToSysLocal(sys),
            timezone,
            name
        )
    }

    private fun mapCoordToCoordLocal(coord: Coord): CoordLocal =
        coord.run {
            CoordLocal(
                lon, lat
            )
        }

    private fun mapWeatherToWeatherLocal(weather: Weather): WeatherLocal =
        weather.run {
            WeatherLocal(
                id, main, description, icon
            )
        }

    private fun mapMainToMainLocal(main: Main): MainLocal = main.run {
        MainLocal(
            temp, feels_like, temp_min, temp_max, pressure, humidity
        )
    }

    private fun mapWindToWindLocal(wind: Wind): WindLocal = wind.run {
        WindLocal(speed, deg)
    }

    private fun mapCloudsToCloudsLocal(clouds: Clouds): CloudsLocal =
        clouds.run {
            CloudsLocal(all)
        }

    private fun mapSysToSysLocal(sys: Sys): SysLocal =
        sys.run {
            SysLocal(type, id, country, sunrise, sunset)
        }

}