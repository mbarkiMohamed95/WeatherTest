package com.example.domain.tools.workmangers.update

import com.example.domain.tools.workmangers.update.model.UpdateWeatherWMModel
import kotlinx.coroutines.CoroutineScope

interface SetUpUploadManagerWorker {
    suspend fun setUpWorkerDownloadChain(
        listPlace: List<UpdateWeatherWMModel>)
}