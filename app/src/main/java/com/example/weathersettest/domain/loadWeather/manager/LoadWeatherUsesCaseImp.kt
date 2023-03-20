package com.example.weathersettest.domain.loadWeather.manager

import com.example.data.repository.manager.WeatherRepository
import com.example.data.repository.model.WeatherRepositoryModel
import com.example.data.utils.DataState
import com.example.weatherapptest.tools.location.LocationManagerInteraction
import com.example.weatherapptest.tools.workmangers.update.SetUpUploadManagerWorker
import com.example.weathersettest.domain.loadWeather.dto.MappingWeatherRepositoryToUi
import com.example.weathersettest.domain.loadWeather.model.WeatherUiModel
import com.example.weathersettest.BuildConfig
import com.example.weathersettest.tools.workmangers.update.model.UpdateWeatherWMModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import java.util.*
import javax.inject.Inject

class LoadWeatherUsesCaseImp @Inject constructor(
    private val locationManagerInteraction: LocationManagerInteraction,
    private val repository: WeatherRepository,
    private val repositoryToUi: MappingWeatherRepositoryToUi,
    private val setUpUploadManagerWorker: SetUpUploadManagerWorker

) : LoadWeatherUsesCase {

    override suspend fun loadWeathers(coroutineScope: CoroutineScope) {
        val listWorkManagerModel = mutableListOf<UpdateWeatherWMModel>()
        when (repository.loadWeatherFromLocal()) {
            is DataState.Success -> {
                listWorkManagerModel += (repository.loadWeatherFromLocal() as DataState.Success<List<WeatherRepositoryModel>>).data.map {
                    createWorkManagerModel(
                        it
                    )
                }
            }
            else -> {}
        }
        locationManagerInteraction.getCurrentLocation(coroutineScope) { location ->
            listWorkManagerModel += UpdateWeatherWMModel(location.latitude, location.longitude)
            delay(300)
            setUpUploadManagerWorker.setUpWorkerDownloadChain(listWorkManagerModel)
        }

    }

    override suspend fun loadWeathersByCityName(cityName: String): Flow<DataState<List<WeatherUiModel>>> =
        callbackFlow {
            repository.loadWeatherByCityName(
                BuildConfig.apikey,
                cityName = cityName,
                currentTime = System.currentTimeMillis() / 1000,
                language = Locale.getDefault().language
            ).collect {
                when (it) {
                    is DataState.Success -> {
                        var res = listOf(repositoryToUi.mapDomainToDTO(it.data))
                        send(DataState.Success(res))
                    }
                    is DataState.Error -> {
                        send(it)
                    }
                    else -> {}
                }
            }
            awaitClose()
        }

    override suspend fun loadWeatherFromLocalAsFlow(): Flow<DataState<List<WeatherUiModel>>> =
        flow {
            repository.loadWeatherFromLocalAsFlow().collect {
                when (it) {
                    is DataState.Success -> {
                        emit(DataState.Success(it.data.sortedBy { it.name }.map { repositoryToUi.mapDomainToDTO(it) }))
                    }
                    is DataState.Error -> {
                        emit(it)
                    }
                    else -> {}
                }
            }
        }

    override fun checkLocationPermissions(): Boolean =
        locationManagerInteraction.checkLocationPermissions()

    override suspend fun saveWeatherCity() {
        repository.saveWeatherCity()
    }
    private fun createWorkManagerModel(weatherRepositoryModel: WeatherRepositoryModel): UpdateWeatherWMModel =
        weatherRepositoryModel.run {
            UpdateWeatherWMModel(coordRepModel.lat, coordRepModel.lon, name)
        }

}