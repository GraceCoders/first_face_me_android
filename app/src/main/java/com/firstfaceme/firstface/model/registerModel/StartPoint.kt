package com.firstfaceme.firstface.model.registerModel


import com.google.gson.annotations.SerializedName

data class StartPoint(
    @SerializedName("lat")
    val lat: Double = 0.0,
    @SerializedName("lng")
    val lng: Double = 0.0
)