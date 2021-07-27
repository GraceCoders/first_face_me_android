package com.urbanspts.urbanspts.controller.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstfaceme.firstface.controller.repository.APIClient
import com.firstfaceme.firstface.controller.repository.APIInterface
import com.firstfaceme.firstface.controller.utills.App
import com.firstfaceme.firstface.controller.utills.AppPreferences
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
import com.firstfaceme.firstface.model.subscription.PojoCheckSubscriptin
import com.firstfaceme.firstface.model.subscription.PojoGetPlanList
import com.firstfaceme.firstface.model.subscription.PostPurchasePlan
import com.firstfaceme.firstface.model.userInfo.PojoUpdateImage
import com.firstfaceme.firstface.model.userInfo.PojoUserDetail
import com.firstfaceme.firstface.model.varifyotpModel.VarifyOtpResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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
            override fun onResponse(
                call: Call<SendOtpResponse>,
                response: Response<SendOtpResponse>
            ) {
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
                    mutableLiveData.value = response.body()
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
            override fun onResponse(
                call: Call<VarifyOtpResponse>,
                response: Response<VarifyOtpResponse>
            ) {
                if (response.isSuccessful) {
                    try {
                        val varifyOtpResponse: VarifyOtpResponse? = response.body()
                        if (response.body()?.statusCode == 200) {
                            AppPreferences.init(App.getAppContext())
                                .putString(
                                    Constants.AUTH_TOKEN,
                                    varifyOtpResponse?.data?.token
                                )

                            AppPreferences.init(App.getAppContext())
                                .putString(
                                    Constants.USER_ID,
                                    varifyOtpResponse?.data?._id
                                )

                            AppPreferences.init(App.getAppContext())
                                .putString(
                                    Constants.MOBILE_NUMBER,
                                    varifyOtpResponse?.data?.mobileNumber
                                )
                            AppPreferences.init(App.getAppContext())
                                .putString(
                                    Constants.COUNTRY_CODE,
                                    varifyOtpResponse?.data?.countryCode
                                )

                        }
                        mutableLiveData.value = varifyOtpResponse
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    mutableLiveData.value = response.body()
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
        mToken: String,
        requestObject: PostRegisterdata
    ): LiveData<RegisterProfileResponse> {
        val mutableLiveData: MutableLiveData<RegisterProfileResponse> = MutableLiveData()
        val requestBody: RequestBody =
            requestObject.toString().toRequestBody("application/json".toMediaType())
        val call = apiInterface.registerProfile("Bearer $mToken", requestObject)
        call.enqueue(object : Callback<RegisterProfileResponse> {
            override fun onResponse(
                call: Call<RegisterProfileResponse>,
                response: Response<RegisterProfileResponse>
            ) {
                if (response.isSuccessful) {
                    try {
                        val registerProfileResponse: RegisterProfileResponse? = response.body()
                        if (response.body()?.statusCode == 200) {

                            AppPreferences.init(App.getAppContext())
                                .putString(
                                    Constants.AUTH_TOKEN,
                                    registerProfileResponse?.data?.token
                                )
                            AppPreferences.init(App.getAppContext())
                                .putString(
                                    Constants.MOBILE_NUMBER,
                                    registerProfileResponse?.data?.mobileNumber
                                )
                            AppPreferences.init(App.getAppContext())
                                .putString(
                                    Constants.COUNTRY_CODE,
                                    registerProfileResponse?.data?.countryCode
                                )
                            AppPreferences.init(App.getAppContext())
                                .putString(Constants.USER_ID, registerProfileResponse?.data?.id)

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


    //update profile
    fun updateProfileImage(
        authToken: String, id: String,
        mImagefile: File?
    ): LiveData<PojoUpdateImage> {
        val mutableLiveData: MutableLiveData<PojoUpdateImage> = MutableLiveData()
        val id: RequestBody = id.toRequestBody("text/plain".toMediaType())

        val part = MultipartBody.Part.createFormData(
            "profile_image", mImagefile?.name, mImagefile!!
                .asRequestBody("image/jpeg".toMediaType())
        )
        val call = apiInterface.updateImage(
            "Bearer $authToken",
            id,
            part
        )
        call!!.enqueue(object : Callback<PojoUpdateImage?> {
            override fun onResponse(
                call: Call<PojoUpdateImage?>,
                response: Response<PojoUpdateImage?>
            ) {
                if (response.isSuccessful) {
                    try {
                        val signupResponse: PojoUpdateImage? = response.body()
                        if (response.body()?.statusCode == 200) {

                            mutableLiveData.value = signupResponse
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<PojoUpdateImage?>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }


    //get User Deatil

    fun getUserDetail(
        mToken: String,
        requestObject: PostGetProfile
    ): LiveData<PojoUserDetail> {
        val mutableLiveData: MutableLiveData<PojoUserDetail> = MutableLiveData()

        val call = apiInterface.userDetail("Bearer $mToken", requestObject)
        call.enqueue(object : Callback<PojoUserDetail> {
            override fun onResponse(
                call: Call<PojoUserDetail>,
                response: Response<PojoUserDetail>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoUserDetail: PojoUserDetail? = response.body()
                        if (response.body()?.statusCode == 200) {

                            mutableLiveData.value = pojoUserDetail


                        } else {
                            mutableLiveData.value = response.body()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<PojoUserDetail>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }
    //get User Deatil

    fun updateSetting(
        mToken: String,
        requestObject: PostUpdateSetting
    ): LiveData<PojoUserDetail> {
        val mutableLiveData: MutableLiveData<PojoUserDetail> = MutableLiveData()

        val call = apiInterface.updateSetting("Bearer $mToken", requestObject)
        call.enqueue(object : Callback<PojoUserDetail> {
            override fun onResponse(
                call: Call<PojoUserDetail>,
                response: Response<PojoUserDetail>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoUserDetail: PojoUserDetail? = response.body()
                        if (response.body()?.statusCode == 200) {

                            mutableLiveData.value = pojoUserDetail


                        } else {
                            mutableLiveData.value = response.body()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<PojoUserDetail>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }


    //get Update Profile  Detail

    fun updateProfileDetail(
        mToken: String,
        requestObject: PostUpdateProfile
    ): LiveData<PojoUserDetail> {
        val mutableLiveData: MutableLiveData<PojoUserDetail> = MutableLiveData()

        val call = apiInterface.updateProfile("Bearer $mToken", requestObject)
        call.enqueue(object : Callback<PojoUserDetail> {
            override fun onResponse(
                call: Call<PojoUserDetail>,
                response: Response<PojoUserDetail>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoUserDetail: PojoUserDetail? = response.body()
                        if (response.body()?.statusCode == 200) {

                            mutableLiveData.value = pojoUserDetail


                        } else {
                            mutableLiveData.value = response.body()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<PojoUserDetail>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

    //update profile
    fun updateImage(
        authToken: String, id: String,
        mImagefile: File?
    ): LiveData<PojoUserDetail> {
        val mutableLiveData: MutableLiveData<PojoUserDetail> = MutableLiveData()
        val id: RequestBody = id.toRequestBody("text/plain".toMediaType())

        val part = MultipartBody.Part.createFormData(
            "profile_image", mImagefile?.name, mImagefile!!
                .asRequestBody("image/jpeg".toMediaType())
        )
        val call = apiInterface.updateImages(
            "Bearer $authToken",
            id,
            part
        )
        call!!.enqueue(object : Callback<PojoUserDetail?> {
            override fun onResponse(
                call: Call<PojoUserDetail?>,
                response: Response<PojoUserDetail?>
            ) {
                if (response.isSuccessful) {
                    try {
                        val signupResponse: PojoUserDetail? = response.body()
                        if (response.body()?.statusCode ==200) {

                            mutableLiveData.value = signupResponse
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    mutableLiveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<PojoUserDetail?>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }


    //delete image
    fun deleteImage(
        mToken: String?,
        imageId: String,
        mUserId: String
    ): LiveData<PojoUserDetail> {
        val mutableLiveData: MutableLiveData<PojoUserDetail> = MutableLiveData()

        val list = mutableListOf<String>()
        list.add(imageId)
        var imageID = PojoDeleteImage(list, mUserId)
        val call =
            apiInterface.deleteImage("Bearer " + mToken, imageID)
        call.enqueue(object : Callback<PojoUserDetail> {
            override fun onResponse(
                call: Call<PojoUserDetail>,
                response: Response<PojoUserDetail>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoNotificationCount: PojoUserDetail? = response.body()
                        mutableLiveData.value = pojoNotificationCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoUserDetail>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }


    //Get Near By  User
    fun nearByUser(
        mToken: String?,
        mUserId: String,
        token: String
    ): LiveData<PojoHome> {
        val mutableLiveData: MutableLiveData<PojoHome> = MutableLiveData()

        val postHome = PostHome(0, 100, mUserId, token)
        val call =
            apiInterface.nearByUser("Bearer " + mToken, postHome)
        call.enqueue(object : Callback<PojoHome> {
            override fun onResponse(
                call: Call<PojoHome>,
                response: Response<PojoHome>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoNotificationCount: PojoHome? = response.body()
                        mutableLiveData.value = pojoNotificationCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoHome>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

    // Add Queue
    fun addQueue(
        mToken: String?,
        mUserId: String,
        OtherUserID: String
    ): LiveData<PojoHome> {
        val mutableLiveData: MutableLiveData<PojoHome> = MutableLiveData()

        val postAddQueue = PostAddQueue(OtherUserID, mUserId)
        val call =
            apiInterface.addQueue("Bearer " + mToken, postAddQueue)
        call.enqueue(object : Callback<PojoHome> {
            override fun onResponse(
                call: Call<PojoHome>,
                response: Response<PojoHome>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoNotificationCount: PojoHome? = response.body()
                        mutableLiveData.value = pojoNotificationCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoHome>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }


    // get token  twilio
    fun getToken(
        mToken: String?,
        mUserId: String,
        queID: String
    ): LiveData<PojoGetToken> {
        val mutableLiveData: MutableLiveData<PojoGetToken> = MutableLiveData()

        val postGetToken = PostGetToken(queID, mUserId)
        val call =
            apiInterface.getTwilioToken("Bearer " + mToken, postGetToken)
        call.enqueue(object : Callback<PojoGetToken> {
            override fun onResponse(
                call: Call<PojoGetToken>,
                response: Response<PojoGetToken>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoGetToken: PojoGetToken? = response.body()
                        mutableLiveData.value = pojoGetToken
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoGetToken>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

    // get token  twilio call
    fun callTwilio(
        mToken: String?,
        mUserId: String,
        otherUserID: String,
        queID: String
    ): LiveData<PojoCall> {
        val mutableLiveData: MutableLiveData<PojoCall> = MutableLiveData()

        val postGetToken = PostCall(otherUserID, queID, mUserId)
        val call =
            apiInterface.call("Bearer " + mToken, postGetToken)
        call.enqueue(object : Callback<PojoCall> {
            override fun onResponse(
                call: Call<PojoCall>,
                response: Response<PojoCall>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoCall: PojoCall? = response.body()
                        mutableLiveData.value = pojoCall
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoCall>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }


// get token  accept call
    fun accpetcall(
        mToken: String?,
        mUserId: String,
        otherUserID: String): LiveData<PojoCallAction> {
        val mutableLiveData: MutableLiveData<PojoCallAction> = MutableLiveData()

        val postGetToken = PostAddQueue(otherUserID, mUserId)
        val call =
            apiInterface.acceptCall("Bearer " + mToken, postGetToken)
        call.enqueue(object : Callback<PojoCallAction> {
            override fun onResponse(
                call: Call<PojoCallAction>,
                response: Response<PojoCallAction>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoCall: PojoCallAction? = response.body()
                        mutableLiveData.value = pojoCall
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoCallAction>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

    // get token  reject call
    fun rejectCall(
        mToken: String?,
        mUserId: String,
        otherUserID: String): LiveData<PojoCallAction> {
        val mutableLiveData: MutableLiveData<PojoCallAction> = MutableLiveData()

        val postGetToken = PostAddQueue(otherUserID, mUserId)
        val call =
            apiInterface.rejectCall("Bearer " + mToken, postGetToken)
        call.enqueue(object : Callback<PojoCallAction> {
            override fun onResponse(
                call: Call<PojoCallAction>,
                response: Response<PojoCallAction>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoCall: PojoCallAction? = response.body()
                        mutableLiveData.value = pojoCall
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoCallAction>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

    // dislike user
    fun dislikeUser(
        mToken: String?,
        mUserId: String,
        OtherUserID: String
    ): LiveData<PojoHome> {
        val mutableLiveData: MutableLiveData<PojoHome> = MutableLiveData()

        val postAddQueue = PojoDislike(OtherUserID, mUserId)
        val call =
            apiInterface.dislikeUser("Bearer " + mToken, postAddQueue)
        call.enqueue(object : Callback<PojoHome> {
            override fun onResponse(
                call: Call<PojoHome>,
                response: Response<PojoHome>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoNotificationCount: PojoHome? = response.body()
                        mutableLiveData.value = pojoNotificationCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoHome>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

    // Get Queue user
    fun getQueue(
        mToken: String?,
        mUserId: String,
    ): LiveData<PojoQueueList> {
        val mutableLiveData: MutableLiveData<PojoQueueList> = MutableLiveData()

        val postAddQueue = PostGetQueue(mUserId)
        val call =
            apiInterface.getQueue("Bearer " + mToken, postAddQueue)
        call.enqueue(object : Callback<PojoQueueList> {
            override fun onResponse(
                call: Call<PojoQueueList>,
                response: Response<PojoQueueList>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoNotificationCount: PojoQueueList? = response.body()
                        mutableLiveData.value = pojoNotificationCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoQueueList>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }


    // Get Plan List
    fun getPlanList(
        mToken: String?,

        ): LiveData<PojoGetPlanList> {
        val mutableLiveData: MutableLiveData<PojoGetPlanList> = MutableLiveData()

        val call =
            apiInterface.getAllPlans("Bearer " + mToken)
        call.enqueue(object : Callback<PojoGetPlanList> {
            override fun onResponse(
                call: Call<PojoGetPlanList>,
                response: Response<PojoGetPlanList>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoNotificationCount: PojoGetPlanList? = response.body()
                        mutableLiveData.value = pojoNotificationCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoGetPlanList>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }


    // purchase Plan
    fun purchasePlan(
        mToken: String?,
        postPurchasePlan: PostPurchasePlan

    ): LiveData<PojoGetPlanList> {
        val mutableLiveData: MutableLiveData<PojoGetPlanList> = MutableLiveData()

        val call =
            apiInterface.purchasePlan("Bearer " + mToken, postPurchasePlan)
        call.enqueue(object : Callback<PojoGetPlanList> {
            override fun onResponse(
                call: Call<PojoGetPlanList>,
                response: Response<PojoGetPlanList>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoNotificationCount: PojoGetPlanList? = response.body()
                        mutableLiveData.value = pojoNotificationCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoGetPlanList>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

    // get Friend list///////////////////
    fun getFriendList(
        mToken: String?,
        postGetFriends: PostGetFriends

    ): LiveData<PojoFriendList> {
        val mutableLiveData: MutableLiveData<PojoFriendList> = MutableLiveData()

        val call =
            apiInterface.getFriendList("Bearer " + mToken, postGetFriends)
        call.enqueue(object : Callback<PojoFriendList> {
            override fun onResponse(
                call: Call<PojoFriendList>,
                response: Response<PojoFriendList>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoFriendList: PojoFriendList? = response.body()
                        mutableLiveData.value = pojoFriendList
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoFriendList>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }


    // block user //////////////////////////
    fun blockUser(
        mToken: String?,
        mUserId: String,
        OtherUserID: String
    ): LiveData<PojoHome> {
        val mutableLiveData: MutableLiveData<PojoHome> = MutableLiveData()

        val postAddQueue = PostAddQueue(OtherUserID, mUserId)
        val call =
            apiInterface.blockUser("Bearer " + mToken, postAddQueue)
        call.enqueue(object : Callback<PojoHome> {
            override fun onResponse(
                call: Call<PojoHome>,
                response: Response<PojoHome>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoNotificationCount: PojoHome? = response.body()
                        mutableLiveData.value = pojoNotificationCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoHome>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }
    // delete user //////////////////////////
    fun deleteUser(
        mToken: String?,
        mUserId: String): LiveData<PojoCallAction> {
        val mutableLiveData: MutableLiveData<PojoCallAction> = MutableLiveData()

        val postDeleteAccount = PostDeleteAccount(mUserId)
        val call =
            apiInterface.deleteAccount("Bearer " + mToken, postDeleteAccount)
        call.enqueue(object : Callback<PojoCallAction> {
            override fun onResponse(
                call: Call<PojoCallAction>,
                response: Response<PojoCallAction>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoNotificationCount: PojoCallAction? = response.body()
                        mutableLiveData.value = pojoNotificationCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoCallAction>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

    // check subscription user //////////////////////////
    fun checkSubscription(
        mToken: String?,
        mUserId: String): LiveData<PojoCheckSubscriptin> {
        val mutableLiveData: MutableLiveData<PojoCheckSubscriptin> = MutableLiveData()

        val postDeleteAccount = PostDeleteAccount(mUserId)
        val call =
            apiInterface.checkSubscription("Bearer " + mToken, postDeleteAccount)
        call.enqueue(object : Callback<PojoCheckSubscriptin> {
            override fun onResponse(
                call: Call<PojoCheckSubscriptin>,
                response: Response<PojoCheckSubscriptin>
            ) {
                if (response.isSuccessful) {
                    try {
                        val pojoNotificationCount: PojoCheckSubscriptin? = response.body()
                        mutableLiveData.value = pojoNotificationCount
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<PojoCheckSubscriptin>, t: Throwable) {
                t.printStackTrace()
            }
        })

        return mutableLiveData
    }

}