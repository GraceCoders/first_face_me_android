package com.firstfaceme.firstface.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.model.home.Result
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_detail.*
import kotlinx.android.synthetic.main.card_view.view.*

class ProfileDetailActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)
        initView()
    }

    private fun initView() {

        // get Data From Intent 
        if (intent.extras != null) {
            val data = intent.getParcelableExtra<Result>("UserData")

            Picasso.with(this).load(data.profileImage)
                .into(ivUserImage)

            tvName.text = data.firstName + " " + data.lastName + "," + data.age
            tvBio.text = data.bio
            if (data.isOnline) {
                tvOnline.text = getString(R.string.online)

            } else {
                tvOnline.text = getString(R.string.offline)

            }
            tvDistance.text = data.distance
        }
        iv_back.setOnClickListener(this)
        iv_accept.setOnClickListener(this)
        iv_reject.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.iv_accept -> {
                val intent = Intent("PerFromAction")
                intent.putExtra("Action", "1")
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                finish()

            }
            R.id.iv_reject -> {
                val intent = Intent("PerFromAction")
                intent.putExtra("Action", "0")

                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                finish()

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
