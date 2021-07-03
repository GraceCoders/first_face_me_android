package com.firstfaceme.firstface.view.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.view.adapter.HomeVPAdapter
import com.firstfaceme.firstface.view.frgament.HomeFragment
import com.firstfaceme.firstface.view.frgament.MessageFragment
import com.firstfaceme.firstface.view.frgament.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var adapterViewPager: HomeVPAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>setting bottom navigation using view pager>>>>>>.>>>>>>>>>>>>
    private fun initView() {

        val menuView =
            bottom_navigation.getChildAt(0) as BottomNavigationMenuView

        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            val activeLabel =
                item.findViewById<View>(R.id.largeLabel)
            (activeLabel as? TextView)?.setPadding(0, 0, 0, 0)
        }


        val flList: MutableList<FrameLayout> = ArrayList()
        flList.add(fl_home)
        flList.add(fl_message)
        flList.add(fl_profile)

        val fragList: MutableList<Fragment> =
            ArrayList()
        fragList.add(HomeFragment())
        fragList.add(MessageFragment())
        fragList.add(ProfileFragment())


        adapterViewPager = HomeVPAdapter(supportFragmentManager, flList, fragList)
        vp_main.setOffscreenPageLimit(3)
        vp_main.setAdapter(adapterViewPager)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)







    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>bottom navigation menu>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val menuNav: Menu = bottom_navigation.getMenu()
            val menuItem = menuNav.findItem(R.id.nav_home)
            when (item.itemId) {
                R.id.nav_home -> {
                    vp_main.setCurrentItem(0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_message -> {
                    vp_main.setCurrentItem(1)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile-> {
                    vp_main.setCurrentItem(2)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    //................................hide keyboard on touch.................................................
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view = currentFocus
        val ret = super.dispatchTouchEvent(ev)
        if (view is EditText) {
            val w = currentFocus
            val scrcoords = IntArray(2)
            w!!.getLocationOnScreen(scrcoords)
            val x = ev.rawX + w.left - scrcoords[0]
            val y = ev.rawY + w.top - scrcoords[1]
            if (ev.action == MotionEvent.ACTION_UP
                && (x < w.left || x >= w.right || y < w.top || y > w.bottom)
            ) {
                hideSoftKeyboard(this)
            }
        }
        return ret
    }
}
