package com.firstfaceme.firstface.view.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstfaceme.firstface.model.registerModel.PostRegisterdata
import com.firstfaceme.firstface.model.registerModel.RegisterProfileResponse
import com.firstfaceme.firstface.model.registerModel.StartPoints
import com.firstfaceme.firstface.model.userInfo.PojoUpdateImage
import com.firstfaceme.firstface.model.userInfo.PojoUserDetail
import com.urbanspts.urbanspts.controller.services.APIRepository
import java.io.File

class RegisterViewModel : AndroidViewModel {
    private lateinit var context: Context

    constructor(application: Application) : super(application) {
        this.context = application
    }

    fun registerProfile(
        mToken: String,
        mobileNumber: String,
        countryCode: String,
        firstName: String,
        lastName: String,
        age: String,
        bio: String,
        job: String,
        gender: Int,
        interestedIn: Int, lat: Double, lng: Double
    ): LiveData<RegisterProfileResponse> {
        var mutableLiveData: MutableLiveData<RegisterProfileResponse> = MutableLiveData()


        val startPoint = StartPoints(lat, lng)
        val registerRequest = PostRegisterdata(
            age,
            bio,
            countryCode,
            firstName,
            gender,
            interestedIn,
            job,
            lastName,
            mobileNumber,
            startPoint
        )


        return APIRepository.registerProfile(mToken, registerRequest)


        return mutableLiveData
    }

    fun updateProfileImage(
        mToken: String,
        id: String,
        userImage: File
    ): LiveData<PojoUpdateImage> {
        var mutableLiveData: MutableLiveData<PojoUpdateImage> = MutableLiveData()
        return APIRepository.updateProfileImage(mToken, id, userImage)


        return mutableLiveData
    }
    fun deleteImage(
        imageId: String,
        mToken: String,
        mUserId: String
    ): LiveData<PojoUserDetail> {
        var mutableLiveData: MutableLiveData<PojoUserDetail> = MutableLiveData()
        return APIRepository.deleteImage(mToken, imageId,mUserId)
        return mutableLiveData
    }


}