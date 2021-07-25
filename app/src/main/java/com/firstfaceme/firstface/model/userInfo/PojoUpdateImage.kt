package com.firstfaceme.firstface.model.userInfo

data class PojoUpdateImage(
    val `data`: Datas,
    val message: String,
    val status: String,
    val statusCode: Int
)

data class Datas(
    val OTP: String,
    val OTPexp: String,
    val _id: String,
    val age: Int,
    val bio: String,
    val countryCode: String,
    val createdAt: String,
    val firebaseToken: String,
    val firstName: String,
    val gender: Int,
    val images: List<String>,
    val interestedIn: String,
    val isBlocked: Boolean,
    val isExist: Boolean,
    val isOTPVerified: Boolean,
    val isOnline: Boolean,
    val isRegistered: Boolean,
    val job: String,
    val lastName: String,
    val mobileNumber: String,
    val profileImage: String,
    val role: String,
    val updatedAt: String,
    val userLocation: UserLocation
)
