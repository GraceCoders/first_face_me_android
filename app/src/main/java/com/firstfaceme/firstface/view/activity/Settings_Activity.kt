package com.firstfaceme.firstface.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.firstfaceme.firstface.R
import kotlinx.android.synthetic.main.activity_settings_.*

class Settings_Activity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_)
        initView()
    }

    private fun initView() {
        iv_back.setOnClickListener(this)
        ll_premium.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.iv_back ->
            {
                onBackPressed()
            }
            R.id.ll_premium ->
            {
                val intent = Intent(this, GoPremiumActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
