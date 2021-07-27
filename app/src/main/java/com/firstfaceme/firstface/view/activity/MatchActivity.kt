package com.firstfaceme.firstface.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.model.Queue.Data
import com.firstfaceme.firstface.view.activity.twilioVoice.VideoActivity
import com.firstfaceme.firstface.view.viewmodel.SubscriptionViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match.*
import kotlinx.android.synthetic.main.activity_stripe_payment.*

class MatchActivity : AppCompatActivity(), View.OnClickListener {
    var mData = Data()
    private var viewModel: SubscriptionViewModel? = null
    var auth_token = ""
    private var mUserId = ""
    private  var mISSubscription=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)


        viewModel = ViewModelProviders.of(this).get(SubscriptionViewModel::class.java)

        auth_token = AppPreferences.init(this).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(this).getString(Constants.USER_ID)

        checkSubscription(auth_token,mUserId)

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

if(!mISSubscription)
{showSnackBar("First purchase subscription")

}else {
    val intent = Intent(this, VideoActivity::class.java)
    intent.putExtra("QUEUEID", mData._id)
    intent.putExtra("OTHERID", mData.queId._id)
    startActivity(intent)
}

            }
            R.id.tvKeepSwiming -> {
                finish()
            }
        }
    }

    //.....................................check  Subscription Plan ............................................

    fun checkSubscription(
        auth_token: String,

        mUserId: String
    ) {
        viewModel!!.checkSubscription(
            auth_token, mUserId
        )

            .observe(
                this,
                { pojoSubscriptionList ->

                    val statusCode = pojoSubscriptionList?.statusCode
                    if (statusCode == 200) {


                        mISSubscription = pojoSubscriptionList.message != "First purchase subscription"




                    } else {
                        showSnackBar(pojoSubscriptionList!!.message)

                    }
                })
    }



    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(this, message)
    }

}