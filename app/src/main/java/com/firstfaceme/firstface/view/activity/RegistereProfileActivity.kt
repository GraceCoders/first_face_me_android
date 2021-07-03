package com.firstfaceme.firstface.view.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.controller.utills.Validations
import com.firstfaceme.firstface.view.viewmodel.RegisterViewModel
import com.firstfaceme.firstface.view.viewmodel.VarifyOtpViewModel
import kotlinx.android.synthetic.main.activity_registere_profile.*
import kotlinx.android.synthetic.main.activity_registere_profile.view.*

class RegistereProfileActivity : AppCompatActivity(),View.OnClickListener{
    var genderSelected:String?=""
    var userGender:String?=""
    private  var viewModel: RegisterViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registere_profile)
        initView()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        val genderSp = resources.getStringArray(R.array.Gender)
        ll_save.setOnClickListener(this)
        //.........................................interested in  spinner..................................................//

        if (sp_gender != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, genderSp
            )
            sp_gender.adapter = adapter
            sp_gender.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    (view as TextView).setTextColor(Color.BLACK)
                    if (position == 0) {
                        genderSelected=""
                    } else {
                        genderSelected=genderSp[position]
                        Log.e("gender",genderSelected+"")
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
   //>..................................................gender................................................

        if (sp_user_gender != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, genderSp
            )
            sp_user_gender.adapter = adapter
            sp_user_gender.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    (view as TextView).setTextColor(Color.BLACK)
                    if (position == 0) {
                        userGender=""
                    } else {
                        userGender=genderSp[position]
                        Log.e("gender",userGender+"")
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.ll_save ->
            {
                if(Validations.isRegisterProfile(this,et_first_name,et_last_name,et_bio,et_job))
                {
                    if(genderSelected!!.isNotEmpty()&&userGender!!.isNotEmpty())
                    {
                        doRegister()
                    }
                    else
                    {
                        showSnackBar("Please Select Gender")
                    }

                }
            }
        }

    }

    private fun doRegister() {


    }


    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(this,message)
    }

}
