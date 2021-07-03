package com.firstfaceme.firstface.controller.utills

import android.app.Activity
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.CustomViews.MyEditTextBoldRegular
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern


object Validations {

    /**
     * @param editText - EditText field which need to be validated
     * @return - Returns true if editText is Null or empty
     */
    fun isNullOrEmpty(editText: EditText): Boolean {
        return (editText.text == null
                || editText.text.toString().trim { it <= ' ' }.length == 0)
    }
    private fun validateEmailAddress(
        applicationContext: Activity,
        view: View,
        errMessage: String
    ): Boolean {
        val email = (view as EditText).text.toString().trim { it <= ' ' }
        if (email.contains("[a-zA-Z0-9._-]+") || email.contains("@")) {
            if (email.isEmpty() || !isValidEmail(email)) {



                SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage)
                // requestFocus(applicationContext, ((EditText) view));
                return true
            }
        } else {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage)
            // requestFocus(applicationContext, ((EditText) view));
            return true
        }
        return false
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isValidMobile(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }
    fun isValidMobileKeyworker(
        applicationContext: Activity?,
        etPhoneNumber: TextInputEditText?,
        phone: String
    ): Boolean {
        return if (!Pattern.matches("[a-zA-Z]+", phone)) {
            phone.length > 9 && phone.length <= 12
        } else false
    }
    private fun validateUsername(
        applicationContext: Activity,
        mEtUsername: EditText,
        errMessage: String
    ): Boolean {
        if (isNullOrEmpty(mEtUsername)) {
            SnackbarUtil.showWarningShortSnackbar(applicationContext, errMessage)
            mEtUsername.isFocusable = true
            mEtUsername.requestFocus()
            return true
        }
        return false
    }

        fun isValidateSendOtp(applicationContext: Activity, mEtMobileNumber: EditText?): Boolean {
            if (isValidMobileKeyworker(
                    applicationContext,
                    (mEtMobileNumber as TextInputEditText?)!!,
                    applicationContext.resources.getString(R.string.err_msg_number)
                )
            ) {
                return false
        }
        return true
    }


    fun isRegisterProfile(
        applicationContext: Activity,
        mEtUsername: EditText?,
        mEtLastname: EditText?,
        mEtBio: EditText?,
        mEtJob: EditText?
    ): Boolean {
        if (validateUsername(applicationContext, mEtUsername!!, applicationContext.resources.getString(R.string.err_first_name))) {
            return false
        }
        if (validateUsername(applicationContext, mEtLastname!!, applicationContext.resources.getString(R.string.err_last_name))) {
            return false
        }
        if (validateUsername(applicationContext, mEtBio!!, applicationContext.resources.getString(R.string.err_bio))) {
            return false
        }
        if (validateUsername(applicationContext, mEtJob!!, applicationContext.resources.getString(R.string.err_job))) {
            return false
        }
        return true
    }
//
//    fun isValidateSignup(applicationContext: Activity,mEtUsername: EditText?,mEtLastName: EditText?,  mEtEmail: EditText?, mEtPassword: EditText?): Boolean {
//        if (validateUsername(applicationContext, mEtUsername!!, applicationContext.resources.getString(R.string.error_empty_first_name))) {
//            return false
//        }
//        if (validateUsername(applicationContext, mEtLastName!!, applicationContext.resources.getString(R.string.error_empty_last_name))) {
//            return false
//        }
//        if (validateEmailAddress(applicationContext, mEtEmail!!, applicationContext.resources.getString(R.string.error_empty_user_id))) {
//            return false
//        }
//        if (validateUsername(applicationContext, mEtPassword!!, applicationContext.resources.getString(R.string.error_empty_password))) {
//            return false
//        }
//        return true
//    }
}