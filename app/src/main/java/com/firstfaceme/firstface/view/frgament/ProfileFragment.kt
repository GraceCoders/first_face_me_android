package com.firstfaceme.firstface.view.frgament

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.App
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.view.activity.EditProfileActivity
import com.firstfaceme.firstface.view.activity.QueauActivity
import com.firstfaceme.firstface.view.activity.Settings_Activity
import com.firstfaceme.firstface.view.adapter.Profile_Adapter
import com.firstfaceme.firstface.view.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment(), View.OnClickListener {
    private var viewModel: ProfileViewModel? = null
    var auth_token = ""

    private var mUserId = ""
    internal var rootView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        initView()
        return rootView
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        auth_token = AppPreferences.init(requireContext()).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(requireContext()).getString(Constants.USER_ID)

        getProfileData()
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>Profile picture adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        rootView?.iv_settings?.setOnClickListener(this)
        rootView?.ll_edit_profile?.setOnClickListener(this)
        rootView?.ll_queau?.setOnClickListener(this)
        rootView?.rv_profile_pic?.layoutManager = GridLayoutManager(context, 2)

        rootView?.rv_profile_pic?.isNestedScrollingEnabled = false


        //Initialize local broadcast
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(
                mUpdateProfileBroadcastReceiver,
                IntentFilter("REFERESH_FRAG")
            )
    }


    private val mUpdateProfileBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, p1: Intent?) {
            //Call api
            getProfileData()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_settings -> {
                val intent = Intent(activity, Settings_Activity::class.java)
                startActivity(intent)
            }
            R.id.ll_edit_profile -> {
                val intent = Intent(activity, EditProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.ll_queau -> {
                val intent = Intent(activity, QueauActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        getProfileData()
    }
    //.....................................get  Profile Data ............................................

    fun getProfileData() {
        if (isAdded)
//        progressBar.visibility=View.VISIBLE
            viewModel!!.getProfile(
                auth_token,
                mUserId
            )
                .observe(
                    this,
                    { pojoUserProfileData ->
//                    progressBar.visibility=View.GONE

                        val statusCode = pojoUserProfileData?.statusCode
                        if (statusCode == 200) {
                            tvBio.text = pojoUserProfileData.data.bio
                            tvJob.text = pojoUserProfileData.data.job
                            tvQueCount.text = pojoUserProfileData.data.queCount.toString()

                            AppPreferences.init(App.getAppContext())
                                .putString(Constants.USER_ID, pojoUserProfileData.data._id)
                            tvNameAge.text =
                                pojoUserProfileData.data.firstName + " " + pojoUserProfileData.data.lastName + ", " + pojoUserProfileData.data.age
                            AppPreferences.init(requireContext())
                                .putString("USERIMAGE", pojoUserProfileData.data.profileImage)
                            Glide.with(requireContext()).load(pojoUserProfileData.data.profileImage)
                                .into(roundedImageView)
                            var imagesList = mutableListOf<String>()
                            imagesList.clear()
                            if (pojoUserProfileData.data.images != null && pojoUserProfileData.data.images.isNotEmpty()) {
                                imagesList.addAll(pojoUserProfileData.data.images)
                                rootView?.rv_profile_pic?.adapter =
                                    Profile_Adapter(activity, imagesList)
                                (rootView?.rv_profile_pic?.adapter as Profile_Adapter).onItemClick =
                                    { pos, view ->


                                    }
                            }

                        } else {
                            showSnackBar(pojoUserProfileData!!.message)

                        }
                    })
    }

    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(requireActivity(), message)
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        getProfileData()
    }
}