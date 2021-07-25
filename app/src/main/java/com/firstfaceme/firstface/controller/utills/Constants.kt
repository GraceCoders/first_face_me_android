package com.firstfaceme.firstface.controller.utills

/**
 * Created by Md Efteaz on 03/07/20.
 */
object Constants {
    const val domain:String="69.18.218.102"
    const val BASE_URL_API = "http://193.46.198.135:5004/api/v1/"

    //api endpoint
    const val API_LOGIN = "user/login"
    const val SEND_OTP = "user/otp"
    const val VARIFY_OTP = "user/verifyotp"
    const val REGISTER = "user/register"
    const val USER_DETAIL = "user/detail"
    const val API_UPDATE_PROFILE_IMAGE = "user/updateprofileimage"
    const val API_UPDATE_IMAGE = "user/updateimages"
    const val API_UPDATE_PROFILE = "user/updateprofile"
    const val API_DELETE_IMAGE = "user/delete/image"
    const val API_HOME_DATA = "user/nearbyyou"
    const val API_BLOCK_USER = "user/block"
    const val API_SETTING = "user/setting"
    const val API_DELETE_ACCOUNT = "user/deleteuser"


    const val API_ADD_QUEUE = "que/add"
    const val API_GET_TWILIO_TOKEN = "que/twillio/token"
    const val API_GET_CALL= "que/twillio/call"
    const val API_GET_ACCEPT_CALL= "que/twillio/call/accept"
    const val API_GET_REJECT_CALL= "que/twillio/call/reject"


    const val API_DISLIKE = "friend/dislike"
    const val API_GET_QUEUE = "que/all "
    const val API_GET_PLAN = "subscription/all/plans"
    const val API_PURCHASE_PLAN = "subscription/purchase/plan"
    const val API_FRIEND_LIST = "friend/list"

    //preferences
    const val AUTH_TOKEN = "token22"
    const val MOBILE_NUMBER = "mobile"
    const val COUNTRY_CODE = "country_code"
    const val IS_LOGIN = "is_login"

    const val USERNAME = "username"
    const val EMAIL = "useremail"
    const val USER_ID = "user_id"
    const val PROFILE_IMAGE = "profile_image"

    const val PASSWORD = "password"
}