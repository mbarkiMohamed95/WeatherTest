package com.example.data.repository.weatherdto


import com.example.data.base.DomainDTOMappingService
import com.example.data.local.entitys.*
import com.example.data.repository.model.*
import javax.inject.Inject

class MappingWeatherLocalToRepository @Inject constructor() :
    DomainDTOMappingService<WeatherLocalModel, WeatherRepositoryModel> {
    override fun mapDomainToDTO(domain: WeatherLocalModel): WeatherRepositoryModel = domain.run {
        WeatherRepositoryModel(
            dt,
            mapCoordLocalToCoordRepModel(coordLocal),
            weatherLocal.map { mapWeatherLocalToWeatherRepModel(it) },
            base,
            mapMainLocalToMainRepModel(mainLocal),
            visibility,
            mapWindLocalToWindRepModel(wind),
            mapCloudsLocalToCloudsRepModel(cloudsLocal),
            mapSysLocalToSysRepModel(sysLocal),
            timezone,
            name,
            cod
        )
    }

    private fun mapCoordLocalToCoordRepModel(coordLocal: CoordLocal): CoordRepModel =
        coordLocal.run {
            CoordRepModel(
                lon, lat
            )
        }

    private fun mapWeatherLocalToWeatherRepModel(weatherLocal: WeatherLocal): WeatherRepModel =
        weatherLocal.run {
            WeatherRepModel(
                id, main, description, icon
            )

        }

    private fun mapMainLocalToMainRepModel(mainLocal: MainLocal): MainRepModel = mainLocal.run {
        MainRepModel(
            temp, feels_like, temp_min, temp_max, pressure, humidity
        )
    }

    private fun mapWindLocalToWindRepModel(windLocal: WindLocal): WindRepModel = windLocal.run {
        WindRepModel(speed, deg)
    }

    private fun mapCloudsLocalToCloudsRepModel(cloudsLocal: CloudsLocal): CloudsRepModel =
        cloudsLocal.run {
            CloudsRepModel(all)
        }

    private fun mapSysLocalToSysRepModel(sysLocal: SysLocal): SysRepModel =
        sysLocal.run {
            SysRepModel(type, id, country, sunrise, sunset)
        }

}
