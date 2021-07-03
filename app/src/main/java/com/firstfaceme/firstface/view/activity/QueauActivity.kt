package com.firstfaceme.firstface.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.view.adapter.Quaue_Adapter
import kotlinx.android.synthetic.main.activity_queau.*

class QueauActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queau)
        initView()
    }
    private fun initView() {
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>Queaue adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        iv_back.setOnClickListener(this)
        rv_queaue?.layoutManager= GridLayoutManager(this,2) as RecyclerView.LayoutManager?
        rv_queaue?.adapter= Quaue_Adapter(this)
//        rv_queaue?.adapter as Categories_Adapter).onItemClick = { pos, view ->
//
//
//            //            val intent = Intent(context, JobDetailActivity::class.java)
////            activity?.startActivity(intent)
//
//
//        }
//        rv_queaue?.setNestedScrollingEnabled(false)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.iv_back ->
            {
                onBackPressed()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
