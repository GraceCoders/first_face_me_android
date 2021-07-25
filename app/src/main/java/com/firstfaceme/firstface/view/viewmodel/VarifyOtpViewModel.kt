package com.firstfaceme.firstface.view.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstfaceme.firstface.model.registerModel.RegisterProfileResponse
import com.firstfaceme.firstface.model.request.PostGetProfile
import com.firstfaceme.firstface.model.varifyotpModel.VarifyOtpResponse
import com.urbanspts.urbanspts.controller.services.APIRepository
import org.json.JSONObject

class VarifyOtpViewModel : AndroidViewModel {
    private lateinit var context: Context
    constructor(application: Application) : super(application) {
        this.context = application
    }
    fun varifyOtp(
        mobileNumber: String?,
        countryCode: String?,
        otpCode: String?
    ): LiveData<VarifyOtpResponse> {
        var mutableLiveData: MutableLiveData<VarifyOtpResponse> = MutableLiveData()
        when {

            else -> {
                val requestObject = JSONObject()
                requestObject.put("mobileNumber", mobileNumber)
                requestObject.put("countryCode", countryCode)
                requestObject.put("OTP", otpCode)
                return APIRepository.varifyOtp(requestObject)
            }
        }
        return mutableLiveData
    }
}
