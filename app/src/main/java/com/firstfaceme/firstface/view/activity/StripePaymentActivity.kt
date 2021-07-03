package com.firstfaceme.firstface.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.view.adapter.Plans_Adapter
import kotlinx.android.synthetic.main.activity_go_premium.*
import kotlinx.android.synthetic.main.activity_go_premium.iv_back
import kotlinx.android.synthetic.main.activity_stripe_payment.*

class StripePaymentActivity : AppCompatActivity(), View.OnClickListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stripe_payment)
        initView()
    }
    private fun initView() {
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>Categories adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        iv_back.setOnClickListener(this)
        ll_confirm_payment.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.iv_back ->
            {
                onBackPressed()
            }
            R.id.ll_confirm_payment ->
            {
                Toast.makeText(this, "Under development", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
