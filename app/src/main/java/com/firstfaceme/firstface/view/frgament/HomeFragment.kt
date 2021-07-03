package com.firstfaceme.firstface.view.frgament

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.view.activity.ProfileDetailActivity
import com.firstfaceme.firstface.view.activity.Settings_Activity
import com.firstfaceme.firstface.view.adapter.Card_Adapter
import com.firstfaceme.firstface.view.adapter.HomeVPAdapter
import com.firstfaceme.firstface.view.adapter.Message_Adapter
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_message.view.*


class HomeFragment : Fragment(), CardStackListener,View.OnClickListener {
    internal var rootView: View? = null
    private var adapterViewPager: Card_Adapter? = null
    private lateinit var layoutManager: CardStackLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        initView()
        return rootView;
    }

    private fun initView() {
        rootView?.iv_accept?.setOnClickListener(this)
        rootView?.iv_reject?.setOnClickListener(this)

        layoutManager = CardStackLayoutManager(context, this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())


        }

        rootView?.stack_view?.layoutManager = layoutManager
        rootView?.stack_view?.adapter = Card_Adapter(activity)
        (rootView?.stack_view?.adapter as Card_Adapter).onItemClick = { pos, view ->

            val intent = Intent(activity, ProfileDetailActivity::class.java)
            startActivity(intent)
        }

        rootView?.stack_view?.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        Toast.makeText(activity, ""+direction, Toast.LENGTH_SHORT).show()
    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardRewound() {

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
             R.id.iv_accept ->
             {

                 val setting = SwipeAnimationSetting.Builder()
                     .setDirection(Direction.Right)
                     .setDuration(Duration.Normal.duration)
                     .setInterpolator(AccelerateInterpolator())
                     .build()

                 layoutManager.setSwipeAnimationSetting(setting)
                 rootView?.stack_view?.swipe()



             }
            R.id.iv_reject ->
            {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()

                layoutManager.setSwipeAnimationSetting(setting)
                rootView?.stack_view?.swipe()

            }
            }
        }

}




