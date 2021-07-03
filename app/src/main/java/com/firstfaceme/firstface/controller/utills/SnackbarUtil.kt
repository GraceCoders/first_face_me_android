package com.firstfaceme.firstface.controller.utills

import android.R
import android.app.Activity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

object SnackbarUtil {
    fun showWarningShortSnackbar(activity: Activity, message: String?) {
        val snackbar = Snackbar.make(
            activity.findViewById(R.id.content),
            message!!,
            4500
        )
        snackbar.view
            .setBackgroundColor(ContextCompat.getColor(App.getAppContext(),R.color.black ))
        snackbar.show()
    }
}