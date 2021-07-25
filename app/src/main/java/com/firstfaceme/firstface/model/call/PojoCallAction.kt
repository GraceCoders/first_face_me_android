package com.firstfaceme.firstface.model.call

data class PojoCallAction(
    val `data`: Data,
    val message: String,
    val status: String,
    val statusCode: Int
)

data class Datas(
    val _id: String,
    val chatRoom: String,
    val createdAt: String,
    val otherId: String,
    val updatedAt: String,
    val userId: String
)