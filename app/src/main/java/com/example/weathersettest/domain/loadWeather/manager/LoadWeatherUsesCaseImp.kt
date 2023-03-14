package com.example.weathersettest.domain.loadWeather.manager

import com.example.data.repository.manager.WeatherRepository
import com.example.data.utils.DataState
import com.example.weatherapptest.tools.location.LocationManagerInteraction
import com.example.weathersettest.domain.loadWeather.dto.MappingWeatherRepositoryToUi
import com.example.weathersettest.domain.loadWeather.model.WeatherUiModel
import com.example.weathersettest.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import java.util.*
import javax.inject.Inject

class LoadWeatherUsesCaseImp @Inject constructor(
    private val locationManagerInteraction: LocationManagerInteraction,
    private val repository: WeatherRepository,
    private val repositoryToUi: MappingWeatherRepositoryToUi
) : LoadWeatherUsesCase {

    override suspend fun loadWeathers(coroutineScope: CoroutineScope) {
        locationManagerInteraction.getCurrentLocation(coroutineScope) { location ->
            repository.loadWeather(
                BuildConfig.apikey,
                latitude = location.latitude,
                longitude = location.longitude,
                language = Locale.getDefault().language,
                currentTime = System.currentTimeMillis() / 1000,
            ).launchIn(coroutineScope)
        }

    }


    override suspend fun loadWeatherFromLocalAsFlow(): Flow<DataState<List<WeatherUiModel>>> =
        flow {
            repository.loadWeatherFromLocalAsFlow().collect {
                when (it) {
                    is DataState.Success -> {
                        emit(DataState.Success(it.data.map { repositoryToUi.mapDomainToDTO(it) }))
                    }
                    is DataState.Error -> {
                        emit(it)
                    }
                    else->{

                    }
                }
            }
        }


    override fun checkLocationPermissions(): Boolean =
        locationManagerInteraction.checkLocationPermissions()


}