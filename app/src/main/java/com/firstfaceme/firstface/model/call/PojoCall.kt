package com.firstfaceme.firstface.model.call

data class PojoCall(
    val `data`: Data,
    val message: String,
    val status: String,
    val statusCode: Int
)

data class Data(
    val body: String,
    val image: String,
    val queId: String,
    val registrationToken: String,
    val title: String,
    val type: String
)