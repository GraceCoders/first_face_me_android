package com.firstfaceme.firstface.view.frgament

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.model.home.Result
import com.firstfaceme.firstface.view.activity.ProfileDetailActivity
import com.firstfaceme.firstface.view.adapter.Card_Adapter
import com.firstfaceme.firstface.view.viewmodel.HomeViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso
import com.yuyakaido.android.cardstackview.*
import kotlinx.android.synthetic.main.activity_match.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(), CardStackListener, View.OnClickListener {
    internal var rootView: View? = null
    private var viewModel: HomeViewModel? = null
    var auth_token = ""
    private var mNearUserList = mutableListOf<Result>()
    private var mOtherUserID = ""
    private var mUserId = ""
    var token = ""
    private var adapterViewPager: Card_Adapter? = null
    private lateinit var layoutManager: CardStackLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()

    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        auth_token = AppPreferences.init(requireContext()).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(requireContext()).getString(Constants.USER_ID)



        // get firebase token
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result!!

            // Hit api near by
            getNearByUser()
            Log.e("TAG", "initView firebase: " + token)
        })


        // set click listener
        rootView?.iv_accept?.setOnClickListener(this)
        rootView?.iv_reject?.setOnClickListener(this)


        //Initialize local broadcast
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(
                mUpdateProfileBroadcastReceiver,
                IntentFilter("PerFromAction")
            )

    }

    // Local Broadcast Receiver
    private val mUpdateProfileBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, p1: Intent?) {

            if (p1?.getStringExtra("Action") == "1") {

                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()

                layoutManager.setSwipeAnimationSetting(setting)
                rootView?.stack_view?.swipe()
            } else {
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

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        if (direction.toString() == "Right") {
            addQueue(mOtherUserID)
        } else {
            dislikUser(mOtherUserID)
        }

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {
        Log.e("TAG", "onCardAppeared: " + position)
        mOtherUserID = mNearUserList[position]._id
    }

    override fun onCardRewound() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_accept -> {

                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()

                layoutManager.setSwipeAnimationSetting(setting)
                rootView?.stack_view?.swipe()


            }
            R.id.iv_reject -> {
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


    //.....................................get  Home Data  ............................................

    fun getNearByUser() {
        pbhome.visibility = View.VISIBLE


        viewModel!!.nearByUser(
            auth_token,
            mUserId, token
        )
            .observe(
                this,
                { pojoUserProfileData ->
                    pbhome.visibility = View.GONE

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {
                        layoutManager = CardStackLayoutManager(context, this).apply {
                            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
                            setOverlayInterpolator(LinearInterpolator())


                        }

                        if (pojoUserProfileData.data.result.isNotEmpty()) {
                            noMatch.visibility = View.GONE
                            llActionButton.visibility = View.VISIBLE
                        } else {
                            noMatch.visibility = View.VISIBLE
                            llActionButton.visibility = View.GONE
                        }
                        mNearUserList.clear()
                        mNearUserList.addAll(pojoUserProfileData.data.result)
                        rootView?.stack_view?.layoutManager = layoutManager

                        rootView?.stack_view?.itemAnimator.apply {
                            if (this is DefaultItemAnimator) {
                                supportsChangeAnimations = false
                            }
                        }
                        rootView?.stack_view?.adapter =
                            Card_Adapter(activity, pojoUserProfileData.data)
                        (rootView?.stack_view?.adapter as Card_Adapter).onItemClick =
                            { pos, view, result ->

                                val intent = Intent(activity, ProfileDetailActivity::class.java)
                                intent.putExtra("UserData", result)
                                startActivity(intent)
                            }


                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    //.....................................add queue    ............................................

    fun addQueue(otherUserId: String) {
        pbhome.visibility = View.VISIBLE
        viewModel!!.addQueue(
            auth_token,
            mUserId, otherUserId
        )
            .observe(
                this,
                { pojoUserProfileData ->
                    pbhome.visibility = View.GONE

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {


                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }
    //.....................................Dislike User   ............................................

    fun dislikUser(otherUserId: String) {
        pbhome.visibility = View.VISIBLE
        viewModel!!.dislikeUser(
            auth_token,
            mUserId, otherUserId
        )
            .observe(
                this,
                { pojoUserProfileData ->
                    pbhome.visibility = View.GONE

                    val statusCode = pojoUserProfileData?.statusCode
                    if (statusCode == 200) {


                    } else {
                        showSnackBar(pojoUserProfileData!!.message)

                    }
                })
    }

    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(requireActivity(), message)
    }
}




