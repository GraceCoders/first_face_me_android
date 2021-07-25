package com.firstfaceme.firstface.model.sendOtpModel


import com.google.gson.annotations.SerializedName

data class SendOtpResponse(
    val `data`: Datas,
    val message: String,
    val status: String,
    val statusCode: Int
)

data class Datas(
    val token: String
)