package com.example.domain.loadWeather.manager.loadWeathersByCityName

import com.example.data.repository.manager.WeatherRepository
import com.example.domain.BuildConfig
import com.example.domain.loadWeather.dto.MappingWeatherRepositoryToUi
import com.example.domain.loadWeather.model.WeatherUiModel
import kotlinx.coroutines.channels.awaitClose
import java.util.*
import javax.inject.Inject

class LoadWeathersByCityNameImp @Inject constructor(
    private val repository: WeatherRepository,
    private val repositoryToUi: MappingWeatherRepositoryToUi,

    ) : LoadWeathersByCityName {
    override suspend fun invoke(cityName: String): Result<List<WeatherUiModel>> {
        return repository.loadWeatherByCityName(
            BuildConfig.apikey,
            cityName = cityName,
            currentTime = System.currentTimeMillis() / 1000,
            language = Locale.getDefault().language
        ).map {
            return Result.success(listOf(repositoryToUi.mapInputToOutput(it)))
        }
    }


}