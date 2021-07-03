package com.firstfaceme.firstface.model.registerModel


import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("bio")
    val bio: String = "",
    @SerializedName("countryCode")
    val countryCode: String = "",
    @SerializedName("firstName")
    val firstName: String = "",
    @SerializedName("interestedIn")
    val interestedIn: String = "",
    @SerializedName("job")
    val job: String = "",
    @SerializedName("lastName")
    val lastName: String = "",
    @SerializedName("mobileNumber")
    val mobileNumber: String = "",
    @SerializedName("startPoint")
    val startPoint: StartPoint = StartPoint()
)