package com.firstfaceme.firstface.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.controller.utills.Validations
import com.firstfaceme.firstface.model.sendOtpModel.SendOtpResponse
import com.firstfaceme.firstface.view.viewmodel.SendOtpViewModel
import com.rilixtech.widget.countrycodepicker.CountryCodePicker
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_mobile_number.*


class MobileNumberActivity : AppCompatActivity(),View.OnClickListener {

    private  var viewModel: SendOtpViewModel?=null
    var statusCode:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_number)
        initView()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(SendOtpViewModel::class.java)
        ll_send_otp.setOnClickListener(this)
//        ccp.registerPhoneNumberTextView(et_number)
    }

override fun onClick(v: View?) {
    when(v?.id)
    {
        R.id.ll_send_otp ->
        {
            if(et_number.text!!.isNotEmpty())
            {
                doLogin()
            }
            else
            {
                showSnackBar("Please Enter Your Valid Mobile Number")
            }

        }
    }
}

    private fun doLogin() {

        clLoader.visibility = View.VISIBLE
        viewModel?.sendOtp(et_number.text.toString(),ccp.selectedCountryCodeWithPlus)?.observe(
            this,
            object : Observer<SendOtpResponse> {
                override fun onChanged(@Nullable loginResponse: SendOtpResponse?) {
                   clLoader.visibility = View.GONE
                    statusCode=loginResponse?.statusCode

                    if(statusCode==200)
                    {

                        val intent = Intent(applicationContext, OtpActivity::class.java)
                        intent.putExtra("mobileNumber",et_number.text.toString())
                        intent.putExtra("countryCode",ccp.selectedCountryCodeWithPlus)
                        startActivity(intent)
                    }
                    else
                    {
                        showSnackBar(loginResponse?.message)
                    }

                }
            })

    }


    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(this,message)
    }


    override fun onBackPressed() {
    super.onBackPressed()
    finish()
}

    //................................hide keyboard on touch.................................................
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        val ret = super.dispatchTouchEvent(ev)
        if (view is EditText) {
            val w = currentFocus
            val scrcoords = IntArray(2)
            w!!.getLocationOnScreen(scrcoords)
            val x = ev.rawX + w.left - scrcoords[0]
            val y = ev.rawY + w.top - scrcoords[1]
            if (ev.action == MotionEvent.ACTION_UP
                && (x < w.left || x >= w.right || y < w.top || y > w.bottom)
            ) {
                hideSoftKeyboard(this)
            }
        }
        return ret
    }
}
