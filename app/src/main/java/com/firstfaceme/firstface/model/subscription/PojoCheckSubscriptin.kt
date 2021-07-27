package com.firstfaceme.firstface.model.subscription

data class PojoCheckSubscriptin(
    val `data`: Datass,
    val message: String,
    val status: String,
    val statusCode: Int
)

data class Datass(
    val _id: String
)