package com.firstfaceme.firstface.model.home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PojoHome(

    val `data`: Data,
    val message: String,
    val status: String,
    val statusCode: Int
)

data class Data(
    val result: List<Result>,
    val total: Int
)
@Parcelize
data class Result(
    val OTP: String="",
    val OTPexp: String="",
    val __v: Int=0,
    val _id: String="",
    val age: Int=0,
    val bio: String="",
    val distance: String="",
    val firstName: String="",
    val gender: Int=0,
    val interestedIn: String="",
    val isExist: Boolean=false,
    val isOnline: Boolean=false,
    val isRegistered: Boolean=false,
    val job: String="",
    val lastName: String="",
    val maxAge: Int=0,
    val minAge: Int=0,
    val profileImage: String="",
    val role: String="",
    val subscriptionDetail: String="",
    val userLocation: UserLocation= UserLocation()
)
    :Parcelable
@Parcelize
data class UserLocation(
    val coordinates: List<Double> = mutableListOf(),
    val type: String=""
):Parcelable