package com.firstfaceme.firstface.view.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstfaceme.firstface.model.registerModel.RegisterProfileResponse
import com.firstfaceme.firstface.model.registerModel.RegisterRequest
import com.urbanspts.urbanspts.controller.services.APIRepository

class RegisterViewModel : AndroidViewModel {
    private lateinit var context: Context
    constructor(application: Application) : super(application) {
        this.context = application
    }
    fun registerProfile(mobileNumber: String, countryCode: String): LiveData<RegisterProfileResponse> {
        var mutableLiveData: MutableLiveData<RegisterProfileResponse> = MutableLiveData()
        when {

            else -> {
                val registerRequest=RegisterRequest()
                registerRequest.bio==mobileNumber
                registerRequest.bio==mobileNumber

                return APIRepository.registerProfile(registerRequest)
            }
        }
        return mutableLiveData
    }
}