package com.example.data.repository.model

import com.google.gson.annotations.SerializedName

data class SysRepModel (
	@SerializedName("type") val type : Int,
	@SerializedName("id") val id : Int,
	@SerializedName("country") val country : String,
	@SerializedName("sunrise") val sunrise : Int,
	@SerializedName("sunset") val sunset : Int
)