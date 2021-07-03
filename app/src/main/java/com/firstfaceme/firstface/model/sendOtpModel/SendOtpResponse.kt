package com.firstfaceme.firstface.model.sendOtpModel


import com.google.gson.annotations.SerializedName

data class SendOtpResponse(
    @SerializedName("data")
    val `data`: Data = Data(),
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("statusCode")
    val statusCode: Int = 0
)