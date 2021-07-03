package com.firstfaceme.firstface.view.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstfaceme.firstface.model.sendOtpModel.SendOtpResponse
import com.urbanspts.urbanspts.controller.services.APIRepository
import org.json.JSONObject

class SendOtpViewModel : AndroidViewModel {
    private lateinit var context: Context

    constructor(application: Application) : super(application) {
        this.context = application
    }
    fun sendOtp(mobileNumber: String, countryCode: String): LiveData<SendOtpResponse> {
        var mutableLiveData: MutableLiveData<SendOtpResponse> = MutableLiveData()
        when {

            else -> {
                val requestObject = JSONObject()
                requestObject.put("mobileNumber", mobileNumber)
                requestObject.put("countryCode", countryCode)
                return APIRepository.sendOtp(requestObject)
            }
        }
        return mutableLiveData
    }
}