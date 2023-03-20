package com.example.data.remote.weather.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Coord (
	@SerializedName("lon") val lon : Double,
	@SerializedName("lat") val lat : Double
)