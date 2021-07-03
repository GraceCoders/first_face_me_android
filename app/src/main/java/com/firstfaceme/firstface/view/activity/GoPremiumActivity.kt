package com.firstfaceme.firstface.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.view.adapter.Plans_Adapter
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_go_premium.*
import kotlinx.android.synthetic.main.activity_go_premium.iv_back

class GoPremiumActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_premium)
        initView()
    }

    private fun initView() {
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>Categories adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        iv_back.setOnClickListener(this)
        ll_subscribe.setOnClickListener(this)
        rv_pack?.layoutManager= LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL ,false)
        rv_pack?.adapter= Plans_Adapter(this)
//        rv_pack?.adapter as Plans_Adapter). = { pos, view ->
//
//
//            //            val intent = Intent(context, JobDetailActivity::class.java)
////            activity?.startActivity(intent)
//
//
//        }
        rv_pack?.setNestedScrollingEnabled(false)
    }


    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.iv_back ->
            {
                onBackPressed()
            }
            R.id.ll_subscribe ->
            {
                val intent = Intent(this, StripePaymentActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
