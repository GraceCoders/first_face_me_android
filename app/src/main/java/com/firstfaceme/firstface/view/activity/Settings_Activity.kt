package com.firstfaceme.firstface.view.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.App
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.view.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_settings_.*
import kotlin.math.max

class Settings_Activity : AppCompatActivity(), View.OnClickListener {
    private var viewModel: ProfileViewModel? = null
    private  var auth_token = ""
private  var maxAge: Int=30
private  var minAge: Int=18
private  var maxDistance: Int=0

    private  var userProfile = ""


    private var mUserId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_)
        initView()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        auth_token = AppPreferences.init(this).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(this).getString(Constants.USER_ID)

        // on click listener
        iv_back.setOnClickListener(this)
        ll_premium.setOnClickListener(this)
        llLogout.setOnClickListener(this)
        liDeleteAccount.setOnClickListener(this)

        // get profile
        getProfileData()

        seekbarListener()
    }
     //////////// seek bar change listener /////////////////////////////////////////////////////

    private  fun  seekbarListener()
    {seekbar_age.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener
    {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            maxAge=progress
            tvAge.text=progress.toString()+"KM"

        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            viewModel?.updateSetting(auth_token,mUserId,maxDistance,minAge,maxAge)

        }

    })
        seekbar_distance.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener
    {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            maxDistance=progress
            tvDistance.text=progress.toString()+"KM"
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            viewModel?.updateSetting(auth_token,mUserId,maxDistance,minAge,maxAge )

        }

    })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.llLogout -> {


                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.logout))
                    .setMessage(getString(R.string.are_ypu_sire_logout))
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            viewModel?.deleteUser(auth_token, mUserId)
                            AppPreferences.init(App.getAppContext())
                                .putBoolean(
                                    Constants.IS_LOGIN, false
                                )

                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }).setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.ll_premium -> {
                val intent = Intent(this, GoPremiumActivity::class.java)
                startActivity(intent)
            }
            R.id.liDeleteAccount -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete_account))
                    .setMessage(getString(R.string.deletee_account_Are_you))
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            viewModel?.deleteUser(auth_token, mUserId)
                            AppPreferences.init(App.getAppContext())
                                .putBoolean(
                                    Constants.IS_LOGIN, false
                                )

                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }).setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    // ....................................get  Profile Data ............................................

    fun getProfileData() {
        clLoaderSetting.visibility = View.VISIBLE
        viewModel!!.getProfile(
            auth_token,
            mUserId
        )
            .observe(
                this,
                { pojoUserProfileData ->
                    clLoaderSetting.visibility = View.GONE

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {
                        tvPhone.text = pojoUserProfileData.data.mobileNumber
                        tvAge.text = pojoUserProfileData.data.age.toString()

                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(this, message)
    }

}
