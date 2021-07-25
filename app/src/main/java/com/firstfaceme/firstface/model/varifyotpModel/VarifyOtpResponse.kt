package com.firstfaceme.firstface.model.varifyotpModel


import com.google.gson.annotations.SerializedName

data class VarifyOtpResponse(
    val `data`: Data,
    val message: String,
    val status: String,
    val statusCode: Int
)

data class Data(
    val OTP: String,
    val OTPexp: String,
    val _id: String,
    val age: Int,
    val bio: String,
    val countryCode: String,
    val createdAt: String,
    val distance: Int,
    val firebaseToken: Any,
    val firstName: String,
    val gender: Int,
    val images: List<Any>,
    val interestedIn: String,
    val isBlocked: Boolean,
    val isExist: Boolean,
    val isOTPVerified: Boolean,
    val isOnline: Boolean,
    val isRegistered: Boolean,
    val job: String,
    val lastName: String,
    val maxDistance: Int,
    val minDistance: Int,
    val mobileNumber: String,
    val profileImage: String,
    val radius: Int,
    val role: String,
    val timezone: Any,
    val token: String="",
    val updatedAt: String,
    val userLocation: UserLocation
)

data class UserLocation(
    val coordinates: List<Double>,
    val type: String
)