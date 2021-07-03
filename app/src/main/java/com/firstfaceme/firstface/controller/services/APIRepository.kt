package com.urbanspts.urbanspts.controller.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstfaceme.firstface.controller.repository.APIClient
import com.firstfaceme.firstface.controller.repository.APIInterface
import com.firstfaceme.firstface.controller.utills.App
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.model.registerModel.RegisterProfileResponse
import com.firstfaceme.firstface.model.registerModel.RegisterRequest
import com.firstfaceme.firstface.model.sendOtpModel.SendOtpResponse
import com.firstfaceme.firstface.model.varifyotpModel.VarifyOtpResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object APIRepository {
   private var apiInterface: APIInterface = APIClient.retrofit(Constants.BASE_URL_API)


    //sendOtp
    fun sendOtp(
        requestObject: JSONObject
    ): LiveData<SendOtpResponse> {
        val mutableLiveData: MutableLiveData<SendOtpResponse> = MutableLiveData()
        val requestBody: RequestBody =
            requestObject.toString().toRequestBody("application/json".toMediaType())
        val call = apiInterface.sendOtp(requestBody)
        call.enqueue(object : Callback<SendOtpResponse> {
            override fun onResponse(call: Call<SendOtpResponse>, response: Response<SendOtpResponse>) {
                if (response.isSuccessful) {
                    try {
                        val sendOtpResponse: SendOtpResponse? = response.body()
                        if (response.body()?.statusCode == 200) {

                            AppPreferences.init(App.getAppContext())
                                .putString(Constants.AUTH_TOKEN, sendOtpResponse?.data?.token)

                        }
                        mutableLiveData.value = sendOtpResponse
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<SendOtpResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

    //varify otp
    fun varifyOtp(
        requestObject: JSONObject
    ): LiveData<VarifyOtpResponse> {
        val mutableLiveData: MutableLiveData<VarifyOtpResponse> = MutableLiveData()
        val requestBody: RequestBody =
            requestObject.toString().toRequestBody("application/json".toMediaType())
        val call = apiInterface.varifyOtp(requestBody)
        call.enqueue(object : Callback<VarifyOtpResponse> {
            override fun onResponse(call: Call<VarifyOtpResponse>, response: Response<VarifyOtpResponse>) {
                if (response.isSuccessful) {
                    try {
                        val varifyOtpResponse: VarifyOtpResponse? = response.body()
                        if (response.body()?.statusCode == 200) {

                            AppPreferences.init(App.getAppContext())
                                .putString(Constants.AUTH_TOKEN, varifyOtpResponse?.data?.token)
                            AppPreferences.init(App.getAppContext())
                                .putString(Constants.MOBILE_NUMBER, varifyOtpResponse?.data?.mobileNumber)
                            AppPreferences.init(App.getAppContext())
                                .putString(Constants.COUNTRY_CODE, varifyOtpResponse?.data?.countryCode)

                        }
                        mutableLiveData.value = varifyOtpResponse
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<VarifyOtpResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

    //registerProfile

    fun registerProfile(
        requestObject: RegisterRequest
    ): LiveData<RegisterProfileResponse> {
        val mutableLiveData: MutableLiveData<RegisterProfileResponse> = MutableLiveData()
        val requestBody: RequestBody =
            requestObject.toString().toRequestBody("application/json".toMediaType())
        val call = apiInterface.registerProfile(requestBody)
        call.enqueue(object : Callback<RegisterProfileResponse> {
            override fun onResponse(call: Call<RegisterProfileResponse>, response: Response<RegisterProfileResponse>) {
                if (response.isSuccessful) {
                    try {
                        val registerProfileResponse: RegisterProfileResponse? = response.body()
                        if (response.body()?.statusCode == 200) {

//                            AppPreferences.init(App.getAppContext())
//                                .putString(Constants.AUTH_TOKEN, varifyOtpResponse?.data?.token)
//                            AppPreferences.init(App.getAppContext())
//                                .putString(Constants.MOBILE_NUMBER, varifyOtpResponse?.data?.mobileNumber)
//                            AppPreferences.init(App.getAppContext())
//                                .putString(Constants.COUNTRY_CODE, varifyOtpResponse?.data?.countryCode)

                        }
                        mutableLiveData.value = registerProfileResponse
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<RegisterProfileResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }
}