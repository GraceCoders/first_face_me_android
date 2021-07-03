package com.firstfaceme.firstface.model.sendOtpModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("token")
    val token: String = ""
)