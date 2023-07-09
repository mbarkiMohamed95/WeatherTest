package com.example.domain.loadWeather.manager.loadWeathe

import com.example.data.repository.manager.WeatherRepository
import com.example.data.repository.model.WeatherRepositoryModel
import com.example.weatherapptest.tools.location.LocationManagerInteraction
import com.example.domain.tools.workmangers.update.SetUpUploadManagerWorker
import com.example.domain.loadWeather.dto.MappingWeatherRepositoryToUi
import com.example.domain.loadWeather.model.WeatherUiModel
import com.example.domain.tools.workmangers.update.model.UpdateWeatherWMModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class LoadWeatherUsesCaseImp @Inject constructor(
    private val locationManagerInteraction: LocationManagerInteraction,
    private val repository: WeatherRepository,
    private val setUpUploadManagerWorker: SetUpUploadManagerWorker

) : LoadWeatherUsesCase {

    override suspend operator fun invoke(coroutineScope: CoroutineScope) {
        val listWorkManagerModel = mutableListOf<UpdateWeatherWMModel>()
        repository.loadWeatherFromLocal().onSuccess {
            listWorkManagerModel += (repository.loadWeatherFromLocal()).getOrThrow().map {
                createWorkManagerModel(
                    it
                )
            }
        }
        locationManagerInteraction.getCurrentLocation(coroutineScope) { location ->
            listWorkManagerModel += UpdateWeatherWMModel(location.latitude, location.longitude)
            delay(300)
            setUpUploadManagerWorker.setUpWorkerDownloadChain(listWorkManagerModel)
        }

    }


//    override fun checkLocationPermissions(): Boolean =
//        locationManagerInteraction.checkLocationPermissions()
//
//    override suspend fun saveWeatherCity() {
//        repository.saveWeatherCity()
//    }
    private fun createWorkManagerModel(weatherRepositoryModel: WeatherRepositoryModel): UpdateWeatherWMModel =
        weatherRepositoryModel.run {
            UpdateWeatherWMModel(coordRepModel.lat, coordRepModel.lon, name)
        }

}