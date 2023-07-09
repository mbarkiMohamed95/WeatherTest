package com.example.weathersettest.presentation.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.loadWeather.manager.loadWeathe.LoadWeatherUsesCase
import com.example.domain.loadWeather.manager.loadWeatherFromLocalAsFlow.LoadWeatherFromLocalAsFlowUseCase
import com.example.domain.loadWeather.model.WeatherUiModel
import com.example.weathersettest.presentation.home.action.HomeAction
import com.example.weathersettest.tools.ui.AsyncState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiModel(var list: AsyncState<List<WeatherUiModel>>? = null)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadWeatherUsesCase: LoadWeatherUsesCase,
    private val loadWeatherFromLocalAsFlowUseCaseUsesCase: LoadWeatherFromLocalAsFlowUseCase
) :
    ViewModel() {

    private val _dataState: MutableStateFlow<HomeUiModel> =
        MutableStateFlow(
            HomeUiModel()
        )

    val dataState: StateFlow<HomeUiModel> get() = _dataState


    fun handleAction(action: HomeAction) {
        viewModelScope.launch {
            when (action) {
                is HomeAction.LoadWeather -> {
                    loadWeatherFromLocalAsFlow()
                }
                is HomeAction.UpdateWeather -> {
                    loadWeather()
                }
            }
        }
    }

    private suspend fun loadWeather() = viewModelScope.launch {
        loadWeatherUsesCase(this)
    }

    private suspend fun loadWeatherFromLocalAsFlow() {
        _dataState.update { uiSate -> uiSate.copy(AsyncState.Loading()) }
        loadWeatherFromLocalAsFlowUseCaseUsesCase().collect() {
            it.onSuccess {
                _dataState.update { uiSate -> uiSate.copy(AsyncState.Success(it)) }
            }.onFailure {
                _dataState.update { uiSate -> uiSate.copy(AsyncState.Failure(it as Exception)) }
            }
        }
    }


}