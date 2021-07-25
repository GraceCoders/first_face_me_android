package com.firstfaceme.firstface.model.setting

data class PostUpdateSetting(
    val maxAge: Int,
    val minAge: Int,
    val radius: Int,
    val userId: String
)