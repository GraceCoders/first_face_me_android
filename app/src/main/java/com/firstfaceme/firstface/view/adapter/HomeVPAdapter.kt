package com.firstfaceme.firstface.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class HomeVPAdapter(
    private val manager: FragmentManager,
    var frameLayouts: List<FrameLayout>,
    var fragList: List<Fragment>
) :
    PagerAdapter() {
    override fun getCount(): Int {
        return frameLayouts.size
    }

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view === `object`
    }

    private fun loadFragment(
        fragment: Fragment?,
        containerId: Int
    ) {
        if (fragment != null) {
            manager.beginTransaction().replace(containerId, fragment).commit()
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val vp = container as ViewPager
        loadFragment(fragList[position], frameLayouts[position].id)
        vp.addView(frameLayouts[position])
        return frameLayouts[position]
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        val vp = container as ViewPager
        val view = `object` as RecyclerView
        vp.removeView(view)
    }

}