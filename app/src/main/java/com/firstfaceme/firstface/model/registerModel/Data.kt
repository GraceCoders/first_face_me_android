package com.firstfaceme.firstface.model.registerModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("bio")
    val bio: String = "",
    @SerializedName("countryCode")
    val countryCode: String = "",
    @SerializedName("_id")
    val id: String = "",
    @SerializedName("isRegistered")
    val isRegistered: Boolean = false,
    @SerializedName("job")
    val job: String = "",
    @SerializedName("mobileNumber")
    val mobileNumber: String = "",
    @SerializedName("profileImage")
    val profileImage: String = "",
    @SerializedName("role")
    val role: String = "",
    @SerializedName("token")
    val token: String = "",
    @SerializedName("userLocation")
    val userLocation: UserLocation = UserLocation(),
    @SerializedName("__v")
    val v: Int = 0
)