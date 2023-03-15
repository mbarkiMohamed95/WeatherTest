package com.example.weatherapptest.tools.workmangers.update

import com.example.weathersettest.tools.workmangers.update.model.UpdateWeatherWMModel
import kotlinx.coroutines.CoroutineScope

interface SetUpUploadManagerWorker {
    suspend fun setUpWorkerDownloadChain(
        listPlace: List<UpdateWeatherWMModel>)
}