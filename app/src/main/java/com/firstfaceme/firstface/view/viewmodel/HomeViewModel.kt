package com.firstfaceme.firstface.view.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstfaceme.firstface.model.Queue.PojoGetToken
import com.firstfaceme.firstface.model.Queue.PojoQueueList
import com.firstfaceme.firstface.model.call.PojoCall
import com.firstfaceme.firstface.model.call.PojoCallAction
import com.firstfaceme.firstface.model.home.PojoHome
import com.urbanspts.urbanspts.controller.services.APIRepository

class HomeViewModel : AndroidViewModel {
    private lateinit var context: Context

    constructor(application: Application) : super(application) {
        this.context = application
    }

    fun nearByUser(
        authToken: String?,
        id: String?,
        token: String
    ): LiveData<PojoHome> {
        var mutableLiveData: MutableLiveData<PojoHome> = MutableLiveData()


        return APIRepository.nearByUser(authToken, id!!,token)


        return mutableLiveData
    }

    fun addQueue(
        authToken: String?,
        id: String?,
        otherUserID: String?
    ): LiveData<PojoHome> {
        var mutableLiveData: MutableLiveData<PojoHome> = MutableLiveData()


        return APIRepository.addQueue(authToken, id!!, otherUserID!!)


        return mutableLiveData
    }

    fun dislikeUser(
        authToken: String?,
        id: String?,
        otherUserID: String?
    ): LiveData<PojoHome> {
        var mutableLiveData: MutableLiveData<PojoHome> = MutableLiveData()


        return APIRepository.dislikeUser(authToken, id!!, otherUserID!!)


        return mutableLiveData
    }


    fun getTwilioToken(
        authToken: String?,
        id: String?,
        queID: String?
    ): LiveData<PojoGetToken> {
        var mutableLiveData: MutableLiveData<PojoGetToken> = MutableLiveData()


        return APIRepository.getToken(authToken, id!!, queID!!)


        return mutableLiveData
    }

    fun callTwilio(
        authToken: String?,
        id: String?,otherUSerID: String?,
        queID: String?
    ): LiveData<PojoCall> {
        var mutableLiveData: MutableLiveData<PojoCall> = MutableLiveData()


        return APIRepository.callTwilio(authToken, id!!,otherUSerID!!, queID!!)


        return mutableLiveData
    }


   fun rejectCall(
        authToken: String?,
        id: String?,
        otherUSerID: String?): LiveData<PojoCallAction> {
        var mutableLiveData: MutableLiveData<PojoCallAction> = MutableLiveData()


        return APIRepository.rejectCall(authToken, id!!,otherUSerID!!)


        return mutableLiveData
    }

    fun acceptCall(
        authToken: String?,
        id: String?,
        otherUSerID: String?): LiveData<PojoCallAction> {
        var mutableLiveData: MutableLiveData<PojoCallAction> = MutableLiveData()


        return APIRepository.accpetcall(authToken, id!!,otherUSerID!!)


        return mutableLiveData
    }

    fun getQueueUser(
        authToken: String?,
        id: String?
    ): LiveData<PojoQueueList> {
        var mutableLiveData: MutableLiveData<PojoQueueList> = MutableLiveData()


        return APIRepository.getQueue(authToken, id!!)


        return mutableLiveData
    }
}
