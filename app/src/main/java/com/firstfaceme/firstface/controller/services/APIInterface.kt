package com.firstfaceme.firstface.controller.repository


import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.model.registerModel.RegisterProfileResponse
import com.firstfaceme.firstface.model.sendOtpModel.SendOtpResponse
import com.firstfaceme.firstface.model.varifyotpModel.VarifyOtpResponse
import okhttp3.RequestBody
import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by nishant on 03/07/20.
 */
interface APIInterface {
  @Headers("Content-Type: application/json")
//    @POST(Constants.API_CATEGORY)
//    fun categories(): Call<CategoryResponse>
//
    @POST(Constants.SEND_OTP)
    fun sendOtp(
        @Body requestBody: RequestBody
    ): Call<SendOtpResponse>

    @POST(Constants.VARIFY_OTP)
    fun varifyOtp(
        @Body requestBody: RequestBody
    ): Call<VarifyOtpResponse>

    @POST(Constants.REGISTER)
    fun registerProfile(
        @Body requestBody: RequestBody
    ): Call<RegisterProfileResponse>

//    @GET(Constants.API_WALLET_BALANCE)
//    fun walletBalance(
//        @Header("Authorization") authToken: String,
//        @Query("username") username: String
//    ): Call<ResponseBody>
//
//    @GET(Constants.API_ACCOUNT_STATUS+"/{username}/account-status")
//    fun accountStatus(
//        @Header("Authorization") authToken: String,
//        @Path("username") username: String
//    ): Call<ResponseBody>
//
//    @GET(Constants.API_HISTORY)
//    fun history(
//        @Header("Authorization") authToken: String,
//        @Query("username") username: String
//    ): Call<ResponseBody>
}