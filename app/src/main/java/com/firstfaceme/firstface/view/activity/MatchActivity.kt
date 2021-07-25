package com.firstfaceme.firstface.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.model.Queue.Data
import com.firstfaceme.firstface.view.activity.twilioVoice.VideoActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match.*

class MatchActivity : AppCompatActivity(), View.OnClickListener {
    var mData = Data()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)
        if (intent.extras != null) {
            mData = intent.getParcelableExtra<Data>("QUEUEData")

            Picasso.with(this).load(mData.queId.profileImage).into(ivOtherUser)
            Picasso.with(this).load(AppPreferences.init(this).getString("USERIMAGE")).into(ivMy)
        }

        // set click listener
        btnVideoCall.setOnClickListener(this)
        tvKeepSwiming.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnVideoCall -> {
                v

                val intent = Intent(this, VideoActivity::class.java)
                intent.putExtra("QUEUEID", mData._id)
                intent.putExtra("OTHERID", mData.queId._id)
                startActivity(intent)


            }
            R.id.tvKeepSwiming -> {
                finish()
            }
        }
    }
}