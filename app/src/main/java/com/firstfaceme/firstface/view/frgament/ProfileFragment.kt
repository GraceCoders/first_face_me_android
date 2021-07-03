package com.firstfaceme.firstface.view.frgament

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.view.activity.EditProfileActivity
import com.firstfaceme.firstface.view.activity.QueauActivity
import com.firstfaceme.firstface.view.activity.Settings_Activity
import com.firstfaceme.firstface.view.adapter.Profile_Adapter
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment(),View.OnClickListener{
    internal var rootView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        initView()
        return rootView;
    }

    private fun initView() {
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>Profile picture adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        rootView?.iv_settings?.setOnClickListener(this)
        rootView?.ll_edit_profile?.setOnClickListener(this)
        rootView?.ll_queau?.setOnClickListener(this)
        rootView?.rv_profile_pic?.layoutManager= GridLayoutManager(context,2)
        rootView?.rv_profile_pic?.adapter= Profile_Adapter(activity)
        (rootView?.rv_profile_pic?.adapter as Profile_Adapter).onItemClick = { pos, view ->


            //            val intent = Intent(context, JobDetailActivity::class.java)
//            activity?.startActivity(intent)


        }
        rootView?.rv_profile_pic?.setNestedScrollingEnabled(false)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.iv_settings ->
            {
                val intent = Intent(activity, Settings_Activity::class.java)
                startActivity(intent)
            }
            R.id.ll_edit_profile ->
            {
                val intent = Intent(activity, EditProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.ll_queau ->
            {
                val intent = Intent(activity, QueauActivity::class.java)
                startActivity(intent)
            }
        }
    }
}