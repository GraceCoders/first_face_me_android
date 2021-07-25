package com.firstfaceme.firstface.model.registerModel

data class PostRegisterdata(
    val age: String="",
    val bio: String="",
    val countryCode: String="",
    val firstName: String="",
    val gender: Int=0,
    val interestedIn: Int=0,
    val job: String="",
    val lastName: String="",
    val mobileNumber: String="",
    val startPoint: StartPoints=StartPoints()
)

data class StartPoints(
    val lat: Double=0.0,
    val lng: Double=0.0
)