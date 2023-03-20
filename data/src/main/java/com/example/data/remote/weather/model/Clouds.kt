package com.example.data.remote.weather.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Clouds (
	@SerializedName("all") val all : Int
)