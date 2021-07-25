package com.firstfaceme.firstface.model.request

data class PostUpdateProfile(
    val bio: String ="",
    val firstName: String,
    val interestedIn: String,
    val job: String,
    val lastName: String,
    val startPoint: StartPoint,
    val userId: String
)



data class StartPoint(
    val lat: Double,
    val lng: Double
)