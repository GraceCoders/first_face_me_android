package com.firstfaceme.firstface.model.Queue

data class PojoGetToken(
    val message: String,
    val status: String,
    val statusCode: Int,
    val token: String,
    val videoGrant: String
)