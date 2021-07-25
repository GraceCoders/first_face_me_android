package com.firstfaceme.firstface.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.view.adapter.Quaue_Adapter
import com.firstfaceme.firstface.view.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_queau.*

class QueauActivity : AppCompatActivity(), View.OnClickListener {
    private var mUserId = ""
    private var viewModel: HomeViewModel? = null
    var auth_token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queau)
        initView()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        auth_token = AppPreferences.init(this).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(this).getString(Constants.USER_ID)

        getQueueUser()


        iv_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    //.....................................get  Queue User list  ............................................

    fun getQueueUser() {
        pbQueue.visibility = View.VISIBLE
        viewModel!!.getQueueUser(
            auth_token,
            mUserId
        )
            .observe(
                this,
                { pojoUserProfileData ->
                    pbQueue.visibility = View.GONE

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {
                        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>Queaue adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                        if (pojoUserProfileData.data.isEmpty()) {
                            tvNoData.visibility = View.VISIBLE
                        } else {
                            tvNoData.visibility = View.GONE

                        }
                        rv_queaue?.layoutManager =
                            GridLayoutManager(this, 2)
                        rv_queaue?.adapter = Quaue_Adapter(this, pojoUserProfileData.data)
//        rv_queaue?.adapter as Categories_Adapter).onItemClick = { pos, view ->
//
//
//            //            val intent = Intent(context, JobDetailActivity::class.java)
////            activity?.startActivity(intent)
//
//
//        }
//        rv_queaue?.setNestedScrollingEnabled(false)


                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(this, message)
    }
}
