package com.firstfaceme.firstface.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstfaceme.firstface.R
import com.firstfaceme.firstface.controller.utills.AppPreferences
import com.firstfaceme.firstface.controller.utills.Constants
import com.firstfaceme.firstface.controller.utills.SnackbarUtil
import com.firstfaceme.firstface.model.subscription.Data
import com.firstfaceme.firstface.view.adapter.Plans_Adapter
import com.firstfaceme.firstface.view.viewmodel.SubscriptionViewModel
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.Source
import com.stripe.android.model.SourceParams
import kotlinx.android.synthetic.main.activity_go_premium.*
import kotlinx.android.synthetic.main.activity_go_premium.iv_back
import kotlinx.android.synthetic.main.activity_stripe_payment.*

class StripePaymentActivity : AppCompatActivity(), View.OnClickListener {
    var month_value = ""
    var year_value: kotlin.String? = ""
    var data=Data()
    private var card: Card? = null
    private var stripetoken = ""
    private var viewModel: SubscriptionViewModel? = null
    var auth_token = ""
    private var mUserId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stripe_payment)
        initView()
    }

    private fun initView() {

        viewModel = ViewModelProviders.of(this).get(SubscriptionViewModel::class.java)

        auth_token = AppPreferences.init(this).getString(Constants.AUTH_TOKEN)
        mUserId = AppPreferences.init(this).getString(Constants.USER_ID)


        if(intent?.extras!=null)
        {
          data   = intent?.extras!!.getParcelable<Data>("Data")!!
            tvPrice.text="Premium Package of $"+data!!.price
        }
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>Categories adapter>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        iv_back.setOnClickListener(this)
        ll_confirm_payment.setOnClickListener(this)
//        et_expiration.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.ll_confirm_payment -> {
                getCard()

            }
        }



        var onValueChangeListeners =
            OnValueChangeListener { numberPicker, i, i1 -> year_value = i1.toString() }
        var onValueChangeListener =
            OnValueChangeListener { numberPicker, i, i1 ->
                val month = i1 + 1
                month_value = if (month < 10) {
                    "0$month"
                } else {
                    month.toString()
                }
            }


        ///////////////////////Expiry date dialog...///////////////////////////////
        fun showcustomdialog() {
            val dialogBuilder = AlertDialog.Builder(this)
            // ...Irrelevant code for customizing the buttons and title
            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.alert_label_editor, null)
            dialogBuilder.setView(dialogView)
            val alertDialog = dialogBuilder.create()
            val np = dialogView.findViewById<NumberPicker>(R.id.month_picker)
            np.minValue = 0
            np.maxValue = 11
            np.displayedValues = arrayOf(
                "Jan",
                "Feb",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
            )
            np.setOnValueChangedListener(onValueChangeListener)
            val nb1 = dialogView.findViewById<NumberPicker>(R.id.year_picker)
            nb1.minValue = 2019
            nb1.maxValue = 2030
            nb1.setOnValueChangedListener(onValueChangeListeners)
            val Okbotton = dialogView.findViewById<Button>(R.id.ok)
            Okbotton.setOnClickListener {
                Log.e("mainacitviy", "onClick: $month_value  $year_value")
                if (month_value.equals("", ignoreCase = true) || year_value.equals(
                        "",
                        ignoreCase = true
                    )
                ) {
                } else {
//                et_expiration.setText(month_value + "/" + year_value)
                }
                alertDialog.dismiss()
            }
            alertDialog.show()
        }

    }


fun getCard()
{
    val stripe = Stripe(
        this@StripePaymentActivity,
        "pk_test_51IlcZCDjYRg4NU9hwvdsCUUTt01tUuU2zR2h6mNcrCZyYpV2Z8TEXAKplrGCut20GCJZq2rwo7LRS661qxxYqGy600HELJjaEC"
    )
    val card = cardInputWidget.card
    val cardSourceParams = SourceParams.createCardParams(card!!)
// The asynchronous way to do it. Call this method on the main thread.
    stripe.createSource(
        cardSourceParams,
        callback = object : ApiResultCallback<Source> {
            override fun onSuccess(source: Source) {
purchaseSubscription(auth_token,data.price.toString(),data._id,source.id!!,mUserId)

                Log.e("TAG", "onSuccess: "+source.toString() )
                // Store the source somewhere, use it, etc
            }

            override fun onError(error: Exception) {
                // Tell the user that something went wrong
            }
        }
    )


}

    //.....................................get Subscription Plan ............................................

    fun purchaseSubscription(
        auth_token: String,
        toString: String,
        planId: String,
        id: String,
        mUserId: String
    ) {
        rlPayment.visibility = View.VISIBLE
        viewModel!!.purchasePlan(
            auth_token,data.price.toString(),data._id,id!!, mUserId
        )

            .observe(
                this,
                { pojoSubscriptionList ->
                    rlPayment.visibility = View.GONE

                    val statusCode = pojoSubscriptionList?.statusCode
                    if (statusCode == 200) {







                    } else {
                        showSnackBar(pojoSubscriptionList!!.message)

                    }
                })
    }



    private fun showSnackBar(message: String?) {
        SnackbarUtil.showWarningShortSnackbar(this, message)
    }

}