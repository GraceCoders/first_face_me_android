package com.firstfaceme.firstface.view.frgament

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.view.adapter.Message_Adapter
import kotlinx.android.synthetic.main.fragment_message.view.*

class MessageFragment : Fragment() {
    internal var rootView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_message, container, false)
        initView()
        return rootView;
    }

    private fun initView() {
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>locations adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        rootView?.rv_message?.layoutManager = LinearLayoutManager(context)
        rootView?.rv_message?.adapter = Message_Adapter(activity)
        (rootView?.rv_message?.adapter as Message_Adapter).onItemClick = { pos, view ->
        }
        rootView?.rv_message?.setNestedScrollingEnabled(false)

    }
}