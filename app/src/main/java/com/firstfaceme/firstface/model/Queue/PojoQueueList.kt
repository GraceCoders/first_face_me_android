package com.firstfaceme.firstface.model.Queue

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PojoQueueList(
    val `data`: List<Data>,
    val message: String,
    val status: String,
    val statusCode: Int,
    val totalCount: Int
)
@Parcelize
data class Data(
    val _id: String="",
    val createdAt: String="",
    val queId: QueId=QueId(),
    val updatedAt: String="",
    val userId: String=""
)
    :Parcelable@Parcelize

data class QueId(
    val _id: String="",
    val age: Int=0,
    val firstName: String="",
    val gender: Int=0,
    val isOnline: Boolean=false,
    val lastName: String="",
    val mobileNumber: String="",
    val profileImage: String=""
)    :Parcelable
