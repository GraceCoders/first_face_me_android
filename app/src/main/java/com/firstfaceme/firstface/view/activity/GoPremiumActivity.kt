package com.firstfaceme.firstface.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.model.subscription.Data
import com.firstfaceme.firstface.view.adapter.Plans_Adapter
import com.firstfaceme.firstface.view.viewmodel.SubscriptionViewModel
import kotlinx.android.synthetic.main.activity_go_premium.*

class GoPremiumActivity : AppCompatActivity(), View.OnClickListener {
    private var viewModel: SubscriptionViewModel? = null
    var auth_token = ""
    private var mUserId = ""
    private var dataprice = ""
    private var mData = Data()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_premium)
        initView()
    }

    private fun initView() {

        viewModel = ViewModelProviders.of(this).get(SubscriptionViewModel::class.java)

        auth_token = AppPreferences.init(this).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(this).getString(Constants.USER_ID)

        // Hit api
        getSubscriptionPlanList()

        // Set click listener
        iv_back.setOnClickListener(this)
        ll_subscribe.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.ll_subscribe -> {
                if (dataprice == "") {
                    showSnackBar(getString(R.string.select_plan))
                } else {
                    val intent = Intent(this, StripePaymentActivity::class.java)
                    intent.putExtra("Data", mData)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    //.....................................get Subscription Plan ............................................

    fun getSubscriptionPlanList() {
        rlSubscription.visibility = View.VISIBLE
        viewModel!!.getPlans(
            auth_token,
            mUserId
        )
            .observe(
                this,
                { pojoSubscriptionList ->
                    rlSubscription.visibility = View.GONE

                    val statusCode = pojoSubscriptionList?.statusCode
                    if (statusCode == 200) {

                        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>Categories adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                        rv_pack?.layoutManager = LinearLayoutManager(
                            this,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                        rv_pack?.adapter = Plans_Adapter(this, pojoSubscriptionList.data)
                        (rv_pack?.adapter as Plans_Adapter).onItemClick = { pos, view, data ->

                            dataprice = data.price.toString()

                            mData = data

                        }
                        rv_pack?.isNestedScrollingEnabled = false


                    } else {
                        showSnackBar(pojoSubscriptionList!!.message)

                    }
                })
    }


    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(this, message)
    }
}
