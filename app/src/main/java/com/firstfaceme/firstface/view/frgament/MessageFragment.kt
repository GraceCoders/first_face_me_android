package com.firstfaceme.firstface.view.frgament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.view.adapter.Message_Adapter
import com.firstfaceme.firstface.view.viewmodel.FriendsViewModel
import kotlinx.android.synthetic.main.fragment_message.*
import kotlinx.android.synthetic.main.fragment_message.view.*

class MessageFragment : Fragment() {
    private var viewModel: FriendsViewModel? = null
    var auth_token = ""

    private var mUserId = ""
    internal var rootView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_message, container, false)
        initView()
        return rootView
    }

    private fun initView() {

        viewModel = ViewModelProviders.of(this).get(FriendsViewModel::class.java)

        auth_token = AppPreferences.init(requireContext()).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(requireContext()).getString(Constants.USER_ID)


        // get friend list
        getFriendsList()
    }


    //.....................................get  friend List Data ............................................

    fun getFriendsList() {
        // pbMessage.visibility=View.VISIBLE
        viewModel!!.getFriendsList(
            auth_token,
            mUserId
        )
            .observe(
                this,
                { pojoUserProfileData ->
                    //     pbMessage.visibility=View.GONE

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {

                        if (pojoUserProfileData.data.isEmpty()) {
                            tvNoFound.visibility = View.VISIBLE

                        } else {
                            tvNoFound.visibility = View.GONE
                        }
                        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>locations adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                        rootView?.rv_message?.layoutManager = LinearLayoutManager(context)
                        rootView?.rv_message?.adapter =
                            Message_Adapter(activity, pojoUserProfileData.data)
                        (rootView?.rv_message?.adapter as Message_Adapter).onItemClick =
                            { pos, view ->
                            }
                        rootView?.rv_message?.isNestedScrollingEnabled = false

                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(requireActivity(), message)
    }
}