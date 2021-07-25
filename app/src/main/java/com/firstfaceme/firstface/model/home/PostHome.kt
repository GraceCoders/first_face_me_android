package com.firstfaceme.firstface.model.home

data class PostHome(
    val page: Int=0,
    val pageSize: Int=100,
    val userId: String="",
    val firebaseToken: String=""
)