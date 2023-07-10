package com.example.data.repository.manager


import android.util.Log
import com.example.data.di.IoDispatcher
import com.example.data.local.entitys.WeatherLocalModel
import com.example.data.local.localManager.LocalWeatherManager
import com.example.data.remote.weather.manager.WeatherNetworkManager
import com.example.data.remote.weather.model.WeatherModel
import com.example.data.repository.model.WeatherRepositoryModel
import com.example.data.repository.weatherdto.MappingWeatherLocalToRepository
import com.example.data.repository.weatherdto.MappingWeatherNetWorkToRepository
import com.example.data.repository.weatherdto.MappingWeatherRemoteToLocal
import com.example.data.utils.runCatchingAndMapToDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImp @Inject constructor(
    private val localWeatherManager: LocalWeatherManager,
    private val weatherNetworkManager: WeatherNetworkManager,
    private val remoteToLocal: MappingWeatherRemoteToLocal,
    private val localToRepository: MappingWeatherLocalToRepository,
    private val netWorkToRepository: MappingWeatherNetWorkToRepository,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {
    private var searchedWeatherCity: WeatherLocalModel? = null
    private var citys = mutableListOf<WeatherModel>()

    /**
     * this function used in the work manager
     * when the app launched
     * i create a worker for each city in local data base
     * each worker use this function to update the data of here city
     * when all the data updated i saved in the local data base
     * and i create a flow to observe all the database change  to update the ui using loadWeatherFromLocalAsFlow()
     */
    override suspend fun loadWeather(
        apiKey: String,
        cityNumber: Int?,
        latitude: Double?,
        longitude: Double?,
        language: String?,
        currentTime: Long?,
        cityName: String?,
        callback: (Boolean) -> Unit
    ) {
        try {
            weatherNetworkManager.loadWeatherWithKtor(
                apiKey,
                latitude,
                longitude,
                language,
                currentTime,
                cityName
            ).let {
                it.let {
                    citys += it
                    if (citys.size == cityNumber) {
                        localWeatherManager.saveListWeather(remoteToLocal.mapInputToOutput(citys))
                        callback(true)
                        citys.clear()
                    }
                }
            }
        }catch (ex:Exception){
            Log.i("TAG", "loadWeather: ${ex.message}")
        }

    }

    /**
     * this function used to load the weather data using the city Name used in the search view
     */
    override suspend fun loadWeatherByCityName(
        apiKey: String,
        language: String?,
        currentTime: Long?,
        cityName: String?
    ): Result<WeatherRepositoryModel> = Result.runCatchingAndMapToDomain(netWorkToRepository) {
        withContext(ioDispatcher) {
            weatherNetworkManager.loadWeather(
                apiKey,
                language = language,
                currentTime = currentTime,
                cityName = cityName
            ).also {
                searchedWeatherCity=remoteToLocal.mapInputToOutput(it.getOrThrow())
            }
        }

    }

    /**
     * bserve all the database change  to update the ui using this function
     *
     */
    override suspend fun loadWeatherFromLocalAsFlow(): Flow<Result<List<WeatherRepositoryModel>>> =
        flow {
            localWeatherManager.getAllWeathersAsFlow().collect { res ->
                if (!res.isNullOrEmpty()) {
                    emit(Result.success(res.map { item ->
                        localToRepository.mapInputToOutput(
                            item
                        )
                    }))
                } else {
                    emit(Result.failure(Exception("empty")))
                }
            }
        }

    /**
     * i created this function to load all the saved data and provide it to the workManager to create the worker
     */
    override suspend fun loadWeatherFromLocal(): Result<List<WeatherRepositoryModel>> {
        val res = localWeatherManager.getAllWeathers()
        return if (res.isNotEmpty()) {
            Result.success(localToRepository.mapInputToOutput(res))
        } else {
            Result.failure((Exception("empty")))
        }
    }

    /**
     * there is not a raison to load the detail from web services so i loaded it from local data base using this function
     */
    override suspend fun loadWeatherByNameCityFromLocal(cityName: String): WeatherRepositoryModel {
        return localToRepository.mapInputToOutput(localWeatherManager.loadWeatherByNameCityFromLocal(cityName))
    }

    /**
     * this function used to save the searched weather
     */
    override suspend fun saveWeatherCity() {
        searchedWeatherCity?.let {
            localWeatherManager.saveWeather(it)
        }
    }

}