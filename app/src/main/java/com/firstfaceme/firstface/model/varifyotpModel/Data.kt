package com.firstfaceme.firstface.model.varifyotpModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("countryCode")
    val countryCode: String = "",
    @SerializedName("isExist")
    val isExist: Boolean = false,
    @SerializedName("mobileNumber")
    val mobileNumber: String = "",
    @SerializedName("OTP")
    val oTP: String = "",
    @SerializedName("token")
    val token: String = ""
)