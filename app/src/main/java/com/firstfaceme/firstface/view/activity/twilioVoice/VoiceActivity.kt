package com.firstfaceme.firstface.view.activity.twilioVoice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.view.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_voice.*

class VoiceActivity : AppCompatActivity(), View.OnClickListener {
    private var mUserId = ""
    private var mOtherUserId = ""
    private var viewModel: HomeViewModel? = null
    var auth_token = ""
    private var voiceBroadcastReceiver: VoiceBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        auth_token = AppPreferences.init(this).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(this).getString(Constants.USER_ID)

        voiceBroadcastReceiver = VoiceBroadcastReceiver()
        SoundPoolManager.getInstance(this).playRinging()


        // set click listener
        declineButton.setOnClickListener(this)
        answerButton.setOnClickListener(this)

    }


    private inner class VoiceBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {
            val action = intent.action
            if (action != null && (action == Constantss.ACTION_INCOMING_CALL || action == Constantss.ACTION_CANCEL_CALL)) {
                /*
                 * Handle the incoming or cancelled call invite
                 */
                handleIncomingCallIntent(intent)
            }
        }
    }

    private fun handleIncomingCallIntent(intent: Intent?) {
        if (intent != null && intent.action != null) {
            try {
                val action = intent.action
                val activeCallInvite = intent.getStringExtra(Constantss.INCOMING_CALL_INVITE)

                when (action) {
                    Constantss.ACTION_INCOMING_CALL -> handleIncomingCall()
                    Constantss.ACTION_INCOMING_CALL_NOTIFICATION -> showIncomingCallDialog()
                    Constantss.ACTION_CANCEL_CALL -> handleCancel()

                    else -> {
                    }
                }
            } catch (e: Exception) {

            }


        }
    }


    private fun showIncomingCallDialog() {
        SoundPoolManager.getInstance(this).playRinging()

    }

    private fun handleIncomingCall() {

        showIncomingCallDialog()


    }

    private fun handleCancel() {
        SoundPoolManager.getInstance(this).stopRinging()

    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constantss.ACTION_INCOMING_CALL)
        intentFilter.addAction(Constantss.ACTION_CANCEL_CALL)
        intentFilter.addAction(Constantss.ACTION_FCM_TOKEN)
        LocalBroadcastManager.getInstance(this).registerReceiver(
            voiceBroadcastReceiver!!, intentFilter
        )
    }


    private fun unregisterReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(voiceBroadcastReceiver!!)

    }

    override fun onResume() {
        super.onResume()
        registerReceiver()

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver()
        handleCancel()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.declineButton -> {
                rejectCall()
            }
            R.id.answerButton -> {
                acceptCall()
            }
        }
    }


    //.....................................get  Queue User list  ............................................

    fun acceptCall() {
        viewModel!!.acceptCall(
            auth_token,
            mUserId, mOtherUserId
        )
            .observe(
                this,
                { pojoUserProfileData ->

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {


                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    fun rejectCall() {
        viewModel!!.rejectCall(
            auth_token,
            mUserId, mOtherUserId
        )
            .observe(
                this,
                { pojoUserProfileData ->

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {


                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(this, message)
    }
}