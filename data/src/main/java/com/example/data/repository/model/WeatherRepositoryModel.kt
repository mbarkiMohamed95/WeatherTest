package com.example.data.repository.model

data class WeatherRepositoryModel(
    val dt:Int,
    val coordRepModel: CoordRepModel,
    val weatherLocal: List<WeatherRepModel>,
    val base: String,
    val mainRepModel: MainRepModel,
    val visibility: Int,
    val wind: WindRepModel,
    val cloudsRepModel: CloudsRepModel,
    val sysRepModel: SysRepModel,
    val timezone: Int,
    val name: String,
    val cod: Int
)