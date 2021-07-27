package com.firstfaceme.firstface.view.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firstfaceme.firstface.model.home.PojoHome
import com.firstfaceme.firstface.model.subscription.PojoCheckSubscriptin
import com.firstfaceme.firstface.model.subscription.PojoGetPlanList
import com.firstfaceme.firstface.model.subscription.PostPurchasePlan
import com.urbanspts.urbanspts.controller.services.APIRepository

class SubscriptionViewModel : AndroidViewModel {
    private lateinit var context: Context

    constructor(application: Application) : super(application) {
        this.context = application
    }

    fun getPlans(
        authToken: String?,
        id: String?
    ): LiveData<PojoGetPlanList> {
        var mutableLiveData: MutableLiveData<PojoGetPlanList> = MutableLiveData()


        return APIRepository.getPlanList(authToken)


        return mutableLiveData
    }

    fun purchasePlan(
        authToken: String?,
        cost: String?,planId:String,purchasetoken:String,userId:String
    ): LiveData<PojoGetPlanList> {
        var mutableLiveData: MutableLiveData<PojoGetPlanList> = MutableLiveData()
val postPurchasePlan= PostPurchasePlan(cost!!.toDouble(),planId,purchasetoken,userId)

        return APIRepository.purchasePlan(authToken,postPurchasePlan)


        return mutableLiveData
    }


    fun checkSubscription(
        authToken: String?,
        userId:String
    ): LiveData<PojoCheckSubscriptin> {
        var mutableLiveData: MutableLiveData<PojoCheckSubscriptin> = MutableLiveData()

        return APIRepository.checkSubscription(authToken!!,userId!!)


        return mutableLiveData
    }


}
