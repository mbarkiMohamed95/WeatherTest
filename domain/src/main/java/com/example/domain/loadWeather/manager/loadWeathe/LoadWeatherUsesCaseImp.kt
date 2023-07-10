package com.example.domain.loadWeather.manager.loadWeathe

import androidx.work.WorkManager
import com.example.data.repository.manager.WeatherRepository
import com.example.data.repository.model.WeatherRepositoryModel
import com.example.weatherapptest.tools.location.LocationManagerInteraction
import com.example.domain.tools.workmangers.update.SetUpUploadManagerWorker
import com.example.domain.tools.workmangers.update.model.UpdateWeatherWMModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoadWeatherUsesCaseImp @Inject constructor(
    private val locationManagerInteraction: LocationManagerInteraction,
    private val repository: WeatherRepository,
    private val setUpUploadManagerWorker: SetUpUploadManagerWorker) : LoadWeatherUsesCase {

    override suspend operator fun invoke(coroutineScope: CoroutineScope): Int {
        val listWorkManagerModel = mutableListOf<UpdateWeatherWMModel>(
            UpdateWeatherWMModel(cityName = "Rennes"),
            UpdateWeatherWMModel(cityName = "Paris"),
            UpdateWeatherWMModel(cityName = "Nantes"),
            UpdateWeatherWMModel(cityName = "Bordeaux "),
            UpdateWeatherWMModel(cityName = "Lyon"),
        )
        repository.loadWeatherFromLocal().onSuccess {
            listWorkManagerModel += (repository.loadWeatherFromLocal()).getOrThrow().map {
                createWorkManagerModel(
                    it
                )
            }
        }
        locationManagerInteraction.getCurrentLocation(coroutineScope).collect { location ->
            location?.let {
                listWorkManagerModel += UpdateWeatherWMModel(location.latitude, location.longitude)
            }
            delay(300)
            setUpUploadManagerWorker.setUpWorkerDownloadChain(listWorkManagerModel)
        }
        return listWorkManagerModel.size
    }

    private fun createWorkManagerModel(weatherRepositoryModel: WeatherRepositoryModel): UpdateWeatherWMModel =
        weatherRepositoryModel.run {
            UpdateWeatherWMModel(coordRepModel.lat, coordRepModel.lon, name)
        }

}