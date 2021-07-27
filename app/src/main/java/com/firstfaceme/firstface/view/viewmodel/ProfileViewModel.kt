package com.firstfaceme.firstface.view.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstfaceme.firstface.model.call.PojoCallAction
import com.firstfaceme.firstface.model.home.PojoHome
import com.firstfaceme.firstface.model.request.PostGetProfile
import com.firstfaceme.firstface.model.request.PostUpdateProfile
import com.firstfaceme.firstface.model.request.StartPoint
import com.firstfaceme.firstface.model.setting.PostUpdateSetting
import com.firstfaceme.firstface.model.subscription.PojoCheckSubscriptin
import com.firstfaceme.firstface.model.userInfo.PojoUserDetail
import com.urbanspts.urbanspts.controller.services.APIRepository
import java.io.File

class ProfileViewModel : AndroidViewModel {
    private lateinit var context: Context

    constructor(application: Application) : super(application) {
        this.context = application
    }

    fun getProfile(
        auth: String?,
        id: String
    ): LiveData<PojoUserDetail> {
        var mutableLiveData: MutableLiveData<PojoUserDetail> = MutableLiveData()
        when {

            else -> {
                val PostGetProfile = PostGetProfile(id)
                return APIRepository.getUserDetail(auth!!, PostGetProfile)
            }
        }
        return mutableLiveData
    }
    fun updateSetting(
        auth: String?,
        id: String,
        radius:Int,
        minAge:Int,
        maxAge:Int
    ): LiveData<PojoUserDetail> {
        var mutableLiveData: MutableLiveData<PojoUserDetail> = MutableLiveData()
        when {

            else -> {
                val postUpdateSetting = PostUpdateSetting(maxAge,minAge,radius,id)
                return APIRepository.updateSetting(auth!!, postUpdateSetting)
            }
        }
        return mutableLiveData
    }

    fun updateProfile(
        auth: String?,
        id: String,
        mImagefile: File?,
        firstName: String,
        lastName: String,
        bio: String,
        job: String,
        genderSelected: Int
    ): LiveData<PojoUserDetail> {


        var mutableLiveData: MutableLiveData<PojoUserDetail> = MutableLiveData()
        when {

            else -> {
                val startPoints = StartPoint(0.0, 0.0)
                val postUpdateProfile = PostUpdateProfile(
                    bio,
                    firstName,
                    genderSelected.toString(),
                    job,
                    lastName,
                    startPoints,
                    id
                )
                return APIRepository.updateProfileDetail(auth!!, postUpdateProfile)
                return mutableLiveData

            }
        }
        return mutableLiveData
    }
    fun updateMultipleImages(
        imageList: File,
        mToken: String,id:String
    ): LiveData<PojoUserDetail> {
        var mutableLiveData: MutableLiveData<PojoUserDetail> = MutableLiveData()
        return APIRepository.updateImage(mToken, id,imageList)
        return mutableLiveData
    }

    fun blockUser(
        mToken: String,
        userID: String,otherUSerID:String
    ): LiveData<PojoHome> {
        var mutableLiveData: MutableLiveData<PojoHome> = MutableLiveData()
        return APIRepository.blockUser(mToken, userID,otherUSerID)
        return mutableLiveData
    }

    fun deleteUser(
        mToken: String,
        userID: String
    ): LiveData<PojoCallAction> {
        var mutableLiveData: MutableLiveData<PojoCallAction> = MutableLiveData()
        return APIRepository.deleteUser(mToken, userID)
        return mutableLiveData
    }

    fun checkSubscription(
        authToken: String?,
        userId:String
    ): LiveData<PojoCheckSubscriptin> {
        var mutableLiveData: MutableLiveData<PojoCheckSubscriptin> = MutableLiveData()

        return APIRepository.checkSubscription(authToken!!,userId!!)


        return mutableLiveData
    }

}
