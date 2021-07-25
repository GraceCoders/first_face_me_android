package com.firstfaceme.firstface.model.subscription

data class PostPurchasePlan(
    val cost: Double,
    val planId: String,
    val token: String,
    val userId: String
)