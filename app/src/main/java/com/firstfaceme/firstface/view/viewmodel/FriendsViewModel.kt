package com.firstfaceme.firstface.view.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstfaceme.firstface.model.friends.PojoFriendList
import com.firstfaceme.firstface.model.friends.PostGetFriends
import com.firstfaceme.firstface.model.request.PostGetProfile
import com.firstfaceme.firstface.model.request.PostUpdateProfile
import com.firstfaceme.firstface.model.request.StartPoint
import com.firstfaceme.firstface.model.subscription.PojoGetPlanList
import com.firstfaceme.firstface.model.userInfo.PojoUserDetail
import com.urbanspts.urbanspts.controller.services.APIRepository
import java.io.File

class FriendsViewModel : AndroidViewModel {
    private lateinit var context: Context

    constructor(application: Application) : super(application) {
        this.context = application
    }

    fun getFriendsList(
        auth: String?,
        id: String
    ): LiveData<PojoFriendList> {
        var mutableLiveData: MutableLiveData<PojoFriendList> = MutableLiveData()
        when {

            else -> {
                val PostGetProfile = PostGetFriends(id)
                return APIRepository.getFriendList(auth!!, PostGetProfile)
            }
        }
        return mutableLiveData
    }


}
