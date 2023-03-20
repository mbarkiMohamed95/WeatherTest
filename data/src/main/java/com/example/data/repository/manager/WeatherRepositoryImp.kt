package com.example.data.repository.manager


import com.example.data.local.entitys.WeatherLocalModel
import com.example.data.local.localManager.LocalWeatherManager
import com.example.data.remote.weather.manager.WeatherNetworkManager
import com.example.data.remote.weather.model.WeatherModel
import com.example.data.repository.model.WeatherRepositoryModel
import com.example.data.repository.weatherdto.MappingWeatherLocalToRepository
import com.example.data.repository.weatherdto.MappingWeatherNetWorkToRepository
import com.example.data.repository.weatherdto.MappingWeatherRemoteToLocal
import com.example.data.utils.DataState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImp @Inject constructor(
    private val localWeatherManager: LocalWeatherManager,
    private val weatherNetworkManager: WeatherNetworkManager,
    private val remoteToLocal: MappingWeatherRemoteToLocal,
    private val localToRepository: MappingWeatherLocalToRepository,
    private val netWorkToRepository: MappingWeatherNetWorkToRepository
) : WeatherRepository {
    private var searchedWeatherCity:WeatherLocalModel?=null
    private var citys= mutableListOf<WeatherModel>()
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
        weatherNetworkManager.loadWeatherWithKtor(
            apiKey,
            latitude,
            longitude,
            language,
            currentTime,
            cityName
        ).collect {
            it.let {
                citys += it
                if (citys.size == cityNumber){
                    localWeatherManager.saveListWeather(remoteToLocal.mapDomainToDTO(citys))
                    delay(100)
                    callback(true)
                    citys.clear()
                }
            }
        }
    }
    override suspend fun loadWeatherByCityName(
        apiKey: String,
        language: String?,
        currentTime: Long?,
        cityName: String?
    ): Flow<DataState<WeatherRepositoryModel>> = callbackFlow{
        weatherNetworkManager.loadWeather(
            apiKey,
            language = language,
            currentTime = currentTime,
            cityName = cityName
        ).collect {
            it?.let {
                searchedWeatherCity=remoteToLocal.mapDomainToDTO(it)
              send(DataState.Success(netWorkToRepository.mapDomainToDTO(it)))
            } ?: kotlin.run {
                send(DataState.Error())
            }
        }
        awaitClose()
    }

    override suspend fun loadWeatherFromLocalAsFlow(): Flow<DataState<List<WeatherRepositoryModel>>> =
        flow {
            localWeatherManager.getAllWeathersAsFlow().collect { res ->
                if (!res.isNullOrEmpty()){
                    emit(DataState.Success(res.map { item ->
                        localToRepository.mapDomainToDTO(
                            item
                        )
                    }))
                }else{
                    emit(DataState.Error(Exception("empty")))
                }

            }
        }

    override suspend fun loadWeatherFromLocal(): DataState<List<WeatherRepositoryModel>> {
        val res = localWeatherManager.getAllWeathers()
        return if (res.isNotEmpty()) {
            DataState.Success(localToRepository.mapDomainToDTO(res))
        } else {
            DataState.Error(Exception("empty"))
        }
    }

    override suspend fun loadWeatherByNameCityFromLocal(cityName: String): WeatherRepositoryModel {
        return localToRepository.mapDomainToDTO(localWeatherManager.loadWeatherByNameCityFromLocal(cityName))
    }

    override suspend fun saveWeatherCity() {
        searchedWeatherCity?.let {
            localWeatherManager.saveWeather(it)
        }
    }

}