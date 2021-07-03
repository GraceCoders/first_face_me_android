package com.firstfaceme.firstface.model.registerModel


import com.google.gson.annotations.SerializedName

data class UserLocation(
    @SerializedName("coordinates")
    val coordinates: List<Double> = listOf(),
    @SerializedName("type")
    val type: String = ""
)