package com.example.data.remote.weather.model

import com.example.data.remote.weather.model.*
import com.google.gson.annotations.SerializedName

import kotlinx.serialization.Serializable
@Serializable
data class WeatherModel (
    @SerializedName("dt") val dt : Int,
    @SerializedName("coord") val coord : Coord,
    @SerializedName("weather") val weather : List<Weather>,
    @SerializedName("base") val base : String,
    @SerializedName("main") val main : Main,
    @SerializedName("visibility") val visibility : Int,
    @SerializedName("wind") val wind : Wind,
    @SerializedName("clouds") val clouds : Clouds,
    @SerializedName("sys") val sys : Sys,
    @SerializedName("timezone") val timezone : Int,
    @SerializedName("name") val name : String,
    @SerializedName("id") val id : Int,
    @SerializedName("cod") val cod : Int
)
