package com.example.data.local.entitys

import com.google.gson.annotations.SerializedName


data class WeatherLocal (
	@SerializedName("id") val id : Int,
	@SerializedName("main") val main : String,
	@SerializedName("description") val description : String,
	@SerializedName("icon") val icon : String
)