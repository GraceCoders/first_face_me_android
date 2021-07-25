package com.firstfaceme.firstface.model.subscription

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PojoGetPlanList(
    val `data`: List<Data>,
    val message: String,
    val status: String,
    val statusCode: Int
)
@Parcelize
data class Data(
    val _id: String="",
    val createdAt: String="",
    val name: String="",
    val price: Double=0.0,
    val updatedAt: String=""
):Parcelable