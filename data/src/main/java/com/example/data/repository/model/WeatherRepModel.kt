package com.example.data.repository.model

import com.google.gson.annotations.SerializedName


data class WeatherRepModel (
	@SerializedName("id") val id : Int,
	@SerializedName("main") val main : String,
	@SerializedName("description") val description : String,
	@SerializedName("icon") val icon : String
)