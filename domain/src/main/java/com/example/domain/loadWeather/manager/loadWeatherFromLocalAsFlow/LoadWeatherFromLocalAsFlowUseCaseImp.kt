package com.example.domain.loadWeather.manager.loadWeatherFromLocalAsFlow

import com.example.data.repository.manager.WeatherRepository
import com.example.domain.loadWeather.dto.MappingWeatherRepositoryToUi
import com.example.domain.loadWeather.model.WeatherUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadWeatherFromLocalAsFlowUseCaseImp @Inject constructor(
    private val repository: WeatherRepository,
    private val repositoryToUi: MappingWeatherRepositoryToUi
) :
    LoadWeatherFromLocalAsFlowUseCase {
     override fun invoke(): Flow<Result<List<WeatherUiModel>>> =
        flow {
            repository.loadWeatherFromLocalAsFlow().collect {
                it.onSuccess {
                    emit(Result.success(it.sortedBy { it.name }.map { repositoryToUi.mapInputToOutput(it) }))
                }.onFailure {
                    emit(Result.failure(it))
                }
            }
        }
}