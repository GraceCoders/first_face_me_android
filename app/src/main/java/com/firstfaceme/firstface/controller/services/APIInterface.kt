package com.firstfaceme.firstface.controller.repository


import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.model.PojoDeleteImage
import com.firstfaceme.firstface.model.Queue.*
import com.firstfaceme.firstface.model.call.PojoCall
import com.firstfaceme.firstface.model.call.PojoCallAction
import com.firstfaceme.firstface.model.call.PostCall
import com.firstfaceme.firstface.model.deleteAccount.PostDeleteAccount
import com.firstfaceme.firstface.model.friends.PojoFriendList
import com.firstfaceme.firstface.model.friends.PostGetFriends
import com.firstfaceme.firstface.model.home.PojoHome
import com.firstfaceme.firstface.model.home.PostHome
import com.firstfaceme.firstface.model.registerModel.PostRegisterdata
import com.firstfaceme.firstface.model.registerModel.RegisterProfileResponse
import com.firstfaceme.firstface.model.request.PostGetProfile
import com.firstfaceme.firstface.model.request.PostUpdateProfile
import com.firstfaceme.firstface.model.sendOtpModel.SendOtpResponse
import com.firstfaceme.firstface.model.setting.PostUpdateSetting
import com.firstfaceme.firstface.model.subscription.PojoGetPlanList
import com.firstfaceme.firstface.model.subscription.PostPurchasePlan
import com.firstfaceme.firstface.model.userInfo.PojoUpdateImage
import com.firstfaceme.firstface.model.userInfo.PojoUserDetail
import com.firstfaceme.firstface.model.varifyotpModel.VarifyOtpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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

    @Headers("Content-Type: application/json")
    @POST(Constants.REGISTER)
    fun registerProfile(
        @Header("Authorization") authorization: String?,
        @Body requestBody: PostRegisterdata
    ): Call<RegisterProfileResponse>

    //get user by Id
    @Headers("Content-Type: application/json")
    @POST(Constants.USER_DETAIL)
    fun userDetail(
        @Header("token") authorization: String?,
        @Body id: PostGetProfile,
    ): Call<PojoUserDetail>

    // update setting
    @Headers("Content-Type: application/json")
    @POST(Constants.API_SETTING)
    fun updateSetting(
        @Header("token") authorization: String?,
        @Body id: PostUpdateSetting,
    ): Call<PojoUserDetail>


    //update user Image
    @Multipart
    @POST(Constants.API_UPDATE_PROFILE_IMAGE)
    fun updateImage(
        @Header("token") authorization: String?,

        @Part("userId") id: RequestBody?,
        @Part profile_image: MultipartBody.Part?

    ): Call<PojoUpdateImage?>?


    @Headers("Content-Type: application/json")
    @POST(Constants.API_UPDATE_PROFILE)
    fun updateProfile(
        @Header("token") authorization: String?,
        @Body requestBody: PostUpdateProfile
    ): Call<PojoUserDetail>

    //update user Image
    @Multipart
    @POST(Constants.API_UPDATE_IMAGE)
    fun updateImages(
        @Header("token") authorization: String?,

        @Part("userId") id: RequestBody?,
        @Part profile_image: MultipartBody.Part?

    ): Call<PojoUserDetail?>?

    @Headers("Content-Type: application/json")
    @POST(Constants.API_DELETE_IMAGE)
    fun deleteImage(
        @Header("token") authorization: String?,
        @Body pojoDeleteImage: PojoDeleteImage
    ): Call<PojoUserDetail>

    @Headers("Content-Type: application/json")
    @POST(Constants.API_HOME_DATA)
    fun nearByUser(
        @Header("token") authorization: String?,
        @Body postHome: PostHome
    ): Call<PojoHome>

    @Headers("Content-Type: application/json")
    @POST(Constants.API_BLOCK_USER)
    fun blockUser(
        @Header("token") authorization: String?,
        @Body postHome: PostAddQueue
    ): Call<PojoHome>

    @Headers("Content-Type: application/json")
    @POST(Constants.API_ADD_QUEUE)
    fun addQueue(
        @Header("token") authorization: String?,
        @Body postHome: PostAddQueue
    ): Call<PojoHome>

    @Headers("Content-Type: application/json")
    @POST(Constants.API_DISLIKE)
    fun dislikeUser(
        @Header("token") authorization: String?,
        @Body pojoDislike: PojoDislike
    ): Call<PojoHome>


    @Headers("Content-Type: application/json")
    @POST(Constants.API_GET_QUEUE)
    fun getQueue(
        @Header("token") authorization: String?,
        @Body postGetQueue: PostGetQueue
    ): Call<PojoQueueList>

    @Headers("Content-Type: application/json")
    @GET(Constants.API_GET_PLAN)
    fun getAllPlans(
        @Header("token") authorization: String?
    ): Call<PojoGetPlanList>


    @Headers("Content-Type: application/json")
    @POST(Constants.API_PURCHASE_PLAN)
    fun purchasePlan(
        @Header("token") authorization: String?, @Body postPurchasePlan: PostPurchasePlan
    ): Call<PojoGetPlanList>

    @Headers("Content-Type: application/json")
    @POST(Constants.API_FRIEND_LIST)
    fun getFriendList(
        @Header("token") authorization: String?, @Body postGetFriends: PostGetFriends
    ): Call<PojoFriendList>


    @Headers("Content-Type: application/json")
    @POST(Constants.API_GET_TWILIO_TOKEN)
    fun getTwilioToken(
        @Header("token") authorization: String?, @Body postGetToken: PostGetToken
    ): Call<PojoGetToken>

    @Headers("Content-Type: application/json")
    @POST(Constants.API_GET_CALL)
    fun call(
        @Header("token") authorization: String?, @Body postGetToken: PostCall
    ): Call<PojoCall>

    @Headers("Content-Type: application/json")
    @POST(Constants.API_GET_ACCEPT_CALL)
    fun acceptCall(
        @Header("token") authorization: String?, @Body postAddQueue: PostAddQueue
    ): Call<PojoCallAction>

    @Headers("Content-Type: application/json")
    @POST(Constants.API_GET_REJECT_CALL)
    fun rejectCall(
        @Header("token") authorization: String?, @Body postAddQueue: PostAddQueue
    ): Call<PojoCallAction>


    @Headers("Content-Type: application/json")
    @POST(Constants.API_DELETE_ACCOUNT)
    fun deleteAccount(
        @Header("token") authorization: String?, @Body postAddQueue: PostDeleteAccount
    ): Call<PojoCallAction>
}