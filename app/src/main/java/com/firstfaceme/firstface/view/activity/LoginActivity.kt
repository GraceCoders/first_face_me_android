package com.firstfaceme.firstface.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.App
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        ll_login.setOnClickListener(this)
        if (AppPreferences.init(App.getAppContext())
                .getBoolean(Constants.IS_LOGIN)
        ) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_login -> {

                val intent = Intent(this, MobileNumberActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
