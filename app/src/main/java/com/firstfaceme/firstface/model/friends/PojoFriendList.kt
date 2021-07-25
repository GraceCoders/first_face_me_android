package com.firstfaceme.firstface.model.friends

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PojoFriendList(
    val `data`: List<Data>,
    val message: String,
    val status: String,
    val statusCode: Int)

@Parcelize
data class Data(
    val _id: String,
    val chatRoom: String,
    val createdAt: String,
    val otherId: OtherId,
    val updatedAt: String,
    val userId: String
):Parcelable

@Parcelize
data class OtherId(
    val _id: String,
    val firstName: String,
    val isOnline: Boolean,
    val lastName: String,
    val mobileNumber: String,
    val profileImage: String
):Parcelable
